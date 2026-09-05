package com.ev.EvChargingStation.helper;

import com.ev.EvChargingStation.entity.Charger;
import com.ev.EvChargingStation.enums.BookingStatus;
import com.ev.EvChargingStation.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueueHelper {

    private final BookingRepository bookingRepository;

    public Integer calculateQueuePosition(Charger charger) {

        if (charger == null) {
            throw new IllegalArgumentException("Charger cannot be null.");
        }

        Integer activeBookings =
                bookingRepository.countByChargerAndStatusIn(
                        charger,
                        List.of(
                                BookingStatus.CHARGING,
                                BookingStatus.WAITING
                        )
                );

        return activeBookings + 1;
    }
}
