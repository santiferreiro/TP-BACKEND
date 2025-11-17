package com.example.MsEnvio.Client;

import com.example.MsEnvio.DTO.CamionDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LogisticaApiClient {

    private final RestClient restClient = RestClient.create();

    private static final String BASE_URL = "http://ms-logistica:8082/camiones/";

    // 👉 VALIDAR CAPACIDAD
    public String validarCapacidad(String patente, Long idContenedor) {
        return restClient.get()
                .uri(BASE_URL + patente + "/validar-capacidad/" + idContenedor)
                .retrieve()
                .body(String.class);
    }
    // 👉 NUEVO: validar disponibilidad del camión
    public String validarDisponibilidad(String patente) {
        return restClient.get()
                .uri(BASE_URL + patente + "/validar-disponibilidad")
                .retrieve()
                .body(String.class);
    }
    // 3️⃣ Ocupar camión (disponible = false)
    public String ocuparCamion(String patente) {
        return restClient.put()
                .uri(BASE_URL + patente + "/ocupar")
                .retrieve()
                .body(String.class);
    }
    // 👉 Obtener camión por su patente
    // ============================================================
    public CamionDTO obtenerCamionPorPatente(String patente) {

        String uri = BASE_URL + patente;
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(CamionDTO.class);
    }
}