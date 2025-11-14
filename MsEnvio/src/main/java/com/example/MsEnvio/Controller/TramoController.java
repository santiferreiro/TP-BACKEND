package com.example.MsEnvio.Controller;

import com.example.MsEnvio.Models.Tramo;
import com.example.MsEnvio.Service.TramoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tramos")
public class TramoController {

    private final TramoService tramoService;

    public TramoController(TramoService tramoService) {
        this.tramoService = tramoService;
    }
    // ===============================
    // 📌 Obtener todos los tramos
    // ===============================
    @GetMapping
    public ResponseEntity<List<Tramo>> obtenerTodos() {
        return ResponseEntity.ok(tramoService.getAll());
    }

    // ===============================
    // 📌 Obtener tramo por ID
    // ===============================
    @GetMapping("/{id}")
    public ResponseEntity<Tramo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tramoService.getById(id));
    }

    // ===============================
    // 📌 Crear tramo asociado a una Ruta
    // ===============================
    @PostMapping(
            value = "/ruta/{idRuta}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<Tramo> crearParaRuta(
            @PathVariable Long idRuta,
            @RequestBody Tramo tramo) {

        Tramo creado = tramoService.createForRuta(idRuta, tramo);
        return ResponseEntity.ok(creado);
    }

}
