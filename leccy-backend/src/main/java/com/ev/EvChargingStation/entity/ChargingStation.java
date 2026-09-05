package com.ev.EvChargingStation.entity;

import com.ev.EvChargingStation.enums.StationStatus;
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
public class ChargingStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;

    private String address;

    private Double latitude;

    private Double longitude;

    private Double pricePerKwh;

    private Double rating;

    @Enumerated(EnumType.STRING)
    private StationStatus stationStatus;

    @OneToMany(
            mappedBy = "chargingStation",
            fetch = FetchType.LAZY
    )
    private List<Charger> chargers = new ArrayList<>();

    @OneToMany(
            mappedBy = "chargingStation",
            fetch = FetchType.LAZY
    )    private List<Booking> bookings = new ArrayList<>();

    @Column(unique = true)
    private Long openChargeMapId;
}
