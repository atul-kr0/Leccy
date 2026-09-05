package com.ev.EvChargingStation.helper;

import com.ev.EvChargingStation.entity.Vehicle;
import com.ev.EvChargingStation.exception.VehicleNotFoundException;
import com.ev.EvChargingStation.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleHelper {

    private final VehicleRepository vehicleRepository;
    private final UserHelper userHelper;

    public Vehicle getVehicleById(Long vehicleId){

        return vehicleRepository.findById(vehicleId)
                .orElseThrow(()-> new VehicleNotFoundException("Vehicle not found: "+vehicleId));
    }

    public Vehicle validateUsersVehicle(Long vehicleId){

        Vehicle vehicle = getVehicleById(vehicleId);

        if (!vehicle.getUser().getId().equals(userHelper.getLoggedInUser().getId())) {
            throw new IllegalArgumentException(
                    "Vehicle does not belong to the logged-in user."
            );
        }

        return vehicle;
    }
}
