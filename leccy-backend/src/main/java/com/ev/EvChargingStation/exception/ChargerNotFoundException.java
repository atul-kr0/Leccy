package com.ev.EvChargingStation.exception;

public class ChargerNotFoundException extends RuntimeException {
    public ChargerNotFoundException(String message) {
        super(message);
    }
}
