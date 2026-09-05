package com.ev.EvChargingStation.service.recommendation;

import com.ev.EvChargingStation.dto.recommendation.RecommendationRequestDTO;
import com.ev.EvChargingStation.enums.RecommendationPriority;
import com.ev.EvChargingStation.enums.RecommendationType;
import com.ev.EvChargingStation.service.recommendation.model.CandidateStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendationScoreService {

    private RecommendationType recommendationType;

    public List<CandidateStation> calculateScores(
            List<CandidateStation> candidates,
            RecommendationRequestDTO request
    ) {

        if (candidates == null || candidates.isEmpty()) {
            return candidates;
        }

        Map<RecommendationPriority, Integer> weights =
                buildWeightMap(request);

        double minEta = Double.MAX_VALUE;
        double maxEta = Double.MIN_VALUE;

        double minWaiting = Double.MAX_VALUE;
        double maxWaiting = Double.MIN_VALUE;

        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;

        double minPower = Double.MAX_VALUE;
        double maxPower = Double.MIN_VALUE;

        for (CandidateStation candidate : candidates) {

            minEta = Math.min(
                    minEta,
                    candidate.getEstimatedTravelTimeMinutes()
            );

            maxEta = Math.max(
                    maxEta,
                    candidate.getEstimatedTravelTimeMinutes()
            );

            minWaiting = Math.min(
                    minWaiting,
                    candidate.getChargerPrediction()
                            .getWaitingTime()
            );

            maxWaiting = Math.max(
                    maxWaiting,
                    candidate.getChargerPrediction()
                            .getWaitingTime()
            );

            minPrice = Math.min(
                    minPrice,
                    candidate.getStation()
                            .getPricePerKwh()
            );

            maxPrice = Math.max(
                    maxPrice,
                    candidate.getStation()
                            .getPricePerKwh()
            );

            minPower = Math.min(
                    minPower,
                    candidate.getChargerPrediction()
                            .getCharger()
                            .getOutputPower()
            );

            maxPower = Math.max(
                    maxPower,
                    candidate.getChargerPrediction()
                            .getCharger()
                            .getOutputPower()
            );
        }

        for (CandidateStation candidate : candidates) {

            double etaScore =
                    normalize(
                            candidate.getEstimatedTravelTimeMinutes(),
                            minEta,
                            maxEta,
                            false
                    );

            double waitingScore =
                    normalize(
                            candidate.getChargerPrediction()
                                    .getWaitingTime(),
                            minWaiting,
                            maxWaiting,
                            false
                    );

            double priceScore =
                    normalize(
                            candidate.getStation()
                                    .getPricePerKwh(),
                            minPrice,
                            maxPrice,
                            false
                    );

            double powerScore =
                    normalize(
                            candidate.getChargerPrediction()
                                    .getCharger()
                                    .getOutputPower(),
                            minPower,
                            maxPower,
                            true
                    );

            double score =
                    calculateWeightedAverage(
                            etaScore,
                            waitingScore,
                            priceScore,
                            powerScore,
                            weights
                    );

            candidate.setRecommendationScore(
                    Math.round(score * 1000.0) / 10.0
            );
        }

        List<CandidateStation> rankedCandidates =
                new ArrayList<>(candidates);

        rankedCandidates.sort(
                Comparator.comparingDouble(
                        CandidateStation::getRecommendationScore
                ).reversed()
        );

        return rankedCandidates;
    }

    private Map<RecommendationPriority, Integer> buildWeightMap(
            RecommendationRequestDTO request
    ) {

        if (request.getRecommendationType() == RecommendationType.SMART) {
            return buildSmartWeights();
        }

        return buildCustomWeights(
                request.getPriorities()
        );
    }

    private Map<RecommendationPriority, Integer> buildSmartWeights() {

        Map<RecommendationPriority, Integer> weights =
                new EnumMap<>(RecommendationPriority.class);

        /*
         * Leccy's default recommendation strategy.
         *
         * ETA            -> Highest priority
         * Waiting Time   -> Second highest
         * Fast Charging  -> Third
         * Lowest Cost    -> Fourth
         */

        weights.put(
                RecommendationPriority.NEAREST_STATION,
                4
        );

        weights.put(
                RecommendationPriority.WAITING_TIME,
                3
        );

        weights.put(
                RecommendationPriority.FAST_CHARGING,
                2
        );

        weights.put(
                RecommendationPriority.LOWEST_COST,
                1
        );

        return weights;
    }

    private Map<RecommendationPriority, Integer> buildCustomWeights(
            List<RecommendationPriority> priorities
    ) {

        Map<RecommendationPriority, Integer> weights =
                new EnumMap<>(RecommendationPriority.class);

        int currentWeight =
                RecommendationPriority.values().length;

        for (RecommendationPriority priority : priorities) {

            weights.put(
                    priority,
                    currentWeight--
            );
        }

        /*
         * Metrics that were not selected
         * still contribute a little.
         */

        for (RecommendationPriority priority
                : RecommendationPriority.values()) {

            weights.putIfAbsent(
                    priority,
                    1
            );
        }

        return weights;
    }

    private double calculateWeightedAverage(

            double eta,

            double waiting,

            double price,

            double power,

            Map<RecommendationPriority, Integer> weights
    ) {

        double etaWeight =
                weights.get(RecommendationPriority.NEAREST_STATION);

        double waitingWeight =
                weights.get(RecommendationPriority.WAITING_TIME);

        double priceWeight =
                weights.get(RecommendationPriority.LOWEST_COST);

        double powerWeight =
                weights.get(RecommendationPriority.FAST_CHARGING);

        double totalWeight =
                etaWeight
                        + waitingWeight
                        + priceWeight
                        + powerWeight;

        return (

                eta * etaWeight

                        +

                        waiting * waitingWeight

                        +

                        price * priceWeight

                        +

                        power * powerWeight

        ) / totalWeight;
    }
    private double normalize(

            double value,

            double minimum,

            double maximum,

            boolean higherIsBetter
    ) {

        if (Double.compare(minimum, maximum) == 0) {
            return 1.0;
        }

        if (higherIsBetter) {

            return (value - minimum)
                    / (maximum - minimum);
        }

        return (maximum - value)
                / (maximum - minimum);
    }

}