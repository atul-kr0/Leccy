package com.ev.EvChargingStation.exception;

public class BookingNotCancellableException extends RuntimeException{

    public BookingNotCancellableException(String message) {
        super(message);
    }
}
