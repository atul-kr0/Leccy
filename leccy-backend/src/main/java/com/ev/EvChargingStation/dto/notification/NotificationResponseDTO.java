package com.ev.EvChargingStation.dto.notification;

import com.ev.EvChargingStation.entity.Notification;
import com.ev.EvChargingStation.enums.NotificationStatus;
import com.ev.EvChargingStation.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {

    private Long id;
    private String title;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
    private LocalDateTime createdAt;
}
