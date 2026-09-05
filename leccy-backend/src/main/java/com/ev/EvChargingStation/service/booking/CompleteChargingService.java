package com.ev.EvChargingStation.service.booking;

import com.ev.EvChargingStation.dto.billing.BillingSummary;
import com.ev.EvChargingStation.entity.Booking;
import com.ev.EvChargingStation.entity.ChargingSession;
import com.ev.EvChargingStation.enums.BookingStatus;
import com.ev.EvChargingStation.enums.ChargerStatus;
import com.ev.EvChargingStation.enums.PaymentStatus;
import com.ev.EvChargingStation.repository.BookingRepository;
import com.ev.EvChargingStation.repository.ChargingSessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompleteChargingService {

    private final BillingService billingService;
    private final ChargingSessionRepository chargingSessionRepository;
    private final BookingRepository bookingRepository;
    private final ReBalanceStation reBalanceStation;
    private final NotifyNextService notifyNextService;

    @Transactional
    public void completeCharging(ChargingSession session){

        LocalDateTime completedAt = LocalDateTime.now();

        session.setEndTime(completedAt);

        session.setEndedEarly(
                completedAt.isBefore(
                        session.getPlannedEndTime()
                )
        );

        BillingSummary bill =
                billingService.generateBill(session);

        session.setEnergyDelivered(
                bill.energyDelivered());

        session.setPenaltyAmount(
                bill.penaltyAmount());

        session.setTotalAmount(
                bill.totalAmount());

        session.setPaymentStatus(
                PaymentStatus.PENDING);

        Booking booking = session.getBooking();

        booking.setStatus(
                BookingStatus.COMPLETED);

        booking.getCharger().setChargerStatus(
                ChargerStatus.AVAILABLE);

        chargingSessionRepository.save(session);

        bookingRepository.save(booking);

        Long stationId =
                booking.getChargingStation().getId();

        reBalanceStation.optimizeStationQueue(stationId);

        notifyNextService.notifyEligibleBookings(stationId);
    }
}
