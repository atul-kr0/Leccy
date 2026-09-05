package com.ev.EvChargingStation.dto.openchargemap;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionTypeDTO {

    @JsonProperty("Title")
    private String title;
}