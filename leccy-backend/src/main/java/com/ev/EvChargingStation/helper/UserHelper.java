package com.ev.EvChargingStation.helper;

import com.ev.EvChargingStation.entity.User;
import com.ev.EvChargingStation.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHelper {

    private final SecurityUtil securityUtil;

    public User getLoggedInUser(){
        return securityUtil.getCurrentUser();
    }
}
