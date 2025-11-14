package com.example.MsEnvio.Client;

import com.example.MsEnvio.DTO.RutaTentativaDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Locale;

@Component
public class GeoApiClient {

    private final RestClient restClient = RestClient.create();

    private static final String GEO_API_URL =
            "http://localhost:8084/geo/ruta-tentativa?origenLat=%f&origenLon=%f&destinoLat=%f&destinoLon=%f";

    public RutaTentativaDTO obtenerRutaTentativa(
            double latOrigen, double lonOrigen,
            double latDestino, double lonDestino) {

        String url = String.format(
                Locale.US,
                GEO_API_URL,
                latOrigen, lonOrigen,
                latDestino, lonDestino
        );

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(RutaTentativaDTO.class);
    }
}
