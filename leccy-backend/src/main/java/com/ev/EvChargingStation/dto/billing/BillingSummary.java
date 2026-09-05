package com.ev.EvChargingStation.dto.billing;

public record BillingSummary(

        double energyDelivered,
        double chargingAmount,
        double penaltyAmount,
        double totalAmount

) {
}