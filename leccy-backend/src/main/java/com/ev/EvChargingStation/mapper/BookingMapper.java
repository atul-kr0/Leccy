package com.ev.EvChargingStation.mapper;

import com.ev.EvChargingStation.dto.booking.BookingRequestDTO;
import com.ev.EvChargingStation.dto.booking.BookingResponseDTO;
import com.ev.EvChargingStation.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking toEntity(BookingRequestDTO request);

    @Mapping(target = "bookingId", source = "id")
    @Mapping(target = "stationName", source = "chargingStation.stationName")
    @Mapping(target = "chargerId", source = "charger.id")
    @Mapping(target = "chargerNumber", source = "charger.chargerNumber")
    BookingResponseDTO entityToDto(Booking booking);
}
