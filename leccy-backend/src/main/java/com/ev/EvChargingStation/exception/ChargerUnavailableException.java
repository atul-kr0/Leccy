package com.ev.EvChargingStation.exception;

public class ChargerUnavailableException extends RuntimeException {
    public ChargerUnavailableException(String message) {
        super(message);
    }
}
