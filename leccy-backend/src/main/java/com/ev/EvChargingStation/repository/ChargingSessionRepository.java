package com.ev.EvChargingStation.repository;

import com.ev.EvChargingStation.entity.ChargingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChargingSessionRepository extends JpaRepository<ChargingSession,Long> {

    Optional<ChargingSession> findByBookingId(Long bookingId);

    List<ChargingSession>
    findByEndTimeIsNullAndPlannedEndTimeBefore(
            LocalDateTime time);
}
