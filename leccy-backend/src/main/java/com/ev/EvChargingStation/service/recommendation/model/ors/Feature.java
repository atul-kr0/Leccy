package com.ev.EvChargingStation.service.recommendation.model.ors;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Feature(
        Geometry geometry,
        Properties properties
) {}