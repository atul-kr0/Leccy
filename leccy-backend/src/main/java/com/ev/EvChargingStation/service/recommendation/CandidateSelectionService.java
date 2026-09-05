package com.ev.EvChargingStation.service.recommendation;

import com.ev.EvChargingStation.service.recommendation.model.CandidateStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateSelectionService {

    private static final int MAX_CANDIDATES = 5;
    private static final double DISTANCE_PENALTY_PER_KM = 2.0;

    public List<CandidateStation> selectTopCandidates(
            List<CandidateStation> candidates
    ) {

        return new ArrayList<>(
                candidates.stream()
                        .sorted(
                                Comparator.comparingDouble(
                                        this::calculatePreliminaryScore
                                )
                        )
                        .limit(MAX_CANDIDATES)
                        .toList()
        );
    }

    private double calculatePreliminaryScore(
            CandidateStation candidate
    ) {

        double completionTime =
                candidate.getChargerPrediction()
                        .getEstimatedCompletionTime();

        double distance =
                candidate.getStraightLineDistanceKm();

        return completionTime + (distance * DISTANCE_PENALTY_PER_KM);
    }
}