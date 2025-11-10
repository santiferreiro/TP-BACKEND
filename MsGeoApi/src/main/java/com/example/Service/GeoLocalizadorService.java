package com.example.Service;


import com.example.Model.GeoLocalizador;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class GeoLocalizadorService {

    @Value("${google.maps.apikey}")
    private String apiKey;

    private final RestClient.Builder builder;

    public GeoLocalizador calcularDistancia(String origen, String destino) throws Exception {
        RestClient client = builder
                .baseUrl("https://maps.googleapis.com/maps/api")
                .build();

        String url = "/distancematrix/json?origins=" + origen +
                "&destinations=" + destino +
                "&units=metric&key=" + apiKey;

        ResponseEntity<String> response = client.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode leg = root.path("rows").get(0).path("elements").get(0);

        GeoLocalizador dto = new GeoLocalizador();
        dto.setOrigen(origen);
        dto.setDestino(destino);
        dto.setKilometros(leg.path("distance").path("value").asDouble() / 1000);
        dto.setDuracionTexto(leg.path("duration").path("text").asText());

        return dto;
    }
}

