package com.ev.EvChargingStation.controller;

import com.ev.EvChargingStation.dto.chargingStation.ChargingStationRequestDTO;
import com.ev.EvChargingStation.dto.chargingStation.ChargingStationResponseDTO;
import com.ev.EvChargingStation.service.ChargingStationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class ChargingStationController {

    private final ChargingStationService chargingStationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ChargingStationResponseDTO createStation(
            @Valid @RequestBody ChargingStationRequestDTO request) {

        return chargingStationService.createStation(request);
    }

    @GetMapping
    public List<ChargingStationResponseDTO> getAllStations() {

        return chargingStationService.getAllStations();
    }

    @GetMapping("/{id}")
    public ChargingStationResponseDTO getStationById(
            @PathVariable Long id) {

        return chargingStationService.getStationById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ChargingStationResponseDTO updateStation(
            @PathVariable Long id,
            @Valid @RequestBody ChargingStationRequestDTO request) {

        return chargingStationService.updateStation(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStation(
            @PathVariable Long id) {

        chargingStationService.deleteStation(id);
    }
}