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
public class ChargingStationResponseDTO {

    private Long id;
    private String stationName;
    private String address;
    private Double latitude;
    private Double longitude;
    private Double pricePerKwh;
    private Double rating;
    private Integer totalChargers;
    private Integer availableChargers;
    private StationStatus stationStatus;
}
