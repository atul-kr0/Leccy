package com.ev.EvChargingStation.mapper;

import com.ev.EvChargingStation.dto.chargingStation.ChargingStationRequestDTO;
import com.ev.EvChargingStation.dto.chargingStation.ChargingStationResponseDTO;
import com.ev.EvChargingStation.entity.ChargingStation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StationMapper {

    ChargingStation toEntity(ChargingStationRequestDTO dto);

    ChargingStationResponseDTO toResponseDto(ChargingStation station);

    void updateEntityFromDto(ChargingStationRequestDTO dto, @MappingTarget ChargingStation entity);
}
