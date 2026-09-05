package com.ev.EvChargingStation.service.recommendation;

import com.ev.EvChargingStation.entity.ChargingStation;
import com.ev.EvChargingStation.enums.StationStatus;
import com.ev.EvChargingStation.repository.ChargingStationRepository;
import com.ev.EvChargingStation.service.recommendation.model.CandidateStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NearbyStationService {

    private final ChargingStationRepository chargingStationRepository;
    private final DistanceCalculatorService distanceCalculatorService;

    public List<CandidateStation> findNearbyStations(
            double userLatitude,
            double userLongitude,
            double radiusKm
    ) {

        List<ChargingStation> stations =
                chargingStationRepository.findByStationStatus(
                        StationStatus.ACTIVE
                );

        List<CandidateStation> nearbyStations = new ArrayList<>();

        for (ChargingStation station : stations) {

            double distance =
                    distanceCalculatorService.calculateDistance(
                            userLatitude,
                            userLongitude,
                            station.getLatitude(),
                            station.getLongitude()
                    );

            if (distance <= radiusKm) {

                CandidateStation candidateStation =
                        new CandidateStation();

                candidateStation.setStation(station);
                candidateStation.setStraightLineDistanceKm(distance);

                nearbyStations.add(candidateStation);
            }
        }

        nearbyStations.sort(
                Comparator.comparing(
                        CandidateStation::getStraightLineDistanceKm
                )
        );

        return nearbyStations;
    }
}