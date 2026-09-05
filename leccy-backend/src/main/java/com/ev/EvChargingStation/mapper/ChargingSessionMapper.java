package com.ev.EvChargingStation.mapper;

import com.ev.EvChargingStation.entity.Booking;
import com.ev.EvChargingStation.entity.ChargingSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChargingSessionMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "booking", source = "booking")
    @Mapping(target = "charger", source = "charger")

    @Mapping(target = "initialBatteryPercentage",
            source = "currentBatteryPercentage")

    @Mapping(target = "targetBatteryPercentage",
            source = "targetBatteryPercentage")

    @Mapping(target = "estimatedEnergyRequired", ignore = true)

    @Mapping(target = "pricePerKwh",
            source = "chargingStation.pricePerKwh")

    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "plannedEndTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)

    @Mapping(target = "endedEarly",
            constant = "false")

    @Mapping(target = "energyDelivered",
            constant = "0.0")

    @Mapping(target = "chargingAmount",
            constant = "0.0")

    @Mapping(target = "penaltyAmount",
            constant = "0.0")

    @Mapping(target = "totalAmount",
            constant = "0.0")

    @Mapping(target = "paymentStatus", ignore = true)

    ChargingSession toEntity(Booking booking);
}