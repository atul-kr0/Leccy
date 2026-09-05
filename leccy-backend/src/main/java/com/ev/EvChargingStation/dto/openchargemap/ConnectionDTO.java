package com.ev.EvChargingStation.dto.openchargemap;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionDTO {

    @JsonProperty("Quantity")
    private Integer quantity;

    @JsonProperty("PowerKW")
    private Double powerKW;

    @JsonProperty("ConnectionType")
    private ConnectionTypeDTO connectionType;
}