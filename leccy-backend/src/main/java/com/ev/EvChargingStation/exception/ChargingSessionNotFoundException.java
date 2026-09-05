package com.ev.EvChargingStation.exception;

public class ChargingSessionNotFoundException extends RuntimeException {
    public ChargingSessionNotFoundException(String message) {
        super(message);
    }
}
