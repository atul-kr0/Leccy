package com.ev.EvChargingStation.repository;

import com.ev.EvChargingStation.entity.Charger;
import com.ev.EvChargingStation.entity.ChargingStation;
import com.ev.EvChargingStation.enums.StationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  ChargingStationRepository extends JpaRepository<ChargingStation,Long> {

    boolean existsByOpenChargeMapId(Long openChargeMapId);

    List<ChargingStation> findByStationStatus(
            StationStatus stationStatus
    );
}
