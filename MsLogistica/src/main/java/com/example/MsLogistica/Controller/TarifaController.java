package com.example.MsLogistica.Controller;

import com.example.MsLogistica.Models.Tarifa;
import com.example.MsLogistica.Service.TarifaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @GetMapping("/{idTarifa}")
    public Tarifa getTarifaById(@PathVariable Long idTarifa) {
        return tarifaService.getById(idTarifa);
    }
    @PostMapping
    public Tarifa createTarifa(@RequestBody Tarifa tarifa) {
        return tarifaService.createTarifa(tarifa);
    }
}