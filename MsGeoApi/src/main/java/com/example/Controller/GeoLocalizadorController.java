package com.example.Controller;


import lombok.RequiredArgsConstructor;
import com.example.Model.GeoLocalizador;
import com.example.Service.GeoLocalizadorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/distancia")
@RequiredArgsConstructor
public class GeoLocalizadorController {
    private final GeoLocalizadorService geoService;

    @GetMapping
    public GeoLocalizador obtenerDistancia(@RequestParam String origen,
           @RequestParam String destino) throws Exception {
                return geoService.calcularDistancia(origen, destino);
    }
}

