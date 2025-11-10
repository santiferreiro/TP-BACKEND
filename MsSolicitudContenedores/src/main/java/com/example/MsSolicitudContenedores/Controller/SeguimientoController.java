package com.example.MsSolicitudContenedores.Controller;

import com.example.MsSolicitudContenedores.Models.Seguimiento;
import com.example.MsSolicitudContenedores.Service.SeguimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoController {

    private final SeguimientoService service;

    public SeguimientoController(SeguimientoService service) {
        this.service = service;
    }

    // 🔹 GET /api/seguimientos/contenedor/{id}
    // Devuelve los estados del contenedor en orden cronológico
    @GetMapping("/contenedor/{id}")
    public ResponseEntity<List<Seguimiento>> obtenerSeguimientoPorContenedor(@PathVariable Long id) {
        List<Seguimiento> estados = service.listarPorContenedor(id);
        return ResponseEntity.ok(estados);
    }
}

