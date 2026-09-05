package com.ev.EvChargingStation.controller;

import com.ev.EvChargingStation.dto.recommendation.RecommendationRequestDTO;
import com.ev.EvChargingStation.dto.recommendation.RecommendationResponseDTO;
import com.ev.EvChargingStation.entity.Vehicle;
import com.ev.EvChargingStation.helper.VehicleHelper;
import com.ev.EvChargingStation.mapper.RecommendationMapper;
import com.ev.EvChargingStation.service.recommendation.RecommendationService;
import com.ev.EvChargingStation.service.recommendation.model.CandidateStation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    private final RecommendationMapper recommendationMapper;

    private final VehicleHelper vehicleHelper;

    @PostMapping
    public ResponseEntity<RecommendationResponseDTO> recommendStations(
            @Valid @RequestBody RecommendationRequestDTO request
    ) {

        Vehicle vehicle =
                vehicleHelper.validateUsersVehicle(
                        request.getVehicleId()
                );

        List<CandidateStation> recommendations =
                recommendationService.buildRecommendationCandidates(
                        request.getCurrentLatitude(),
                        request.getCurrentLongitude(),
                        vehicle,
                        request.getCurrentBatteryPercentage(),
                        request.getTargetBatteryPercentage(),
                        request
                );

        return ResponseEntity.ok(
                recommendationMapper.toResponse(
                        recommendations
                )
        );
    }
}