package com.ev.EvChargingStation.service.recommendation;

import com.ev.EvChargingStation.dto.booking.ChargerSelectionResult;
import com.ev.EvChargingStation.entity.Vehicle;
import com.ev.EvChargingStation.exception.NoCompatibleChargerException;
import com.ev.EvChargingStation.service.booking.ChargerPickerService;
import com.ev.EvChargingStation.service.recommendation.model.CandidateStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateBuildingService {

    private final ChargerPickerService chargerPickerService;

    public List<CandidateStation> buildCandidates(
            List<CandidateStation> nearbyStations,
            Vehicle vehicle,
            Integer currentBatteryPercentage,
            Integer targetBatteryPercentage
    ) {

        List<CandidateStation> recommendationCandidates =
                new ArrayList<>();

        for (CandidateStation candidate : nearbyStations) {

            try {

                ChargerSelectionResult prediction =
                        chargerPickerService.pickFastestCompletionCharger(
                                candidate.getStation(),
                                vehicle,
                                currentBatteryPercentage,
                                targetBatteryPercentage
                        );

                candidate.setChargerPrediction(prediction);

                recommendationCandidates.add(candidate);

            } catch (NoCompatibleChargerException ignored) {

                // Station is not eligible for recommendation.
            }
        }

        return recommendationCandidates;
    }
}