package com.ev.EvChargingStation.service;

import com.ev.EvChargingStation.dto.chargingStation.ChargingStationRequestDTO;
import com.ev.EvChargingStation.dto.chargingStation.ChargingStationResponseDTO;
import com.ev.EvChargingStation.entity.ChargingStation;
import com.ev.EvChargingStation.exception.ChargingStationNotFoundException;
import com.ev.EvChargingStation.mapper.StationMapper;
import com.ev.EvChargingStation.repository.ChargingStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ChargingStationServiceImpl implements ChargingStationService {

    private final ChargingStationRepository chargingStationRepository;
    private final StationMapper stationMapper;

    @Override
    public ChargingStationResponseDTO createStation(ChargingStationRequestDTO request){

        ChargingStation chargingStation = stationMapper.toEntity(request);

         ChargingStation saved = chargingStationRepository.save(chargingStation);

         return stationMapper.toResponseDto(saved);
    }

    @Override
    public List<ChargingStationResponseDTO> getAllStations(){

        List<ChargingStation> stations = chargingStationRepository.findAll();

        return stations.stream()
                .map(station -> stationMapper.toResponseDto(station))
                .toList();
    }

    @Override
    public ChargingStationResponseDTO getStationById(Long id){

        ChargingStation station = chargingStationRepository.findById(id)
                .orElseThrow(() -> new ChargingStationNotFoundException("Station not found"));

        return stationMapper.toResponseDto(station);
    }

    @Override
    public ChargingStationResponseDTO updateStation(Long id, ChargingStationRequestDTO request){

        ChargingStation chargingStation = chargingStationRepository.findById(id).orElseThrow(() -> new ChargingStationNotFoundException("Station not found"));

        stationMapper.updateEntityFromDto(request, chargingStation);
        return stationMapper.toResponseDto(
                chargingStationRepository.save(chargingStation)
        );
    }

    @Override
    public void deleteStation(Long id){

        ChargingStation chargingStation = chargingStationRepository.findById(id)
                .orElseThrow(() -> new ChargingStationNotFoundException("Station not found"));

        chargingStationRepository.delete(chargingStation);
    }
}
