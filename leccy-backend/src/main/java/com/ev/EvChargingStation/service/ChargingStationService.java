package com.ev.EvChargingStation.service;

import com.ev.EvChargingStation.dto.chargingStation.ChargingStationRequestDTO;
import com.ev.EvChargingStation.dto.chargingStation.ChargingStationResponseDTO;

import java.util.List;

public interface ChargingStationService {

    public ChargingStationResponseDTO createStation(ChargingStationRequestDTO request);
    public List<ChargingStationResponseDTO> getAllStations();
    public ChargingStationResponseDTO getStationById(Long id);
    public ChargingStationResponseDTO updateStation(Long id, ChargingStationRequestDTO request);
    public void deleteStation(Long id);
}
