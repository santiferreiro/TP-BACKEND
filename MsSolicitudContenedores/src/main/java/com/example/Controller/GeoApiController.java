package com.example.Controller;

import com.example.DTO.GeoAPI;
import com.example.Service.GeoApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/distancia")
@RequiredArgsConstructor
public class GeoApiController {
    private final GeoApiService geoService;
    @GetMapping
    public GeoAPI obtenerDistancia(@RequestParam String origen,
            @RequestParam String destino) throws Exception {
                return geoService.calcularDistancia(origen, destino);
    }
}
