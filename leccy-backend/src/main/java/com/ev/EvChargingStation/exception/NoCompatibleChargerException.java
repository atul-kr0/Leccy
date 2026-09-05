package com.ev.EvChargingStation.exception;

public class NoCompatibleChargerException extends RuntimeException {

    public NoCompatibleChargerException(String message) {
        super(message);
    }
}
