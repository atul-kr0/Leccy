package com.ev.EvChargingStation.service.booking;

import com.ev.EvChargingStation.entity.Booking;
import com.ev.EvChargingStation.entity.ChargingSession;
import com.ev.EvChargingStation.enums.BookingStatus;
import com.ev.EvChargingStation.exception.BookingNotFoundException;
import com.ev.EvChargingStation.exception.ChargingSessionNotFoundException;
import com.ev.EvChargingStation.exception.InvalidTokenException;
import com.ev.EvChargingStation.helper.UserHelper;
import com.ev.EvChargingStation.repository.BookingRepository;
import com.ev.EvChargingStation.repository.ChargingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StopChargingService {

    private final UserHelper userHelper;
    private final BookingRepository bookingRepository;
    private final ChargingSessionRepository chargingSessionRepository;
    private final CompleteChargingService completeChargingService;

    public void stopCharging(String token) {

        Booking booking = getActiveChargingBooking();

        validateToken(token, booking);

        ChargingSession session = getChargingSession(booking);

        completeChargingService.completeCharging(session);
    }

    private Booking getActiveChargingBooking() {

        return bookingRepository.findByUserAndStatus(
                        userHelper.getLoggedInUser(),
                        BookingStatus.CHARGING
                )
                .orElseThrow(() ->
                        new BookingNotFoundException(
                                "No active charging session found."
                        ));
    }

    private void validateToken(String token, Booking booking) {

        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Token is required.");
        }

        if (!booking.getTokenNumber().equals(token)) {
            throw new InvalidTokenException("Invalid token.");
        }
    }

    private ChargingSession getChargingSession(Booking booking) {

        return chargingSessionRepository.findByBookingId(booking.getId())
                .orElseThrow(() ->
                        new ChargingSessionNotFoundException(
                                "Charging session not found."
                        ));
    }
}