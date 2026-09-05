package com.ev.EvChargingStation.exception;

public class StationUnavailableException extends RuntimeException {
    public StationUnavailableException(String message) {
        super(message);
    }
}
