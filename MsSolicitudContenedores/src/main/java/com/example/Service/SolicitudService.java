package com.example.Service;

import com.example.Models.Cliente;
import com.example.Models.Contenedor;
import com.example.Models.Solicitud;
import com.example.Models.EstadoContenedor;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.Repository.SolicitudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ClienteService clienteService;
    private final ContenedorService contenedorService;

    public SolicitudService(SolicitudRepository solicitudRepository,
                            ClienteService clienteService,
                            ContenedorService contenedorService) {
        this.solicitudRepository = solicitudRepository;
        this.clienteService = clienteService;
        this.contenedorService = contenedorService;
    }


    // Crear una nueva solicitud
    @Transactional
    public Solicitud create(Solicitud solicitud) {

        // 1️⃣ Cliente
        Cliente cliente = clienteService.crear(solicitud.getCliente());
        solicitud.setCliente(cliente);

        // 2️⃣ Contenedor
        Contenedor cont = solicitud.getContenedor();
        if (cont == null || cont.getPeso() == null || cont.getVolumen() == null) {
            throw new IllegalArgumentException("Debe especificarse peso y volumen del contenedor.");
        }

        // 🚀 ASIGNAR CLIENTE AL CONTENEDOR ANTES DE GUARDARLO
        cont.setCliente(cliente);

        // Crear contenedor
        Contenedor contenedor = contenedorService.create(cont);
        solicitud.setContenedor(contenedor);

        // 3️⃣ Validar ubicaciones
        if (solicitud.getOrigen() == null || solicitud.getDestino() == null) {
            throw new IllegalArgumentException("Debe especificarse una ubicación de origen y destino.");
        }

        // 4️⃣ Guardar solicitud
        return solicitudRepository.save(solicitud);
    }


    // Eliminar
    public void delete(Long id) {
        if (!solicitudRepository.existsById(id)) {
            throw new EntityNotFoundException("Solicitud no encontrada con ID: " + id);
        }
        solicitudRepository.deleteById(id);
    }


    // Listar todo
    public List<Solicitud> getAll() {
        return solicitudRepository.findAll();
    }


    // Obtener por ID
    public Solicitud getById(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada con ID: " + id));
    }


    // Filtros GET /api/solicitudes?estado=...&cliente=...
    public List<Solicitud> filtrarSolicitudes(String estado, String dniCliente) {

        // Ambos filtros
        if (estado != null && dniCliente != null) {
            return solicitudRepository.findByContenedor_EstadoAndCliente_Dni(
                    EstadoContenedor.valueOf(estado.toUpperCase()),
                    dniCliente
            );
        }
        // Solo estado
        if (estado != null) {
            return solicitudRepository.findByContenedor_Estado(
                    EstadoContenedor.valueOf(estado.toUpperCase())
            );
        }
        // Solo DNI
        if (dniCliente != null) {
            return solicitudRepository.findByCliente_Dni(dniCliente);
        }

        // Sin filtros
        return solicitudRepository.findAll();
    }


    // Asignar ruta a la solicitud
    public Solicitud asignarRuta(Long idSolicitud, Long idRuta) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() ->
                        new EntityNotFoundException("Solicitud no encontrada con ID: " + idSolicitud));

        solicitud.setIdRuta(idRuta);
        return solicitudRepository.save(solicitud);
    }
}
