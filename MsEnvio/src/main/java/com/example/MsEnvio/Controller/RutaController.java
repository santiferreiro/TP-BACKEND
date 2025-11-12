package com.example.MsEnvio.Controller;

import com.example.MsEnvio.Service.RutaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    // 🔹 Asignar una ruta a una solicitud
    @PostMapping("/solicitudes/{id}/asignar-ruta")
    public ResponseEntity<String> asignarRuta(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body) {

        Long idRuta = body.get("rutaId");
        rutaService.asignarRuta(id, idRuta);
        return ResponseEntity.ok("Ruta asignada correctamente a la solicitud " + id);
    }

}
