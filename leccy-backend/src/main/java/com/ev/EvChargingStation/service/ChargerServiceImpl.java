package com.ev.EvChargingStation.service;

import com.ev.EvChargingStation.dto.charger.ChargerRequestDTO;
import com.ev.EvChargingStation.dto.charger.ChargerResponseDTO;
import com.ev.EvChargingStation.entity.Charger;
import com.ev.EvChargingStation.entity.ChargingStation;
import com.ev.EvChargingStation.exception.ChargerNotFoundException;
import com.ev.EvChargingStation.exception.ChargingStationNotFoundException;
import com.ev.EvChargingStation.mapper.ChargerMapper;
import com.ev.EvChargingStation.repository.ChargerRepository;
import com.ev.EvChargingStation.repository.ChargingStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChargerServiceImpl implements ChargerService {

    private final ChargerRepository chargerRepository;
    private final ChargingStationRepository chargingStationRepository;
    private final ChargerMapper chargerMapper;

    @Override
    public ChargerResponseDTO createCharger(Long stationId, ChargerRequestDTO dto){

        ChargingStation station = chargingStationRepository.findById(stationId).orElseThrow(() -> new ChargingStationNotFoundException("Charging Station not found"+stationId));
        Charger charger = chargerMapper.toEntity(dto);
        charger.setChargingStation(station);
        Charger saved = chargerRepository.save(charger);

        return chargerMapper.toResponseDto(saved);
    }

    @Override
    public List<ChargerResponseDTO> getAllChargers() {

        List<Charger> chargers = chargerRepository.findAll();

        List<ChargerResponseDTO> chargerList = chargers.stream()
                .map(charger -> chargerMapper.toResponseDto(charger))
                .toList();

        return chargerList;
    }

    @Override
    public ChargerResponseDTO getChargerById(Long chargerId){

        Charger charger = chargerRepository.findById(chargerId)
                .orElseThrow(() ->
                new ChargerNotFoundException("Charger not found"+ chargerId));

        return chargerMapper.toResponseDto(charger);
    }

    @Override
    public ChargerResponseDTO updateCharger(Long chargerId, ChargerRequestDTO dto){

        Charger charger = chargerRepository.findById(chargerId)
                .orElseThrow(()->
                        new ChargerNotFoundException("Charger not found"+ chargerId));
        chargerMapper.updateEntityFromDto(dto,charger);
        Charger updated = chargerRepository.save(charger);

        return chargerMapper.toResponseDto(updated);
    }

    @Override
    public void deleteCharger(Long chargerId){

        Charger charger = chargerRepository.findById(chargerId)
                .orElseThrow(()->
                        new ChargerNotFoundException("Charger not found"+ chargerId));

        chargerRepository.delete(charger);
    }

    @Override
    public List<ChargerResponseDTO> getChargersByStation(Long stationId){

        ChargingStation chargingStation = chargingStationRepository.findById(stationId)
                .orElseThrow(()->
                        new ChargingStationNotFoundException("Charging Station not found"+ stationId));

        List<Charger> chargers = chargerRepository.findByChargingStation(chargingStation);

        List<ChargerResponseDTO> response = chargers.stream()
                .map(charger -> chargerMapper.toResponseDto(charger))
                .toList();

        return response;
    }
}
