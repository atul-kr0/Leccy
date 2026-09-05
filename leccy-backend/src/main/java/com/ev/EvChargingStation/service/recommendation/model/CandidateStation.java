package com.ev.EvChargingStation.service.recommendation.model;

import com.ev.EvChargingStation.dto.booking.ChargerSelectionResult;
import com.ev.EvChargingStation.entity.Charger;
import com.ev.EvChargingStation.entity.ChargingStation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateStation {

    private ChargingStation station;

    private double straightLineDistanceKm;

    private ChargerSelectionResult chargerPrediction;

    private Double drivingDistanceKm;

    private Integer estimatedTravelTimeMinutes;

    private double recommendationScore;

    private List<String> recommendationReasons;
}