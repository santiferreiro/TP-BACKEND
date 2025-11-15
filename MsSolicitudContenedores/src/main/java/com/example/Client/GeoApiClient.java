package com.example.Client;

import com.example.DTO.DistanciaResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Locale;

@Component
public class GeoApiClient {

    private static final String BASE_URL = "http://localhost:8084/geo";
    private final RestClient restClient = RestClient.create();

    public Double obtenerDistancia(
            double origenLat, double origenLon,
            double destinoLat, double destinoLon) {

        String url = String.format(
                Locale.US,
                BASE_URL + "/distancia?origenLat=%.6f&origenLon=%.6f&destinoLat=%.6f&destinoLon=%.6f",
                origenLon, origenLat,   // 🚨 SE INVIERTEN
                destinoLon, destinoLat  // 🚨 SE INVIERTEN
        );

        System.out.println("URL GENERADA = " + url);

        DistanciaResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(DistanciaResponse.class);

        return response.getDistancia();
    }

}

