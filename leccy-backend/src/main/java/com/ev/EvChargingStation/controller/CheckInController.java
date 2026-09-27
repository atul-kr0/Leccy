package com.ev.EvChargingStation.controller;

import com.ev.EvChargingStation.dto.chargingSession.CheckInRequestDTO;
import com.ev.EvChargingStation.exception.BookingStatusInvalidException;
import com.ev.EvChargingStation.exception.ChargerUnavailableException;
import com.ev.EvChargingStation.exception.InvalidTokenException;
import com.ev.EvChargingStation.service.booking.CheckInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/check-in")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping(
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> checkIn(
            @Valid @RequestBody CheckInRequestDTO requestDTO) {

        String message = checkInService.checkIn(
                requestDTO.getToken()
        );

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(message);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidToken(
            InvalidTokenException exception) {

        return errorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

    @ExceptionHandler(BookingStatusInvalidException.class)
    public ResponseEntity<String> handleInvalidBookingStatus(
            BookingStatusInvalidException exception) {

        return errorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }

    @ExceptionHandler(ChargerUnavailableException.class)
    public ResponseEntity<String> handleChargerUnavailable(
            ChargerUnavailableException exception) {

        return errorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }

    private ResponseEntity<String> errorResponse(
            HttpStatus status,
            String message) {

        return ResponseEntity.status(status)
                .contentType(MediaType.TEXT_PLAIN)
                .body(message);
    }
}