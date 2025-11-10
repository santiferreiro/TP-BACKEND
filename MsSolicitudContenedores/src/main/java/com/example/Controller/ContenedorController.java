package com.example.Controller;
import com.example.Models.Contenedor;
import com.example.Service.ContenedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contenedores")
public class ContenedorController {

    private final ContenedorService service;

    public ContenedorController(ContenedorService service) {
        this.service = service;
    }

    // 🔹 GET /api/contenedores → obtener todos los contenedores
    @GetMapping
    public ResponseEntity<List<Contenedor>> listarContenedores() {
        List<Contenedor> contenedores = service.getAll();
        return ResponseEntity.ok(contenedores);
    }

    // 🔹 GET /api/contenedores/{id} → buscar un contenedor por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Contenedor> obtenerPorId(@PathVariable Long id) {
        Contenedor contenedor = service.getById(id);
        return ResponseEntity.ok(contenedor);
    }

    // 🔹 POST /api/contenedores → agregar un nuevo contenedor
    @PostMapping("/test")
    public ResponseEntity<Contenedor> crearContenedor(@RequestBody Contenedor contenedor) {
        Contenedor nuevo = service.create(contenedor);
        return ResponseEntity.ok(nuevo);
    }
}