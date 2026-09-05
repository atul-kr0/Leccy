package com.ev.EvChargingStation.mapper;

import com.ev.EvChargingStation.dto.recommendation.RecommendationResponseDTO;
import com.ev.EvChargingStation.dto.recommendation.RecommendedStationDTO;
import com.ev.EvChargingStation.service.recommendation.model.CandidateStation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    @Mapping(target = "stationId",
            source = "station.id")

    @Mapping(target = "stationName",
            source = "station.stationName")

    @Mapping(target = "distance",
            source = "drivingDistanceKm")

    @Mapping(target = "waitingTime",
            source = "chargerPrediction.waitingTime")

    @Mapping(target = "chargingDuration",
            source = "chargerPrediction.estimatedChargingDuration")

    @Mapping(target = "totalTime",
            source = "chargerPrediction.estimatedCompletionTime")

    @Mapping(target = "pricePerKwh",
            source = "station.pricePerKwh")

    @Mapping(target = "recommendationScore",
            source = "recommendationScore")

    @Mapping(target = "recommendationReasons",
            source = "recommendationReasons")
    RecommendedStationDTO toRecommendedStation(
            CandidateStation candidate
    );

    List<RecommendedStationDTO> toRecommendedStations(
            List<CandidateStation> candidates
    );

    default RecommendationResponseDTO toResponse(
            List<CandidateStation> candidates
    ) {
        return new RecommendationResponseDTO(
                toRecommendedStations(candidates)
        );
    }
}