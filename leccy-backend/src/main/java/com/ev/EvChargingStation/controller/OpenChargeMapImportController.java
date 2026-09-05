package com.ev.EvChargingStation.controller;

import com.ev.EvChargingStation.service.OpenChargeMapImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class OpenChargeMapImportController {

    private final OpenChargeMapImportService importService;

    @PostMapping("/import-stations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> importStations() {

        log.info("OpenChargeMap station import started...");

        importService.importStations();

        log.info("OpenChargeMap station import completed.");

        return ResponseEntity.ok("Stations imported successfully.");
    }
}