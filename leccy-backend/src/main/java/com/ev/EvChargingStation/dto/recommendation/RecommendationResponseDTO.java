package com.ev.EvChargingStation.dto.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponseDTO {

    private List<RecommendedStationDTO> recommendations;
}
