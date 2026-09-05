package com.ev.EvChargingStation.service.booking;

import com.ev.EvChargingStation.entity.Booking;
import com.ev.EvChargingStation.enums.BookingStatus;
import com.ev.EvChargingStation.enums.ChargerStatus;
import com.ev.EvChargingStation.exception.BookingStatusInvalidException;
import com.ev.EvChargingStation.exception.ChargerUnavailableException;
import com.ev.EvChargingStation.exception.InvalidTokenException;
import com.ev.EvChargingStation.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.ev.EvChargingStation.enums.BookingStatus.CHARGING;
import static com.ev.EvChargingStation.enums.ChargerStatus.BUSY;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final BookingRepository bookingRepository;
    private final ChargingSessionService chargingSessionService;

    @Transactional
    public void checkIn(String token) {

        Booking booking = bookingRepository.findByTokenNumber(token)
                .orElseThrow(() -> new InvalidTokenException("Invalid token."));

        switch (booking.getStatus()) {

            case WAITING ->
                    throw new BookingStatusInvalidException(
                            "It's not your turn yet. Please wait for your notification."
                    );

            case CANCELLED ->
                    throw new BookingStatusInvalidException(
                            "This booking has been cancelled."
                    );

            case EXPIRED ->
                    throw new BookingStatusInvalidException(
                            "This booking has expired."
                    );

            case CHARGING ->
                    throw new BookingStatusInvalidException(
                            "Charging session is already in progress."
                    );

            case COMPLETED ->
                    throw new BookingStatusInvalidException(
                            "Charging session has already been completed."
                    );

            case NOTIFIED -> {

                if (booking.getCharger().getChargerStatus() != ChargerStatus.AVAILABLE) {
                    throw new ChargerUnavailableException(
                            "Assigned charger is currently unavailable."
                    );
                }

                booking.setCheckedInAt((LocalDateTime.now()));

                chargingSessionService.startSession(booking);
            }
        }

//        booking.setStatus(BookingStatus.CHECKED_IN);

    }
}