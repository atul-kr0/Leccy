package com.ev.EvChargingStation.controller;

import com.ev.EvChargingStation.dto.charger.ChargerRequestDTO;
import com.ev.EvChargingStation.dto.charger.ChargerResponseDTO;
import com.ev.EvChargingStation.service.ChargerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChargerController {

    private final ChargerService chargerService;

    // ==========================
    // ADMIN ENDPOINTS
    // ==========================

    @PostMapping("/stations/{stationId}/chargers")
    @PreAuthorize("hasRole('ADMIN')")
    public ChargerResponseDTO createCharger(
            @PathVariable Long stationId,
            @Valid @RequestBody ChargerRequestDTO dto) {

        return chargerService.createCharger(stationId, dto);
    }

    @PutMapping("/chargers/{chargerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ChargerResponseDTO updateCharger(
            @PathVariable Long chargerId,
            @Valid @RequestBody ChargerRequestDTO dto) {

        return chargerService.updateCharger(chargerId, dto);
    }
    @DeleteMapping("/chargers/{chargerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCharger(@PathVariable Long chargerId) {

        chargerService.deleteCharger(chargerId);
    }

    // ==========================
    // USER + ADMIN ENDPOINTS
    // ==========================

    @GetMapping("/chargers")
    public List<ChargerResponseDTO> getAllChargers() {

        return chargerService.getAllChargers();
    }

    @GetMapping("/chargers/{chargerId}")
    public ChargerResponseDTO getChargerById(
            @PathVariable Long chargerId) {

        return chargerService.getChargerById(chargerId);
    }

    @GetMapping("/stations/{stationId}/chargers")
    public List<ChargerResponseDTO> getChargersByStation(
            @PathVariable Long stationId) {

        return chargerService.getChargersByStation(stationId);
    }
}