package com.example.MsEnvio.Service;

import com.example.MsEnvio.Client.LogisticaApiClient;
import com.example.MsEnvio.Client.SolicitudApiClient;
import com.example.MsEnvio.DTO.CamionDTO;
import com.example.MsEnvio.Models.EstadoTramo;
import com.example.MsEnvio.Models.Tramo;
import com.example.MsEnvio.Repository.TramoRepository;
import com.example.MsEnvio.Repository.RutaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TramoService {

    private final TramoRepository tramoRepository;
    private final RutaRepository rutaRepository;
    private final LogisticaApiClient logisticaApiClient;
    private final SolicitudApiClient solicitudApiClient;


    public TramoService(TramoRepository tramoRepository, RutaRepository rutaRepository, LogisticaApiClient logisticaApiClient, SolicitudApiClient solicitudApiClient) {
        this.tramoRepository = tramoRepository;
        this.rutaRepository = rutaRepository;
        this.logisticaApiClient = logisticaApiClient;
        this.solicitudApiClient = solicitudApiClient;
    }
    // Listar todos los tramos
    public List<Tramo> getAll() {
        return tramoRepository.findAll();
    }

    // Buscar un tramo por ID
    public Tramo getById(Long id) {
        return tramoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tramo no encontrado con ID: " + id));
    }

    public Tramo createForRuta(Long idRuta, Tramo tramo) {
        var ruta = rutaRepository.findById(idRuta)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        tramo.setRuta(ruta);
        return tramoRepository.save(tramo);
    }
    public String asignarCamionATramo(String patente, Long idContenedor, Long idTramo) {

        // Validar disponibilidad del camión
        String validacionDisponibilidad = logisticaApiClient.validarDisponibilidad(patente);
        System.out.println("✔️ Validación de disponibilidad OK: " + validacionDisponibilidad);
        // Validar capacidad del camión: peso y volumen
        String resultado = logisticaApiClient.validarCapacidad(patente, idContenedor);
        System.out.println("Resultado de validación desde Logística: " + resultado);

        // 3️⃣ Obtener el tramo donde quiero asignar el camión
        Tramo tramo = getById(idTramo);
        System.out.println("✔️ Tramo obtenido correctamente. ID: " + tramo.getId());

        // Paso 4: setear el camión al tramo (después)
        tramo.setCamion(patente);
        // Paso 5: cambiar estado del tramo -> ASIGNADO (después)
        tramo.setEstado(EstadoTramo.ASIGNADO);
        // Paso 6: cambiar estado del camión -> OCUPADO (después)
        logisticaApiClient.ocuparCamion(patente);
        // Paso 7: cambiar estado del contenedor -> ASIGNADO (después)
        solicitudApiClient.cambiarEstadoContenedor(idContenedor, "ASIGNADO");
        // 8️⃣ Guardar los cambios del tramo (ESTO ES LO QUE FALTABA)
        tramoRepository.save(tramo);
        return "Validación completada (primer paso OK)";
    }

    public boolean validarTransportista(Long idTramo, Long idTransportista) {

        // 1️⃣ Obtener el tramo
        Tramo tramo = getById(idTramo);

        // 2️⃣ Validar que tenga camión asignado
        if (tramo.getCamion() == null) {
            throw new IllegalStateException("El tramo no tiene un camión asignado.");
        }

        // 3️⃣ Obtener el camión desde MS Logistica
        CamionDTO camion = logisticaApiClient.obtenerCamionPorPatente(tramo.getCamion());

        if (camion == null) {
            throw new EntityNotFoundException("No se encontró el camión asignado al tramo.");
        }

        // 4️⃣ Obtener transportista del camión
        Long idTranspDelCamion = camion.getTransportista().getIdTransportista();

        // 5️⃣ Comparar transporte
        if (!idTranspDelCamion.equals(idTransportista)) {
            throw new SecurityException("El transportista NO está asignado a este tramo.");
        }

        return true; // Validación correcta
    }

    public String cambiarEstado(Long idTramo, EstadoTramo nuevoEstado) {

        // 1️⃣ obtener el tramo
        Tramo tramo = getById(idTramo);

        // 2️⃣ cambiar el estado
        tramo.setEstado(nuevoEstado);

        // 3️⃣ guardar en BD
        tramoRepository.save(tramo);

        return "✔️ Estado del tramo actualizado a " + nuevoEstado;
    }
    public void fechaInicio(Long idTramo) {
        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> new EntityNotFoundException("Tramo no encontrado"));

        tramo.setFechaHoraInicio(LocalDateTime.now());
        tramoRepository.save(tramo);
    }

    public void fechaFin(Long idTramo) {
        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> new EntityNotFoundException("Tramo no encontrado"));

        tramo.setFechaHoraFin(LocalDateTime.now());
        tramoRepository.save(tramo);
    }



}
