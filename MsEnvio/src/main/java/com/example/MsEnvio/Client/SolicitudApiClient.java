package com.example.MsEnvio.Client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class SolicitudApiClient {

    private final RestClient solicitudClient;

    public SolicitudApiClient(RestClient solicitudClient) {
        this.solicitudClient = solicitudClient;
    }

    // 🔹 Acá vas a agregar los métodos para llamar al microservicio de SolicitudContenedores
    public void asignarRuta(Long idSolicitud, Long idRuta) {
        // URL: POST /api/solicitudes/{id}/asignar-ruta
        String uri = "/api/solicitudes/" + idSolicitud + "/asignar-ruta";

        // Cuerpo JSON que se enviará al otro microservicio
        Map<String, Object> requestBody = Map.of("rutaId", idRuta);

        // Envío de la petición POST
        solicitudClient.post()
                .uri(uri)
                .body(requestBody)
                .retrieve()
                .toBodilessEntity();
    }
}