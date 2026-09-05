package com.ev.EvChargingStation.service.booking;

import com.ev.EvChargingStation.entity.Booking;
import com.ev.EvChargingStation.entity.ChargingSession;
import com.ev.EvChargingStation.enums.BookingStatus;
import com.ev.EvChargingStation.enums.ChargerStatus;
import com.ev.EvChargingStation.enums.PaymentStatus;
import com.ev.EvChargingStation.mapper.ChargingSessionMapper;
import com.ev.EvChargingStation.repository.BookingRepository;
import com.ev.EvChargingStation.repository.ChargingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
class ChargingSessionService {

    private final ChargingSessionRepository chargingSessionRepository;
    private final BookingRepository bookingRepository;
    private final ChargingSessionMapper chargingSessionMapper;

    void startSession(Booking booking) {

        ChargingSession session =
                chargingSessionMapper.toEntity(booking);

        LocalDateTime startTime = LocalDateTime.now();

        session.setStartTime(startTime);

        session.setPlannedEndTime(
                startTime.plusMinutes(
                        booking.getEstimatedChargingDuration()
                )
        );

        session.setPaymentStatus(PaymentStatus.PENDING);

        booking.setStatus(BookingStatus.CHARGING);

        booking.getCharger()
                .setChargerStatus(ChargerStatus.BUSY);

        ChargingSession saved =
                chargingSessionRepository.save(session);

        bookingRepository.save(booking);
    }
}