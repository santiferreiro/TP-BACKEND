package com.example.Controller;

import com.example.Models.Cliente;
import com.example.Service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // 🔹 POST /api/clientes → agregar un nuevo cliente si no existe
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente nuevo = service.crear(cliente);
        return ResponseEntity.ok(nuevo);
    }

    // 🔹 GET /api/clientes → obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = service.getAll();
        return ResponseEntity.ok(clientes);
    }

    // 🔹 GET /api/clientes/{dni} → obtener un cliente por DNI
    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> obtenerPorDni(@PathVariable String dni) {
        Cliente cliente = service.getByDni(dni);
        return ResponseEntity.ok(cliente);
    }
}
