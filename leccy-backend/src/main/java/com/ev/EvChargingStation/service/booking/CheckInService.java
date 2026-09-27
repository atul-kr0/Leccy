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

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final BookingRepository bookingRepository;
    private final ChargingSessionService chargingSessionService;

    @Transactional
    public String checkIn(String token) {

        if (token == null || token.isBlank()) {
            throw new InvalidTokenException(
                    "Please enter your booking token."
            );
        }

        String cleanToken = token.trim();

        Booking booking = bookingRepository
                .findByTokenNumber(cleanToken)
                .orElseThrow(() ->
                        new InvalidTokenException(
                                "Invalid token. Please check and try again."
                        )
                );

        switch (booking.getStatus()) {

            case WAITING ->
                    throw new BookingStatusInvalidException(
                            "It's not your turn yet. Please wait for your notification."
                    );

            case NOTIFIED -> {
                if (booking.getCharger() == null) {
                    throw new ChargerUnavailableException(
                            "No charger is assigned to this booking. Please contact support."
                    );
                }

                if (booking.getCharger().getChargerStatus()
                        != ChargerStatus.AVAILABLE) {

                    throw new ChargerUnavailableException(
                            "Your assigned charger is currently unavailable. Please contact support."
                    );
                }

                // Starts the session and updates the booking/charger
                // according to your existing ChargingSessionService.
                chargingSessionService.startSession(booking);

                return "Check-in successful! Your charging session has started.";
            }

            case CANCELLED ->
                    throw new BookingStatusInvalidException(
                            "This booking has been cancelled. Please join the queue again."
                    );

            case EXPIRED ->
                    throw new BookingStatusInvalidException(
                            "This booking has expired. Please join the queue again."
                    );

            case CHARGING ->
                    throw new BookingStatusInvalidException(
                            "Your charging session is already in progress."
                    );

            case COMPLETED ->
                    throw new BookingStatusInvalidException(
                            "This charging session has already been completed."
                    );
        }

        // Defensive fallback if another status is added in the future.
        throw new BookingStatusInvalidException(
                "This booking cannot be checked in."
        );
    }
}