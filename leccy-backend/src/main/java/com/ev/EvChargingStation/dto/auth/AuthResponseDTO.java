package com.ev.EvChargingStation.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ev.EvChargingStation.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private Long userId;
    private String fullName;
    private String email;
    private String token;
    private Role role;
}
