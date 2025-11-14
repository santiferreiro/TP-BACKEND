package com.example.MsLogistica.Controller;

import com.example.MsLogistica.Models.Camion;
import com.example.MsLogistica.Service.CamionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/camiones")
public class CamionController {

    private final CamionService camionService;

    public CamionController(CamionService camionService) {
        this.camionService = camionService;
    }

    // ====================================================
    // 1️⃣ BUSCAR CAMIÓN POR PATENTE
    // ====================================================
    @GetMapping("/{patente}")
    public ResponseEntity<Camion> obtenerPorPatente(@PathVariable String patente) {

        Camion camion = camionService.buscarPorPatente(patente);
        return ResponseEntity.ok(camion);
    }
    @PostMapping
    public ResponseEntity<Camion> crearCamion(@RequestBody Camion camion) {
        Camion creado = camionService.crearCamion(camion);
        return ResponseEntity.ok(creado);
    }

    // ====================================================
    // 2️⃣ VALIDAR CAPACIDAD DE CAMIÓN VS CONTENEDOR
    // ====================================================
    @GetMapping("/{patente}/validar-capacidad/{idContenedor}")
    public ResponseEntity<String> validarCapacidad(
            @PathVariable String patente,
            @PathVariable Long idContenedor) {

        camionService.validarCapacidad(patente, idContenedor);
        return ResponseEntity.ok("✔️ El contenedor es compatible con el camión " + patente);
    }
}
