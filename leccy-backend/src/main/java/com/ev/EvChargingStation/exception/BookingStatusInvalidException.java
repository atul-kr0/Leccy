package com.ev.EvChargingStation.exception;

public class BookingStatusInvalidException extends RuntimeException {
    public BookingStatusInvalidException(String message) {
        super(message);
    }
}
