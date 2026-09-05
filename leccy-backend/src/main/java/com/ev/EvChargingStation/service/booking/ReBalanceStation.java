package com.ev.EvChargingStation.service.booking;

import com.ev.EvChargingStation.dto.booking.BookingRequestDTO;
import com.ev.EvChargingStation.dto.booking.ChargerSelectionResult;
import com.ev.EvChargingStation.entity.Booking;
import com.ev.EvChargingStation.enums.BookingStatus;
import com.ev.EvChargingStation.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
public class ReBalanceStation {

    private static final int REASSIGNMENT_THRESHOLD = 10;

    private final BookingRepository bookingRepository;
    private final ChargerPickerService chargerPickerService;

    public void optimizeStationQueue(Long stationId) {

        List<Booking> bookings =
                bookingRepository.findByChargingStationIdAndStatusOrderByBookedAtAsc(
                        stationId,
                        BookingStatus.WAITING
                );

        for (Booking booking : bookings) {
            recalculatePrediction(booking);
        }

        // TODO:
        // promoteEligibleBookings(stationId);
    }

    private void recalculatePrediction(Booking booking) {

        ChargerSelectionResult currentPrediction =
                chargerPickerService.calculatePrediction(
                        booking.getCharger(),
                        booking.getVehicle(),
                        booking.getCurrentBatteryPercentage(),
                        booking.getTargetBatteryPercentage(),
                        booking.getId() // Exclude itself
                );

        ChargerSelectionResult bestPrediction =
                chargerPickerService.pickFastestCompletionCharger(
                        booking.getChargingStation(),
                        booking.getVehicle(),
                        booking.getCurrentBatteryPercentage(),
                        booking.getTargetBatteryPercentage()
                );

        if (booking.getCharger().getId()
                .equals(bestPrediction.getCharger().getId())) {
            return;
        }

        if (isWorthReassigning(currentPrediction, bestPrediction)) {

            booking.setCharger(bestPrediction.getCharger());

            booking.setEstimatedChargingDuration(
                    bestPrediction.getEstimatedChargingDuration()
            );
        }
    }

    private boolean isWorthReassigning(
            ChargerSelectionResult currentPrediction,
            ChargerSelectionResult bestPrediction) {

        return currentPrediction.getEstimatedCompletionTime()
                - bestPrediction.getEstimatedCompletionTime()
                >= REASSIGNMENT_THRESHOLD;
    }
}