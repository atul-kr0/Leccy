package com.ev.EvChargingStation.service.booking;


import com.ev.EvChargingStation.entity.Booking;
import com.ev.EvChargingStation.enums.BookingStatus;
import com.ev.EvChargingStation.helper.BookingValidationHelper;
import com.ev.EvChargingStation.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CancelBookingService {

    private final BookingValidationHelper bookingValidationHelper;
    private final BookingRepository bookingRepository;
    private final ReBalanceStation reBalanceStation;
    private final NotifyNextService notifyNextService;

    public void cancelBooking(Long bookingId){

        Booking booking = bookingValidationHelper.getOwnedBooking(bookingId);

        bookingValidationHelper.validateCancellation(booking);

        Long stationId = booking.getChargingStation().getId();

        booking.setStatus(BookingStatus.CANCELLED);

        reBalanceStation.optimizeStationQueue(stationId);

        notifyNextService.notifyEligibleBookings(stationId);
    }

}
