package com.example.Client;

import com.example.DTO.CamionDTO;
import com.example.DTO.TarifaDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LogisticaApiClient {

    private final RestClient restClient = RestClient.create();

    private static final String BASE_URL = "http://ms-logistica:8082";


    public TarifaDTO getTarifaById(Long idTarifa) {
        return restClient.get()
                .uri(BASE_URL + "/tarifas/" + idTarifa)
                .retrieve()
                .body(TarifaDTO.class);
    }
    public CamionDTO getCamionByPatente(String patente) {
        return restClient.get()
                .uri(BASE_URL + "/camiones/" + patente)
                .retrieve()
                .body(CamionDTO.class);
    }
}
