package com.ev.EvChargingStation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EvChargingStationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvChargingStationApplication.class, args);
	}

}
