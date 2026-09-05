package com.ev.EvChargingStation.service.recommendation.model.ors;

import java.util.List;

public record DirectionsRequest(

        List<List<Double>> coordinates

) {}