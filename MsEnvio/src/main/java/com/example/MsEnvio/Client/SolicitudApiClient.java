package com.example.MsEnvio.Client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class SolicitudApiClient {

    private final RestClient solicitudClient;

    // 💡 BASE_URL del microservicio MsSolicitudContenedores
    private static final String BASE_URL = "http://ms-solicitud:8081";


    public SolicitudApiClient() {
        this.solicitudClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    // 1️⃣ Asignar ruta a solicitud
    public void asignarRuta(Long idSolicitud, Long idRuta) {

        String uri = "/api/solicitudes/" + idSolicitud + "/asignar-ruta";

        Map<String, Object> requestBody = Map.of("rutaId", idRuta);

        solicitudClient.post()
                .uri(uri)
                .body(requestBody)
                .retrieve()
                .toBodilessEntity();
    }

    // 2️⃣ Cambiar estado del contenedor
    public String cambiarEstadoContenedor(Long idContenedor, String nuevoEstado) {

        String uri = "/api/contenedores/" + idContenedor + "/estado/" + nuevoEstado;

        return solicitudClient.put()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }
}
