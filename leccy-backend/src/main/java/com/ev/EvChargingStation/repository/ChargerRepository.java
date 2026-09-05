package com.ev.EvChargingStation.repository;

import com.ev.EvChargingStation.entity.Charger;
import com.ev.EvChargingStation.entity.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChargerRepository extends JpaRepository<Charger,Long> {

    List<Charger> findByChargingStation(ChargingStation chargingStation);

    List<Charger> findByChargingStationId(Long stationId);
}
