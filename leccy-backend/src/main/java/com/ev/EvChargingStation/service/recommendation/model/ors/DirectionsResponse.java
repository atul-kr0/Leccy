package com.ev.EvChargingStation.service.recommendation.model.ors;

import java.util.List;

public record DirectionsResponse(

        List<Feature> features

) {}