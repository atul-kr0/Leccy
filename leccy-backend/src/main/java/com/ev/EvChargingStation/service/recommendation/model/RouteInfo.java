package com.ev.EvChargingStation.service.recommendation.model;

import com.ev.EvChargingStation.service.recommendation.model.ors.Geometry;

import java.util.List;

public record RouteInfo(

        double drivingDistanceKm,

        int estimatedTravelTimeMinutes,

        List<List<Double>> routeCoordinates
) {}