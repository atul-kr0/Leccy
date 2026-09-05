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
public class BookingHistoryResponseDTO {

    private Long bookingId;
    private String stationName;
    private String tokenNumber;
    private BookingStatus status;
    private LocalDateTime bookedAt;
    private LocalDateTime completedAt;
}
