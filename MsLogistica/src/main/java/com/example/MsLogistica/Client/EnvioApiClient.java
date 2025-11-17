package com.example.MsLogistica.Client;

import com.example.MsLogistica.Dto.TramoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class EnvioApiClient {

    private static final String BASE_URL = "http://ms-envio:8083"; // Puerto de MsEnvio

    private final RestClient envioClient;

    public EnvioApiClient() {
        this.envioClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    public TramoDTO obtenerTramoPorId(Long idTramo) {
        String uri = "/api/tramos/" + idTramo;
        return envioClient.get()
                .uri(uri)
                .retrieve()
                .body(TramoDTO.class);
    }

    // 2️⃣ VALIDAR TRANSPORTISTA ASIGNADO AL TRAMO
    public boolean validarTransportistaEnTramo(Long idTramo, Long idTransportista) {

        String uri = "api/tramos/" + idTramo + "/validar-transportista/" + idTransportista;
        return envioClient.get()
                .uri(uri)
                .retrieve()
                .body(Boolean.class);
    }
    // 3️⃣ MÉTODO GENÉRICO PARA CAMBIAR ESTADO DEL TRAMO
    public String cambiarEstadoTramo(Long idTramo, String nuevoEstado) {

        String uri = "api/tramos/" + idTramo + "/estado/" + nuevoEstado;
        return envioClient.put()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }
    // 4️⃣ INICIAR TRAMO (setea fechaInicio)
    public void iniciarTramo(Long idTramo) {
        String uri = "/api/tramos/" + idTramo + "/inicio";
        envioClient.put()
                .uri(uri)
                .retrieve()
                .toBodilessEntity();
    }

    // 5️⃣ FINALIZAR TRAMO (setea fechaFin)
    public void finalizarTramo(Long idTramo) {
        String uri = "/api/tramos/" + idTramo + "/fin";
        envioClient.put()
                .uri(uri)
                .retrieve()
                .toBodilessEntity();
    }
}
