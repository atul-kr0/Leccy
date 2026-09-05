package com.ev.EvChargingStation.service.booking;

import com.ev.EvChargingStation.entity.Booking;
import com.ev.EvChargingStation.entity.Charger;
import com.ev.EvChargingStation.enums.BookingStatus;
import com.ev.EvChargingStation.enums.ChargerStatus;
import com.ev.EvChargingStation.repository.BookingRepository;
import com.ev.EvChargingStation.repository.ChargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotifyNextService {

    private final ChargerRepository chargerRepository;
    private final BookingRepository bookingRepository;

    public void notifyEligibleBookings(Long stationId) {

        List<Charger> chargers =
                chargerRepository.findByChargingStationId(stationId);

        for (Charger charger : chargers) {

            // Charger must be free
            if (charger.getChargerStatus() != ChargerStatus.AVAILABLE) {
                continue;
            }

            // Get the head of this charger's queue
            Optional<Booking> optionalBooking =
                    bookingRepository.findFirstByChargerIdAndStatusInOrderByBookedAtAsc(
                            charger.getId(),
                            List.of(BookingStatus.WAITING, BookingStatus.NOTIFIED)
                    );

            if (optionalBooking.isEmpty()) {
                continue;
            }

            Booking booking = optionalBooking.get();

            // Someone has already been notified for this charger
            if (booking.getStatus() == BookingStatus.NOTIFIED) {
                continue;
            }

            // Notify the first waiting booking
            booking.setStatus(BookingStatus.NOTIFIED);
            booking.setNotifiedAt(LocalDateTime.now());

            bookingRepository.save(booking);

            System.out.println("""
=========================
USER NOTIFIED
User    : %s
Station : %s
Charger : %s
Token   : %s
=========================
""".formatted(
                    booking.getUser().getName(),
                    booking.getChargingStation().getStationName(),
                    booking.getCharger().getChargerNumber(),
                    booking.getTokenNumber()
            ));

        }
    }
}