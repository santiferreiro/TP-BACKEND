package com.example.MsEnvio.Service;

import com.example.MsEnvio.Client.GeoApiClient;
import com.example.MsEnvio.Client.SolicitudApiClient;
import com.example.MsEnvio.DTO.RutaTentativaDTO;
import com.example.MsEnvio.Models.*;
import com.example.MsEnvio.Repository.RutaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;
    private final SolicitudApiClient solicitudApiClient;
    private final GeoApiClient geoApiClient;// 👈 cliente para comunicar con el otro microservicio

    public RutaService(RutaRepository rutaRepository, SolicitudApiClient solicitudApiClient, GeoApiClient geoApiClient) {
        this.rutaRepository = rutaRepository;
        this.solicitudApiClient = solicitudApiClient;
        this.geoApiClient = geoApiClient;
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

    public RutaTentativaDTO obtenerRutaTentativa(
            double latOrigen, double lonOrigen,
            double latDestino, double lonDestino) {

        return geoApiClient.obtenerRutaTentativa(
                latOrigen, lonOrigen,
                latDestino, lonDestino
        );
    }
    public Ruta confirmarRuta(RutaTentativaDTO dto) {

        // 1) Crear Ruta vacía
        Ruta ruta = Ruta.builder()
                .cantidadTramos(1)
                .cantidadDepositos(0)
                .build();

        // 2) Crear tramo ORIGEN–DESTINO
        Tramo tramo = new Tramo();
        tramo.setTipo(TipoTramo.ORIGEN_DESTINO);
        tramo.setEstado(EstadoTramo.ESTIMADO);

        // Ubicación origen
        Ubicacion origen = new Ubicacion();
        origen.setLatitud(dto.getOrigenLat());
        origen.setLongitud(dto.getOrigenLon());
        tramo.setOrigen(origen);

        // Ubicación destino
        Ubicacion destino = new Ubicacion();
        destino.setLatitud(dto.getDestinoLat());
        destino.setLongitud(dto.getDestinoLon());
        tramo.setDestino(destino);

        // ===============================
        // ⏱️ Agregamos duración estimada
        // ===============================
        tramo.setTiempoEstimado(dto.getDuracionSegundos());  // en segundos

        // ===============================
        // 📏 Opcional: distancia estimada
        // ===============================
        tramo.setCostoAproximado(null); // lo calculás después si querés

        // Asociar tramo a ruta (lado dueño: tramo)
        tramo.setRuta(ruta);

        // Agregar tramo a la lista de la ruta
        ruta.setTramos(List.of(tramo));

        // 3) Guardar ruta (por cascade se guarda el tramo)
        return rutaRepository.save(ruta);
    }


}
