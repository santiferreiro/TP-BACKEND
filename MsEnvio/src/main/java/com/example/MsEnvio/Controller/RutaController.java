package com.example.MsEnvio.Controller;

import com.example.MsEnvio.DTO.RutaTentativaDTO;
import com.example.MsEnvio.Models.Ruta;
import com.example.MsEnvio.Service.RutaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    // ===============================
    // 📌 Crear una nueva Ruta
    // ===============================
    @PostMapping
    public ResponseEntity<Ruta> crearRuta(@RequestBody Ruta ruta) {
        Ruta nueva = rutaService.create(ruta);
        return ResponseEntity.ok(nueva);
    }

    // ===============================
    // 📌 Obtener todas las rutas
    // ===============================
    @GetMapping
    public ResponseEntity<List<Ruta>> obtenerRutas() {
        return ResponseEntity.ok(rutaService.getAll());
    }

    // ===============================
    // 📌 Obtener una ruta por ID
    // ===============================
    @GetMapping("/{id}")
    public ResponseEntity<Ruta> obtenerRuta(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.getById(id));
    }

    // ===============================
    // 📌 Eliminar una ruta por ID
    // ===============================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRuta(@PathVariable Long id) {
        rutaService.delete(id);
        return ResponseEntity.ok("Ruta eliminada correctamente");
    }

    // ===============================
    // 📌 Asignar una ruta a una solicitud
    // ===============================
    @PostMapping("/solicitudes/{id}/asignar-ruta")
    public ResponseEntity<String> asignarRuta(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body) {

        Long idRuta = body.get("rutaId");
        rutaService.asignarRuta(id, idRuta);
        return ResponseEntity.ok("Ruta asignada correctamente a la solicitud " + id);
    }

    @GetMapping("/tentativa")
    public RutaTentativaDTO obtenerRutaTentativa(
            @RequestParam double origenLat,
            @RequestParam double origenLon,
            @RequestParam double destinoLat,
            @RequestParam double destinoLon) {

        return rutaService.obtenerRutaTentativa(
                origenLat, origenLon,
                destinoLat, destinoLon
        );
    }
    @PostMapping("/confirmar")
    public ResponseEntity<String> confirmarRuta(@RequestBody RutaTentativaDTO dto) {
        rutaService.confirmarRuta(dto);
        return ResponseEntity.ok("Ruta confirmada correctamente");
    }
}
