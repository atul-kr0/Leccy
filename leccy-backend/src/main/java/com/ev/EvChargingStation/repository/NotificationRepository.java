package com.ev.EvChargingStation.repository;

import com.ev.EvChargingStation.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
