package com.ev.EvChargingStation.dto.booking;

import com.ev.EvChargingStation.entity.Booking;
import com.ev.EvChargingStation.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {

    private Long bookingId;
    private String stationName;
    private Long chargerId;
    private String chargerNumber;
    private String tokenNumber;
    private BookingStatus status;
    private LocalDateTime bookedAt;
    private LocalDateTime notifiedAt;
    private LocalDateTime expiresAt;
    private Integer estimatedChargingDuration;
}
