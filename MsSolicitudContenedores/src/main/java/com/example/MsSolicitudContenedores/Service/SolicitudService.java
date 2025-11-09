package com.example.MsSolicitudContenedores.Service;

import com.example.MsSolicitudContenedores.Models.Cliente;
import com.example.MsSolicitudContenedores.Models.Contenedor;
import com.example.MsSolicitudContenedores.Models.Solicitud;
import com.example.MsSolicitudContenedores.Models.EstadoContenedor;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.MsSolicitudContenedores.Repository.SolicitudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ClienteService clienteService;
    private final ContenedorService contenedorService;

    public SolicitudService(SolicitudRepository solicitudRepository, ClienteService clienteService, ContenedorService contenedorService) {
        this.solicitudRepository = solicitudRepository;
        this.clienteService = clienteService;
        this.contenedorService = contenedorService;
    }
    // Crear una nueva solicitud
    @Transactional
    public Solicitud create(Solicitud solicitud) {
        // 1️⃣ Delegamos al ClienteService la lógica de obtener o crear cliente
        Cliente cliente = clienteService.crear(solicitud.getCliente());
        solicitud.setCliente(cliente);

        // 2️⃣ Validar contenedor
        Contenedor cont = solicitud.getContenedor();
        if (cont == null || cont.getPeso() == null || cont.getVolumen() == null) {
            throw new IllegalArgumentException("Debe especificarse peso y volumen del contenedor.");
        }

        // Crear contenedor
        Contenedor contenedor = contenedorService.create(cont);
        solicitud.setContenedor(contenedor);

        // 3️⃣ Validamos que la solicitud tenga ubicaciones de origen y destino
        if (solicitud.getOrigen() == null || solicitud.getDestino() == null) {
            throw new IllegalArgumentException("Debe especificarse una ubicación de origen y destino.");
        }

        // 4️⃣ Guardamos la solicitud
        return solicitudRepository.save(solicitud);
    }

    // Listar todas las solicitudes
    public List<Solicitud> getAll() {
        return solicitudRepository.findAll();
    }
    // Obtener una solicitud por ID
    public Solicitud getById(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada con ID: " + id));
    }
    // GET /api/solicitudes?estado={estado}&cliente={dni}
    public List<Solicitud> filtrarSolicitudes(String estado, String dniCliente) {
        // Si vienen ambos parámetros
        if (estado != null && dniCliente != null) {
            return solicitudRepository.findByEstadoAndCliente_Dni(EstadoContenedor.valueOf(estado.toUpperCase()), dniCliente);
        }

        // Si viene solo el estado
        if (estado != null) {
            return solicitudRepository.findByEstado(EstadoContenedor.valueOf(estado.toUpperCase()));
        }

        // Si viene solo el cliente
        if (dniCliente != null) {
            return solicitudRepository.findByCliente_Dni(dniCliente);
        }

        // Si no viene ningún filtro, devolver todas
        return solicitudRepository.findAll();
    }
}
