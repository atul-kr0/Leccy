package com.ev.EvChargingStation.repository;

import com.ev.EvChargingStation.entity.ChargingStation;
import com.ev.EvChargingStation.enums.StationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChargingStationRepository
        extends JpaRepository<ChargingStation, Long> {

    boolean existsByOpenChargeMapId(Long openChargeMapId);

    List<ChargingStation> findByStationStatus(
            StationStatus stationStatus
    );

    @Query("""
        SELECT s.openChargeMapId
        FROM ChargingStation s
        WHERE s.openChargeMapId IS NOT NULL
    """)
    List<Long> findAllOpenChargeMapIds();
}