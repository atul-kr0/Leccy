package com.ev.EvChargingStation.controller;

import com.ev.EvChargingStation.dto.chargingSession.StopChargingRequestDTO;
import com.ev.EvChargingStation.service.booking.StopChargingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/charging-session")
@RequiredArgsConstructor
public class ChargingSessionController {

    private final StopChargingService stopChargingService;

    @PostMapping("/stop")
    public ResponseEntity<String> stopCharging(
            @Valid @RequestBody StopChargingRequestDTO requestDTO) {

        stopChargingService.stopCharging(requestDTO.getToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Charging session stopped successfully.");
    }
}