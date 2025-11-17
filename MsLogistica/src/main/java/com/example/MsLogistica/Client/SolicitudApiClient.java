package com.example.MsLogistica.Client;


import com.example.MsLogistica.Dto.ContenedorDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SolicitudApiClient {

    private final RestClient restClient = RestClient.create();

    // ESTA ES LA URL CORRECTA
    private static final String BASE_URL = "http://ms-solicitud:8081/api/contenedores/";


    public ContenedorDTO obtenerContenedor(Long idContenedor) {
        return restClient.get()
                .uri(BASE_URL + idContenedor)
                .retrieve()
                .body(ContenedorDTO.class);
    }
    // 2️⃣ Cambiar estado del contenedor
    public String cambiarEstadoContenedor(Long idContenedor, String nuevoEstado) {

        String uri = BASE_URL + idContenedor + "/estado/" + nuevoEstado;

        return restClient.put()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }
}