package com.ev.EvChargingStation.entity;

import com.ev.EvChargingStation.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private ChargingStation chargingStation;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

//    private Integer queuePosition;

    private LocalDateTime bookedAt;

    @PrePersist
    public void onCreate() {
        this.bookedAt = LocalDateTime.now();
    }

    private Integer estimatedChargingDuration;

    private LocalDateTime checkedInAt;

    private LocalDateTime completedAt;

    private String tokenNumber;

    private LocalDateTime notifiedAt;

    private LocalDateTime expiresAt;

    @OneToOne(
            mappedBy = "booking",
            fetch = FetchType.LAZY
    )
    private ChargingSession chargingSession;

    @OneToMany(
            mappedBy = "booking",
            fetch = FetchType.LAZY
    )
    private List<Notification> notifications = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charger_id", nullable = false)
    private Charger charger;

    private Integer currentBatteryPercentage;

    private Integer targetBatteryPercentage;
}
