package com.example.Controller;

import com.example.Dto.DistanciaResponse;
import com.example.Service.GeoLocalizadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rutas")
public class GeoLocalizadorController {

    private final GeoLocalizadorService geoLocalizadorService;

    public GeoLocalizadorController(GeoLocalizadorService geoLocalizadorService) {
        this.geoLocalizadorService = geoLocalizadorService;
    }

    @GetMapping("/distancia")
    public ResponseEntity<DistanciaResponse> obtenerDistancia(
            @RequestParam double lon1, @RequestParam double lat1,
            @RequestParam double lon2, @RequestParam double lat2) {

        double distanciaKm = Math.round(geoLocalizadorService.calcularDistanciaKm(lon1, lat1, lon2, lat2) * 100.0) / 100.0;
        DistanciaResponse respuesta = new DistanciaResponse(distanciaKm);
        return ResponseEntity.ok(respuesta);
    }
}
