package com.ev.EvChargingStation.dto.chargingSession;

import com.ev.EvChargingStation.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChargingSessionResponseDTO {

    private Long bookingId;

    private String stationName;

    private String chargerNumber;

    private LocalDateTime startTime;

    private LocalDateTime plannedEndTime;

    private LocalDateTime endTime;

    private Double energyDelivered;

    private Double chargingAmount;

    private Double penaltyAmount;

    private Double totalAmount;

    private PaymentStatus paymentStatus;
}