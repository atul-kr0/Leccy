package com.ev.EvChargingStation.dto.auth;

import com.ev.EvChargingStation.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String token;
    private String type;
    private Long userId;
    private String fullName;
    private String email;
    private Role role;
}
