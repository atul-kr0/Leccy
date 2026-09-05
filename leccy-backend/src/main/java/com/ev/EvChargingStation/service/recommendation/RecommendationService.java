package com.ev.EvChargingStation.service.recommendation;

import com.ev.EvChargingStation.dto.recommendation.RecommendationRequestDTO;
import com.ev.EvChargingStation.entity.Vehicle;
import com.ev.EvChargingStation.service.recommendation.model.CandidateStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private static final double DEFAULT_RADIUS_KM = 10.0;

    private final NearbyStationService nearbyStationService;

    private final CandidateBuildingService candidateBuildingService;

    private final CandidateSelectionService candidateSelectionService;

    private final RouteService routeService;

    private final RecommendationScoreService recommendationScoreService;

    public List<CandidateStation> buildRecommendationCandidates(
            double userLatitude,
            double userLongitude,
            Vehicle vehicle,
            Integer currentBatteryPercentage,
            Integer targetBatteryPercentage,
            RecommendationRequestDTO request
    ) {

        System.out.println("=== Recommendation Started ===");

        List<CandidateStation> nearbyStations =
                nearbyStationService.findNearbyStations(
                        userLatitude,
                        userLongitude,
                        DEFAULT_RADIUS_KM
                );

        System.out.println("Nearby Stations: " + nearbyStations.size());

        List<CandidateStation> candidates =
                candidateBuildingService.buildCandidates(
                        nearbyStations,
                        vehicle,
                        currentBatteryPercentage,
                        targetBatteryPercentage
                );

        System.out.println("Candidates Built: " + candidates.size());

        List<CandidateStation> shortlistedCandidates =
                candidateSelectionService.selectTopCandidates(
                        candidates
                );

        System.out.println("Shortlisted: " + shortlistedCandidates.size());

        System.out.println(">>> Calling RouteService");

        routeService.enrichCandidatesWithRoutes(
                shortlistedCandidates,
                userLatitude,
                userLongitude
        );

        System.out.println("<<< RouteService Finished");

        System.out.println(">>> Calling RecommendationScoreService");

        List<CandidateStation> scored =
                recommendationScoreService.calculateScores(
                        shortlistedCandidates,
                        request
                );

        System.out.println("<<< RecommendationScoreService Finished");

        System.out.println("=== Recommendation Finished ===");

        return scored;
    }
}