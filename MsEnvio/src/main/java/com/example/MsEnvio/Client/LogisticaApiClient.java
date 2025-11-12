package com.example.MsEnvio.Client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LogisticaApiClient {

    private final RestClient logisticaClient;

    public LogisticaApiClient(RestClient logisticaClient) {
        this.logisticaClient = logisticaClient;
    }

    // 🔹 Acá vas a agregar los métodos para llamar al microservicio de Logística

}
