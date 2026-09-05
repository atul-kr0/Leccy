package com.ev.EvChargingStation.dto.chargingSession;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckInRequestDTO {

    @NotBlank(message = "Token is required.")
    private String token;
}