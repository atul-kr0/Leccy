package com.ev.EvChargingStation.mapper;

import com.ev.EvChargingStation.dto.charger.ChargerRequestDTO;
import com.ev.EvChargingStation.dto.charger.ChargerResponseDTO;
import com.ev.EvChargingStation.entity.Charger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChargerMapper {

    Charger toEntity(ChargerRequestDTO request);

    @Mapping(target = "stationId", source = "chargingStation.id")
    @Mapping(target = "stationName", source = "chargingStation.stationName")
    ChargerResponseDTO toResponseDto(Charger charger);

    void updateEntityFromDto(ChargerRequestDTO requestDTO, @MappingTarget Charger charger);
}
