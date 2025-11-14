package com.example.MsLogistica.Client;


import com.example.MsLogistica.Dto.ContenedorDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SolicitudApiClient {

    private final RestClient restClient = RestClient.create();

    // ESTA ES LA URL CORRECTA
    private static final String BASE_URL = "http://localhost:8081/api/contenedores/";

    public ContenedorDTO obtenerContenedor(Long idContenedor) {
        return restClient.get()
                .uri(BASE_URL + idContenedor)
                .retrieve()
                .body(ContenedorDTO.class);
    }
}