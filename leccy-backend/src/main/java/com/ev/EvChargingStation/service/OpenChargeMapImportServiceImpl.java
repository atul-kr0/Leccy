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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenChargeMapImportServiceImpl
        implements OpenChargeMapImportService {

    private final ChargingStationRepository stationRepository;
    private final ChargerRepository chargerRepository;

    @Value("${openchargemap.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    private static final int MAX_RESULTS = 1_000_000;

    @Override
    public void importStations() {

        int importedStations = 0;
        int skippedStations = 0;
        int importedChargers = 0;

        // 1. Fetch stations from OpenChargeMap.
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.openchargemap.io/v3/poi")
                .queryParam("output", "json")
                .queryParam("countrycode", "IN")
                .queryParam("maxresults", MAX_RESULTS)
                .queryParam("key", apiKey)
                .toUriString();

        log.info("Fetching EV charging stations across India...");

        OpenChargeMapResponse[] response = restClient.get()
                .uri(url)
                .retrieve()
                .body(OpenChargeMapResponse[].class);

        if (response == null || response.length == 0) {
            log.warn("OpenChargeMap returned no stations.");
            return;
        }

        log.info("OpenChargeMap returned {} stations.", response.length);

        // 2. Load all existing OCM IDs in one database query.
        Set<Long> existingIds = new HashSet<>(
                stationRepository.findAllOpenChargeMapIds()
        );

        log.info(
                "Found {} existing OpenChargeMap station IDs.",
                existingIds.size()
        );

        // 3. Process each station.
        for (OpenChargeMapResponse dto : response) {

            if (dto == null || dto.getId() == null) {
                continue;
            }

            Long openChargeMapId = dto.getId();

            // Skip IDs already in the database OR already processed
            // during this import.
            if (!existingIds.add(openChargeMapId)) {
                skippedStations++;
                continue;
            }

            if (dto.getAddressInfo() == null) {
                log.warn(
                        "Skipping station with missing address. OCM ID: {}",
                        openChargeMapId
                );

                // It wasn't saved, so remove it from the in-memory set.
                existingIds.remove(openChargeMapId);
                continue;
            }

            ChargingStation station = new ChargingStation();

            station.setOpenChargeMapId(openChargeMapId);

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

            // 4. Save the new station.
            station = stationRepository.save(station);
            importedStations++;

            // 5. Import chargers only for this newly inserted station.
            List<ConnectionDTO> connections = dto.getConnections();

            if (connections == null || connections.isEmpty()) {
                continue;
            }

            int chargerNumber = 1;

            for (ConnectionDTO connection : connections) {

                if (connection == null) {
                    continue;
                }

                int quantity = connection.getQuantity() == null
                        ? 1
                        : Math.max(0, connection.getQuantity());

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

        log.info("India-wide import completed.");
        log.info("Stations imported: {}", importedStations);
        log.info("Stations skipped: {}", skippedStations);
        log.info("Chargers imported: {}", importedChargers);
    }

    private ConnectorType mapConnector(ConnectionDTO connection) {

        if (connection.getConnectionType() == null
                || connection.getConnectionType().getTitle() == null) {
            return ConnectorType.CCS2;
        }

        String type = connection.getConnectionType()
                .getTitle()
                .toUpperCase();

        if (type.contains("CHADEMO")) {
            return ConnectorType.CHADEMO;
        }

        if (type.contains("TYPE 2")) {
            return ConnectorType.TYPE2;
        }

        if (type.contains("CCS")) {
            return ConnectorType.CCS2;
        }

        return ConnectorType.CCS2;
    }
}