package com.ev.EvChargingStation.dto.openchargemap;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInfoDTO {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("AddressLine1")
    private String addressLine1;

    @JsonProperty("Town")
    private String town;

    @JsonProperty("Latitude")
    private Double latitude;

    @JsonProperty("Longitude")
    private Double longitude;
}