package com.ev.EvChargingStation.controller;

import com.ev.EvChargingStation.dto.chargingSession.CheckInRequestDTO;
import com.ev.EvChargingStation.service.booking.CheckInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/check-in")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping
    public ResponseEntity<String> checkIn(
            @Valid @RequestBody CheckInRequestDTO requestDTO) {

        checkInService.checkIn(requestDTO.getToken());

        return ResponseEntity.ok(
                "Check-in successful. Charging session started."
        );
    }
}