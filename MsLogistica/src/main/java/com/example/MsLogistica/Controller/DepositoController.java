package com.example.MsLogistica.Controller;

import com.example.MsLogistica.Models.Deposito;
import com.example.MsLogistica.Service.DepositoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depositos")
public class DepositoController {

    private final DepositoService depositoService;

    public DepositoController(DepositoService depositoService) {
        this.depositoService = depositoService;
    }

    // ==========================================================
    // 1️⃣ OBTENER TODOS LOS DEPÓSITOS
    // ==========================================================
    @GetMapping
    public ResponseEntity<List<Deposito>> obtenerTodos() {
        List<Deposito> depositos = depositoService.buscarTodos();
        return ResponseEntity.ok(depositos);
    }

    // ==========================================================
    // 2️⃣ CREAR UN DEPÓSITO
    // ==========================================================
    @PostMapping
    public ResponseEntity<Deposito> crear(@RequestBody Deposito deposito) {
        Deposito creado = depositoService.crearDeposito(deposito);
        return ResponseEntity.ok(creado);
    }
}
