package com.example.Controller;

import com.example.Dto.DistanciaResponse;
import com.example.Dto.RutaTentativa;
import com.example.Service.GeoLocalizadorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geo")
public class GeoLocalizadorController {

    private final GeoLocalizadorService service;

    public GeoLocalizadorController(GeoLocalizadorService service) {
        this.service = service;
    }
    @GetMapping("/distancia")
    public DistanciaResponse obtenerDistancia(
            @RequestParam double origenLat,
            @RequestParam double origenLon,
            @RequestParam double destinoLat,
            @RequestParam double destinoLon
    ) {
        double distancia = service.calcularDistancia(
                origenLat, origenLon, destinoLat, destinoLon
        );

        return new DistanciaResponse(distancia);
    }

    @GetMapping("/ruta-tentativa")
    public RutaTentativa obtenerRutaTentativa(
            @RequestParam double origenLat,
            @RequestParam double origenLon,
            @RequestParam double destinoLat,
            @RequestParam double destinoLon) {

        return service.obtenerRutaTentativa(
                origenLat, origenLon,
                destinoLat, destinoLon
        );
    }
}
