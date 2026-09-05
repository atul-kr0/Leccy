package com.ev.EvChargingStation.dto.openchargemap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenChargeMapResponse {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("AddressInfo")
    private AddressInfoDTO addressInfo;

    @JsonProperty("Connections")
    private List<ConnectionDTO> connections;
}