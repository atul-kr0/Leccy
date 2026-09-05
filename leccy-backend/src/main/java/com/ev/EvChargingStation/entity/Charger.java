package com.ev.EvChargingStation.entity;

import com.ev.EvChargingStation.enums.ChargerStatus;
import com.ev.EvChargingStation.enums.ConnectorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Charger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chargerNumber;

    @Enumerated(EnumType.STRING)
    private ConnectorType connectorType;

    private Double outputPower; // kW

    @Enumerated(EnumType.STRING)
    private ChargerStatus chargerStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private ChargingStation chargingStation;

    @OneToMany(
            mappedBy = "charger",
            fetch = FetchType.LAZY
    )
    private List<ChargingSession> chargingSessions = new ArrayList<>();

    @OneToMany(
            mappedBy = "charger",
            fetch = FetchType.LAZY
    )
    private List<Booking> bookings = new ArrayList<>();
}
