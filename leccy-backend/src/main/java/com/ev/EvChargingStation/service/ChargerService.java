package com.ev.EvChargingStation.service;

import com.ev.EvChargingStation.dto.charger.ChargerRequestDTO;
import com.ev.EvChargingStation.dto.charger.ChargerResponseDTO;

import java.util.List;

public interface ChargerService {

    public ChargerResponseDTO createCharger(Long stationId, ChargerRequestDTO dto);

    public List<ChargerResponseDTO> getAllChargers();

    public ChargerResponseDTO getChargerById(Long chargerId);

    public ChargerResponseDTO updateCharger(Long chargerId, ChargerRequestDTO dto);

    public void deleteCharger(Long chargerId);

    public List<ChargerResponseDTO> getChargersByStation(Long stationId);
}
