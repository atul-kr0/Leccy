package com.ev.EvChargingStation.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {

    private Integer totalStations;
    private Integer totalChargers;
    private Integer availableChargers;
    private Integer activeBookings;
    private Integer activeSessions;
    private Double totalRevenue;
}
