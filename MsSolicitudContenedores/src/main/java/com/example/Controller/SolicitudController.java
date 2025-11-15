package com.example.Controller;




import com.example.DTO.TramoDTO;
import com.example.Models.Solicitud;
import com.example.Service.SolicitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    // 🔹 GET /api/solicitudes → listar todas las solicitudes
    @GetMapping
    public ResponseEntity<List<Solicitud>> listarSolicitudes() {
        List<Solicitud> solicitudes = service.getAll();
        return ResponseEntity.ok(solicitudes);
    }

    // 🔹 GET /api/solicitudes/{id} → obtener una solicitud por ID
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> obtenerPorId(@PathVariable Long id) {
        Solicitud solicitud = service.getById(id);
        return ResponseEntity.ok(solicitud);
    }

    // 🔹 POST /api/solicitudes → crear nueva solicitud
    @PostMapping
    public ResponseEntity<Solicitud> crearSolicitud(@RequestBody Solicitud solicitud) {
        Solicitud nueva = service.create(solicitud);
        return ResponseEntity.ok(nueva);
    }

    // 🔹 GET /api/solicitudes?estado={estado}&cliente={dni}
    // permite filtrar solicitudes por estado o cliente
    @GetMapping(params = {"estado", "cliente"})
    public ResponseEntity<List<Solicitud>> filtrarSolicitudes(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String cliente) {

        List<Solicitud> resultado = service.filtrarSolicitudes(estado, cliente);
        return ResponseEntity.ok(resultado);
    }
    @PutMapping("/{id}/ruta/{idRuta}")
    public ResponseEntity<Solicitud> asignarRuta(
            @PathVariable Long id,
            @PathVariable Long idRuta) {
        return ResponseEntity.ok(service.asignarRuta(id, idRuta));
    }

    @GetMapping("/{idSolicitud}/estimar-costo")
    public Double estimarCosto(@PathVariable Long idSolicitud) {
        return service.estimarCostoTotal(idSolicitud);
    }
    @PutMapping("/{idSolicitud}/tarifa/{idTarifa}")
    public Solicitud asignarTarifa(
            @PathVariable Long idSolicitud,
            @PathVariable Long idTarifa
    ) {
        return service.asignarTarifa(idSolicitud, idTarifa);
    }
    @GetMapping("/{idSolicitud}/tramos")
    public ResponseEntity<List<TramoDTO>> verTramos(@PathVariable Long idSolicitud) {
        List<TramoDTO> tramos = service.verTramos(idSolicitud);
        return ResponseEntity.ok(tramos);
    }
}
