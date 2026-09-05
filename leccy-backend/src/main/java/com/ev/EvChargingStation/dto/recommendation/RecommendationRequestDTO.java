package com.ev.EvChargingStation.dto.recommendation;

import com.ev.EvChargingStation.enums.RecommendationPriority;
import com.ev.EvChargingStation.enums.RecommendationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequestDTO {

    private Long vehicleId;

    private Double currentLatitude;

    private Double currentLongitude;

    private Integer currentBatteryPercentage;

    private Integer targetBatteryPercentage;

    private RecommendationType recommendationType;

    private List<RecommendationPriority> priorities;

    private Boolean rememberPreference;
}
