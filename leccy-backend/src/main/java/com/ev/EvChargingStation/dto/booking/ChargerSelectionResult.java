package com.ev.EvChargingStation.dto.booking;

import com.ev.EvChargingStation.entity.Charger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargerSelectionResult {

    private Charger charger;

    private Integer waitingTime;

    private Integer estimatedChargingDuration;

    private Integer estimatedCompletionTime;
}