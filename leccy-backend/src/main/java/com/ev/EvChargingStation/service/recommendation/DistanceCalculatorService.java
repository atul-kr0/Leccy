package com.ev.EvChargingStation.service.recommendation;

import com.ev.EvChargingStation.exception.InvalidCoordinatesException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistanceCalculatorService {

    private static final double EARTH_RADIUS_KM = 6371.0;

    /**
     * Calculates the great-circle distance between two geographic coordinates
     * using the Haversine formula.
     *
     * @return Distance in kilometers.
     */
    public double calculateDistance(
            double userLatitude,
            double userLongitude,
            double stationLatitude,
            double stationLongitude
    ) {

        validateCoordinates(userLatitude, userLongitude);
        validateCoordinates(stationLatitude, stationLongitude);

        double latitudeDifference =
                Math.toRadians(stationLatitude - userLatitude);

        double longitudeDifference =
                Math.toRadians(stationLongitude - userLongitude);

        double userLatitudeRadians =
                Math.toRadians(userLatitude);

        double stationLatitudeRadians =
                Math.toRadians(stationLatitude);

        double haversineValue =
                Math.sin(latitudeDifference / 2)
                        * Math.sin(latitudeDifference / 2)
                        + Math.cos(userLatitudeRadians)
                        * Math.cos(stationLatitudeRadians)
                        * Math.sin(longitudeDifference / 2)
                        * Math.sin(longitudeDifference / 2);

        double centralAngle =
                2 * Math.atan2(
                        Math.sqrt(haversineValue),
                        Math.sqrt(1 - haversineValue)
                );

        return EARTH_RADIUS_KM * centralAngle;
    }

    private void validateCoordinates(
            double latitude,
            double longitude
    ) {

        if (latitude < -90 || latitude > 90) {
            throw new InvalidCoordinatesException("Latitude must be between -90 and 90.");
        }

        if (longitude < -180 || longitude > 180) {
            throw new InvalidCoordinatesException ("Longitude must be between -180 and 180.");
        }
    }
}