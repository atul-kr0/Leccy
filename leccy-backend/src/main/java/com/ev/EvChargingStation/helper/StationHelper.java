package com.ev.EvChargingStation.helper;

import com.ev.EvChargingStation.entity.ChargingStation;
import com.ev.EvChargingStation.exception.ChargingStationNotFoundException;
import com.ev.EvChargingStation.repository.ChargingStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StationHelper {

    private final ChargingStationRepository stationRepository;

    public ChargingStation getStation(Long stationId) {

        return stationRepository.findById(stationId)
                .orElseThrow(() ->
                        new ChargingStationNotFoundException(
                                "Station not found: " + stationId));
    }
}
