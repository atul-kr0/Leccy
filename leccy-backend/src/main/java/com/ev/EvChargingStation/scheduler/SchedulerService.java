package com.ev.EvChargingStation.scheduler;

import com.ev.EvChargingStation.entity.Booking;
import com.ev.EvChargingStation.entity.ChargingSession;
import com.ev.EvChargingStation.enums.BookingStatus;
import com.ev.EvChargingStation.repository.BookingRepository;
import com.ev.EvChargingStation.repository.ChargingSessionRepository;
import com.ev.EvChargingStation.service.booking.CompleteChargingService;
import com.ev.EvChargingStation.service.booking.NotifyNextService;
import com.ev.EvChargingStation.service.booking.ReBalanceStation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SchedulerService {

    private static final long NO_SHOW_TIMEOUT_MINUTES = 10;

    private final BookingRepository bookingRepository;
    private final ReBalanceStation reBalanceStation;
    private final NotifyNextService notifyNextService;
    private final ChargingSessionRepository chargingSessionRepository;
    private final CompleteChargingService completeChargingService;

    @Scheduled(fixedRate = 60000)
    public void expireNoShowBookings() {

        List<Booking> expiredBookings =
                bookingRepository.findByStatusAndNotifiedAtBefore(
                        BookingStatus.NOTIFIED,
                        LocalDateTime.now().minusMinutes(NO_SHOW_TIMEOUT_MINUTES)
                );

        if (expiredBookings.isEmpty()) {
            return;
        }

        Set<Long> affectedStations = new HashSet<>();

        for (Booking booking : expiredBookings) {

            booking.setStatus(BookingStatus.EXPIRED);

            affectedStations.add(
                    booking.getChargingStation().getId()
            );

            log.info(
                    "Booking {} expired due to no-show.",
                    booking.getId()
            );
        }

        bookingRepository.saveAll(expiredBookings);

        for (Long stationId : affectedStations) {

            reBalanceStation.optimizeStationQueue(stationId);

            notifyNextService.notifyEligibleBookings(stationId);
        }
    }

    @Scheduled(fixedRate = 60000)
    public void completeFinishedSessions() {

        List<ChargingSession> sessions =
                chargingSessionRepository
                        .findByEndTimeIsNullAndPlannedEndTimeBefore(
                                LocalDateTime.now());

        for (ChargingSession session : sessions) {

            completeChargingService.completeCharging(session);
        }
    }

}