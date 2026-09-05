package com.ev.EvChargingStation.dto.chargingStation;

import com.ev.EvChargingStation.enums.StationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargingStationRequestDTO {

    private String stationName;
    private String address;
    private Double latitude;
    private Double longitude;
    private Double pricePerKwh;
    private StationStatus stationStatus;
}
