package com.ev.EvChargingStation.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    private Long vehicleId;
    private Long stationId;
    private Integer currentBatteryPercentage;
    private Integer targetBatteryPercentage;
}
