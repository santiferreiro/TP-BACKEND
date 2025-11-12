package com.example.MsEnvio.Service;

import com.example.MsEnvio.Client.SolicitudApiClient;
import com.example.MsEnvio.Models.Ruta;
import com.example.MsEnvio.Repository.RutaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;
    private final SolicitudApiClient solicitudApiClient; // 👈 cliente para comunicar con el otro microservicio

    public RutaService(RutaRepository rutaRepository, SolicitudApiClient solicitudApiClient) {
        this.rutaRepository = rutaRepository;
        this.solicitudApiClient = solicitudApiClient;
    }

    // 🔹 Crear una nueva ruta
    public Ruta create(Ruta ruta) {
        return rutaRepository.save(ruta);
    }

    // 🔹 Obtener todas las rutas
    public List<Ruta> getAll() {
        return rutaRepository.findAll();
    }

    // 🔹 Obtener una ruta por ID
    public Ruta getById(Long id) {
        return rutaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ruta no encontrada con ID: " + id));
    }

    // 🔹 Actualizar una ruta existente
    public Ruta update(Long id, Ruta rutaActualizada) {
        Ruta existente = rutaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ruta no encontrada con ID: " + id));

        existente.setCantidadTramos(rutaActualizada.getCantidadTramos());
        existente.setCantidadDepositos(rutaActualizada.getCantidadDepositos());
        existente.setTramos(rutaActualizada.getTramos());

        return rutaRepository.save(existente);
    }

    // 🔹 Eliminar una ruta por ID
    public void delete(Long id) {
        if (!rutaRepository.existsById(id)) {
            throw new EntityNotFoundException("Ruta no encontrada con ID: " + id);
        }
        rutaRepository.deleteById(id);
    }

    // 🔹 Asignar una ruta a una solicitud (llama al otro microservicio)
    public void asignarRuta(Long idSolicitud, Long idRuta) {
        // Verificar que la ruta exista en MsEnvio antes de asignarla
        if (!rutaRepository.existsById(idRuta)) {
            throw new EntityNotFoundException("Ruta no encontrada con ID: " + idRuta);
        }

        // Llamar al MsSolicitudContenedores para actualizar la solicitud
        solicitudApiClient.asignarRuta(idSolicitud, idRuta);
    }

    
}
