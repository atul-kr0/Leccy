package com.ev.EvChargingStation.entity;

import com.ev.EvChargingStation.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "charging_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charger_id", nullable = false)
    private Charger charger;

    // ---------------- Battery Details ----------------

    private Double initialBatteryPercentage;

    private Double targetBatteryPercentage;

    private Double estimatedEnergyRequired;

    // ---------------- Session Lifecycle ----------------

    private LocalDateTime startTime;

    private LocalDateTime plannedEndTime;

    private Integer actualChargingDuration;

    private LocalDateTime endTime;

    private Boolean endedEarly = false;

    // ---------------- Billing ----------------

    /**
     * Tariff at the time the charging session started.
     * Stored to preserve historical pricing.
     */
    private Double pricePerKwh;

    /**
     * Actual energy delivered during the session.
     */
    private Double energyDelivered = 0.0;

    /**
     * Charging cost excluding penalties.
     */
    private Double chargingAmount = 0.0;

    /**
     * Penalty for early termination.
     */
    private Double penaltyAmount = 0.0;

    /**
     * Final payable amount.
     */
    private Double totalAmount = 0.0;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}