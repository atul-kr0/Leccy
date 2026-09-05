package com.ev.EvChargingStation.service.recommendation;

import com.ev.EvChargingStation.exception.RouteServiceException;
import com.ev.EvChargingStation.service.recommendation.model.RouteInfo;
import com.ev.EvChargingStation.service.recommendation.model.ors.DirectionsRequest;
import com.ev.EvChargingStation.service.recommendation.model.ors.DirectionsResponse;
import com.ev.EvChargingStation.service.recommendation.model.ors.Feature;
import com.ev.EvChargingStation.service.recommendation.model.ors.Segment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenRouteServiceClient {

    private static final String DIRECTIONS_ENDPOINT =
            "/v2/directions/driving-car/geojson";

    private final RestClient restClient;

    @Value("${ors.api.key}")
    private String apiKey;

    @Value("${ors.base-url}")
    private String baseUrl;

    public RouteInfo getRoute(
            double userLatitude,
            double userLongitude,
            double stationLatitude,
            double stationLongitude
    ) {

        DirectionsRequest request =
                new DirectionsRequest(
                        List.of(
                                List.of(userLongitude, userLatitude),
                                List.of(stationLongitude, stationLatitude)
                        )
                );

        try {
            DirectionsResponse response =
                    restClient.post()
                            .uri(baseUrl + DIRECTIONS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", apiKey)
                            .body(request)
                            .retrieve()
                            .body(DirectionsResponse.class);

            if (response == null
                    || response.features() == null
                    || response.features().isEmpty()) {

                throw new RouteServiceException(
                        "OpenRouteService returned an empty response."
                );
            }

            Feature feature = response.features().getFirst();

            if (feature.properties() == null
                    || feature.properties().segments() == null
                    || feature.properties().segments().isEmpty()) {

                throw new RouteServiceException(
                        "No route segment found in OpenRouteService response."
                );
            }

            Segment segment =
                    feature.properties().segments().getFirst();

            return new RouteInfo(
                    segment.distance() / 1000.0,
                    (int) Math.ceil(segment.duration() / 60.0),
                    feature.geometry().coordinates()            );

        } catch (RestClientException ex) {

            throw new RouteServiceException(
                    "Failed to communicate with OpenRouteService.",
                    ex);
        }
    }
}