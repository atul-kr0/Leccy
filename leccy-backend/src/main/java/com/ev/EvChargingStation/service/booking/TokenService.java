package com.ev.EvChargingStation.service.booking;

import com.ev.EvChargingStation.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final int TOKEN_LENGTH = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final BookingRepository bookingRepository;

    public String generateUniqueToken() {

        String token;

        do {
            token = generateRandomToken();
        } while (bookingRepository.existsByTokenNumber(token));

        return token;
    }

    private String generateRandomToken() {

        StringBuilder token = new StringBuilder(TOKEN_LENGTH);

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(RANDOM.nextInt(10));
        }

        return token.toString();
    }
}