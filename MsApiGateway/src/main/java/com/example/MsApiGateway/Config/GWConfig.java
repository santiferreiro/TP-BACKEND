package com.example.MsApiGateway.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${gateway.url-solicitud}") String uriSolicitud,
                                        @Value("${gateway.url-logistica}") String uriLogistica,
                                        @Value("${gateway.url-envio}") String uriEnvio,
                                        @Value("${gateway.url-geo}") String uriGeo) {

        return builder.routes()

                // =============================================================
                // 🔵 MsSolicitudContenedores - Puerto 8081
                // =============================================================
                .route(p -> p.path("/api/clientes/**").uri(uriSolicitud))
                .route(p -> p.path("/api/contenedores/**").uri(uriSolicitud))
                .route(p -> p.path("/api/seguimientos/**").uri(uriSolicitud))
                .route(p -> p.path("/api/solicitudes/**").uri(uriSolicitud))

                // =============================================================
                // 🟢 MsLogistica - Puerto 8082
                // (Tus controllers NO tienen "/api", así que ruteamos directo)
                // =============================================================
                .route(p -> p.path("/camiones/**").uri(uriLogistica))
                .route(p -> p.path("/depositos/**").uri(uriLogistica))
                .route(p -> p.path("/tarifas/**").uri(uriLogistica))
                .route(p -> p.path("/transportistas/**").uri(uriLogistica))

                // =============================================================
                // 🟣 MsEnvio - Puerto 8083
                // =============================================================
                .route(p -> p.path("/api/tramos/**").uri(uriEnvio))
                .route(p -> p.path("/api/rutas/**").uri(uriEnvio))

                // =============================================================
                // 🔶 MsGeoApi - Puerto 8084
                // =============================================================
                .route(p -> p.path("/geo/**").uri(uriGeo))

                .build();
    }
}
