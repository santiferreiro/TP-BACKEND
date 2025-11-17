package com.example.Client;

import com.example.DTO.CamionDTO;
import com.example.DTO.RutaDTO;
import com.example.DTO.TramoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class EnvioApiClient {

    private final RestClient restClient = RestClient.create();

    private static final String BASE_URL = "http://ms-envio:8083/api";

    // Cambialo según puerto real de MsEnvio

    public RutaDTO getRutaById(Long idRuta) {
        return restClient.get()
                .uri(BASE_URL + "/rutas/" + idRuta)
                .retrieve()
                .body(RutaDTO.class);
    }
    public TramoDTO getTramoById(Long idTramo) {
        return restClient.get()
                .uri(BASE_URL + "/tramos/" + idTramo)
                .retrieve()
                .body(TramoDTO.class);
    }

}