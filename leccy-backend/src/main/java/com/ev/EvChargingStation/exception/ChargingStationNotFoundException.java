package com.ev.EvChargingStation.exception;

public class ChargingStationNotFoundException extends RuntimeException {
    public ChargingStationNotFoundException(String message) {
        super(message);
    }
}
