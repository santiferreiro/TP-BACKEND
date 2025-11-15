package com.example.Service;

import com.example.Dto.RutaTentativa;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Locale;

@Service
public class GeoLocalizadorService {

    private final RestClient restClient = RestClient.create();

    private static final String OSRM_URL =
            "http://localhost:5000/route/v1/driving/%f,%f;%f,%f?overview=false&steps=false";

    public RutaTentativa obtenerRutaTentativa(
            double origenLat, double origenLon,
            double destinoLat, double destinoLon) {

        // Forzar Locale.US para que los decimales usen punto (OSRM requiere puntos)
        String url = String.format(
                Locale.US,
                OSRM_URL,
                origenLat, origenLon,  // lon,lat
                 destinoLat,destinoLon  // lon,lat
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
            double destinoLat, double destinoLon
    ) {
        // OSRM usa formato LON,LAT → ATENCIÓN AL ORDEN
        String url = String.format(
                Locale.US,
                OSRM_URL,
                origenLat, origenLon,  // lon,lat
                destinoLat,destinoLon  // lon,lat
        );
        // Llamada a OSRM
        Map response = restClient.get()
                .uri(url)
                .retrieve()
                .body(Map.class);

        // routes[0].distance → viene en METROS
        List routes = (List) response.get("routes");
        Map route0 = (Map) routes.get(0);

        double distanciaMetros = ((Number) route0.get("distance")).doubleValue();

        // Convertimos a kilómetros
        return distanciaMetros / 1000.0;
    }

}