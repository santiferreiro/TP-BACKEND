package com.example.Service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class GeoLocalizadorService {

    private static final String OSRM_URL =
            "http://localhost:5000/route/v1/driving/%f,%f;%f,%f?overview=false";

    public double calcularDistanciaKm(double lon1, double lat1, double lon2, double lat2) {
        String url = String.format(Locale.US, OSRM_URL, lon1, lat1, lon2, lat2);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        double distanciaMetros = json
                .getJSONArray("routes")
                .getJSONObject(0)
                .getDouble("distance");

        return distanciaMetros / 1000.0; // convertir a km
    }
}