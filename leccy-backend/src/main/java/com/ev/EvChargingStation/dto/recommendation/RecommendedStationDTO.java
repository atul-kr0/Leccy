package com.ev.EvChargingStation.dto.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendedStationDTO {

    private Long stationId;

    private String stationName;

    private Double distance;

    private Integer waitingTime;

    private Integer chargingDuration;

    private Integer totalTime;

    private Double pricePerKwh;

    private Double recommendationScore;

    private List<String> recommendationReasons;
}
