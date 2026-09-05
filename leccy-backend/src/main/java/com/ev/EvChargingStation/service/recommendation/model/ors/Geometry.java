package com.ev.EvChargingStation.service.recommendation.model.ors;

import java.util.List;

public record Geometry(
        String type,
        List<List<Double>> coordinates
) {}
