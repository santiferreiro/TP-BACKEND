package com.example.Service;

import com.example.Dto.RutaTentativa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class GeoLocalizadorService {

    private final RestClient restClient = RestClient.create();

    // Se lee desde application.properties
    @Value("${osrm.url}")
    private String osrmBaseUrl;

    public RutaTentativa obtenerRutaTentativa(
            double origenLat, double origenLon,
            double destinoLat, double destinoLon) {

        // IMPORTANTE: OSRM usa formato → LON,LAT
        String url = String.format(
                Locale.US,
                "%s/route/v1/driving/%.6f,%.6f;%.6f,%.6f?overview=false&steps=false",
                osrmBaseUrl,
                origenLon, origenLat,        // lon,lat
                destinoLon, destinoLat       // lon,lat
        );

        Map<String, Object> response = restClient.get()
                .uri(url)
                .retrieve()
                .body(Map.class);

        Map<String, Object> ruta = ((List<Map<String, Object>>) response.get("routes"))
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("OSRM no devolvió rutas"));

        double distancia = ((Number) ruta.get("distance")).doubleValue();
        double duracion = ((Number) ruta.get("duration")).doubleValue();

        return RutaTentativa.builder()
                .origenLat(origenLat)
                .origenLon(origenLon)
                .destinoLat(destinoLat)
                .destinoLon(destinoLon)
                .distanciaMetros(distancia)
                .tiempoSegundos(duracion)
                .build();
    }

    public double calcularDistancia(
            double origenLat, double origenLon,
            double destinoLat, double destinoLon) {

        String url = String.format(
                Locale.US,
                "%s/route/v1/driving/%.6f,%.6f;%.6f,%.6f?overview=false&steps=false",
                osrmBaseUrl,
                origenLon, origenLat,     // lon,lat
                destinoLon, destinoLat    // lon,lat
        );

        Map response = restClient.get()
                .uri(url)
                .retrieve()
                .body(Map.class);

        List routes = (List) response.get("routes");
        Map route0 = (Map) routes.get(0);

        double distanciaMetros = ((Number) route0.get("distance")).doubleValue();

        return distanciaMetros / 1000.0;
    }
}
