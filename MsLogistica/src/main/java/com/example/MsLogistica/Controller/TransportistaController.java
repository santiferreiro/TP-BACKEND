package com.example.MsLogistica.Controller;

import com.example.MsLogistica.Models.Transportista;
import com.example.MsLogistica.Service.TransportistaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transportistas")
public class TransportistaController {

    private final TransportistaService transportistaService;

    public TransportistaController(TransportistaService transportistaService) {
        this.transportistaService = transportistaService;
    }

    // ==========================================================
    // 1️⃣ OBTENER TODOS LOS TRANSPORTISTAS
    // ==========================================================
    @GetMapping
    public ResponseEntity<List<Transportista>> obtenerTodos() {
        List<Transportista> lista = transportistaService.buscarTodos();
        return ResponseEntity.ok(lista);
    }

    // ==========================================================
    // 2️⃣ CREAR UN NUEVO TRANSPORTISTA
    // ==========================================================
    @PostMapping
    public ResponseEntity<Transportista> crear(@RequestBody Transportista transportista) {
        Transportista creado = transportistaService.crearTransportista(transportista);
        return ResponseEntity.ok(creado);
    }
}
