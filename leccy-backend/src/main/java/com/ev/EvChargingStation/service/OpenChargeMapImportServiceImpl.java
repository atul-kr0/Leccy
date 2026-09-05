package com.ev.EvChargingStation.service;

import com.ev.EvChargingStation.dto.openchargemap.ConnectionDTO;
import com.ev.EvChargingStation.dto.openchargemap.OpenChargeMapResponse;
import com.ev.EvChargingStation.entity.Charger;
import com.ev.EvChargingStation.entity.ChargingStation;
import com.ev.EvChargingStation.enums.ChargerStatus;
import com.ev.EvChargingStation.enums.ConnectorType;
import com.ev.EvChargingStation.enums.StationStatus;
import com.ev.EvChargingStation.repository.ChargerRepository;
import com.ev.EvChargingStation.repository.ChargingStationRepository;
import com.ev.EvChargingStation.service.OpenChargeMapImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class OpenChargeMapImportServiceImpl implements OpenChargeMapImportService {

    private final ChargingStationRepository stationRepository;
    private final ChargerRepository chargerRepository;

    @Value("${openchargemap.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    private static final List<double[]> NCR_LOCATIONS = List.of(
            new double[]{28.6139, 77.2090}, // Delhi
            new double[]{28.5355, 77.3910}, // Noida
            new double[]{28.4595, 77.0266}, // Gurgaon
            new double[]{28.4089, 77.3178}, // Faridabad
            new double[]{28.6692, 77.4538}, // Ghaziabad
            new double[]{28.4744, 77.5040}  // Greater Noida
    );

    @Override
    public void importStations() {

        int importedStations = 0;
        int skippedStations = 0;
        int importedChargers = 0;

        for (double[] location : NCR_LOCATIONS) {

            String url = UriComponentsBuilder
                    .fromHttpUrl("https://api.openchargemap.io/v3/poi")
                    .queryParam("output", "json")
                    .queryParam("countrycode", "IN")
                    .queryParam("latitude", location[0])
                    .queryParam("longitude", location[1])
                    .queryParam("distance", 500)
                    .queryParam("distanceunit", "KM")
                    .queryParam("maxresults", 500)
                    .queryParam("key", apiKey)
                    .toUriString();

            OpenChargeMapResponse[] response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(OpenChargeMapResponse[].class);
//
            System.out.println(response.length);

            System.out.println(response[0].getId());

            System.out.println(response[0].getAddressInfo());

            System.out.println(response[0].getAddressInfo().getTitle());
//
            if (response == null)
                continue;
//
            System.out.println(response.length);
            System.out.println(response[0].getId());

            System.out.println("Response Size = " + response.length);
            System.out.println("First ID = " + response[0].getId());
            System.out.println("First AddressInfo = " + response[0].getAddressInfo());

            System.out.println("First Title = " +
                    response[0].getAddressInfo().getTitle());
//
            for (OpenChargeMapResponse dto : response) {

                if (dto.getId() == null)
                    continue;

                if (stationRepository.existsByOpenChargeMapId(dto.getId())) {
                    skippedStations++;
                    continue;
                }

                ChargingStation station = new ChargingStation();

                station.setOpenChargeMapId(dto.getId());

                station.setStationName(
                        dto.getAddressInfo().getTitle() == null
                                ? "Unknown Station"
                                : dto.getAddressInfo().getTitle()
                );

                station.setAddress(
                        dto.getAddressInfo().getAddressLine1()
                );

                station.setLatitude(
                        dto.getAddressInfo().getLatitude()
                );

                station.setLongitude(
                        dto.getAddressInfo().getLongitude()
                );

                station.setPricePerKwh(18.0);

                station.setRating(0.0);

                station.setStationStatus(StationStatus.ACTIVE);

                stationRepository.save(station);

                importedStations++;

                if (dto.getConnections() == null)
                    continue;

                int chargerNumber = 1;

                for (ConnectionDTO connection : dto.getConnections()) {

                    int quantity = connection.getQuantity() == null
                            ? 1
                            : connection.getQuantity();

                    for (int i = 0; i < quantity; i++) {

                        Charger charger = new Charger();

                        charger.setChargingStation(station);

                        charger.setChargerNumber(
                                station.getId() + "-CH-" + chargerNumber++
                        );

                        charger.setOutputPower(
                                connection.getPowerKW() == null
                                        ? 22.0
                                        : connection.getPowerKW()
                        );

                        charger.setConnectorType(
                                mapConnector(connection)
                        );

                        charger.setChargerStatus(
                                ChargerStatus.AVAILABLE
                        );

                        chargerRepository.save(charger);

                        importedChargers++;
                    }
                }
            }
        }

        log.info("Stations Imported : {}", importedStations);
        log.info("Stations Skipped : {}", skippedStations);
        log.info("Chargers Imported : {}", importedChargers);
    }

    private ConnectorType mapConnector(ConnectionDTO connection) {

        if (connection.getConnectionType() == null)
            return ConnectorType.CCS2;

        String type = connection.getConnectionType()
                .getTitle()
                .toUpperCase();

        if (type.contains("CHADEMO"))
            return ConnectorType.CHADEMO;

        if (type.contains("TYPE 2"))
            return ConnectorType.TYPE2;

        if (type.contains("CCS"))
            return ConnectorType.CCS2;

        return ConnectorType.CCS2;
    }
}