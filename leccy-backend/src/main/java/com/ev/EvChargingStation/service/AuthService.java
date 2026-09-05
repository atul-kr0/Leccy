package com.ev.EvChargingStation.service;

import com.ev.EvChargingStation.dto.auth.AuthResponseDTO;
import com.ev.EvChargingStation.dto.auth.LoginRequestDTO;
import com.ev.EvChargingStation.dto.auth.LoginResponseDTO;
import com.ev.EvChargingStation.dto.auth.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);
}
