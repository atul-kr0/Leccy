package com.ev.EvChargingStation.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "openchargemap.api")
@Getter
@Setter
public class OpenChargeMapProperties {

    private String key;

}