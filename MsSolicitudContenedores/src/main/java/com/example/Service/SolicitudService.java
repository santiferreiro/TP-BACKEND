package com.example.Service;

import com.example.Client.EnvioApiClient;
import com.example.Client.GeoApiClient;
import com.example.Client.LogisticaApiClient;
import com.example.DTO.CamionDTO;
import com.example.DTO.RutaDTO;
import com.example.DTO.TarifaDTO;
import com.example.DTO.TramoDTO;
import com.example.Models.Cliente;
import com.example.Models.Contenedor;
import com.example.Models.Solicitud;
import com.example.Models.EstadoContenedor;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.Repository.SolicitudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ClienteService clienteService;
    private final ContenedorService contenedorService;
    private final LogisticaApiClient logisticaApiClient;
    private final EnvioApiClient envioApiClient;
    private final GeoApiClient geoApiClient;

    public SolicitudService(SolicitudRepository solicitudRepository,
                            ClienteService clienteService,
                            ContenedorService contenedorService,
                            LogisticaApiClient logisticaApiClient,
                            EnvioApiClient envioApiClient,
                            GeoApiClient geoApiClient) {
        this.solicitudRepository = solicitudRepository;
        this.clienteService = clienteService;
        this.contenedorService = contenedorService;
        this.logisticaApiClient = logisticaApiClient;
        this.envioApiClient = envioApiClient;
        this.geoApiClient = geoApiClient;
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
    public Solicitud asignarTarifa(Long idSolicitud, Long idTarifa) {

        // 1. Buscar la solicitud
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        // 2. Asignar la tarifa
        solicitud.setIdTarifa(idTarifa);

        // 3. Guardar y devolver
        return solicitudRepository.save(solicitud);
    }

    public Double estimarCostoTotal(Long idSolicitud) {

        // ================================
        // 1️⃣ Obtener solicitud
        // ================================
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        // ================================
        // 2️⃣ Obtener tarifa asociada
        // ================================
        TarifaDTO tarifa = logisticaApiClient.getTarifaById(solicitud.getIdTarifa());

        // ================================
        // 3️⃣ Obtener ruta completa
        // ================================
        RutaDTO ruta = envioApiClient.getRutaById(solicitud.getIdRuta());
        int cantidadTramos = ruta.getTramos().size();

        // ================================
        // Variables acumuladoras
        // ================================
        double totalKm = 0.0;
        double totalCostoKm = 0.0;
        double totalLitrosConsumidos = 0.0;
        double totalCostoCombustible = 0.0;

        // ================================
        // 4️⃣ Procesar cada tramo
        // ================================
        for (TramoDTO tramo : ruta.getTramos()) {

            // ---- 4.1 Obtener distancia real entre origen/destino ----
            double km = geoApiClient.obtenerDistancia(
                    tramo.getOrigen().getLatitud(),
                    tramo.getOrigen().getLongitud(),
                    tramo.getDestino().getLatitud(),
                    tramo.getDestino().getLongitud()
            );

            // ---- 4.2 Costo por km ----
            double costoKm = km * tarifa.getCostoPorKMBase();

            // ---- 4.3 Obtener datos del camión ----
            CamionDTO camion = logisticaApiClient.getCamionByPatente(tramo.getCamion());

            // ---- 4.4 Calcular combustible real del tramo ----
            double litrosPorKm = camion.getConsumoCombustible() / 100.0;
            double litrosConsumidos = litrosPorKm * km;
            double costoCombustible = litrosConsumidos * tarifa.getCostoLitroCombustible();

            // ---- 4.5 Acumuladores ----
            totalKm += km;
            totalCostoKm += costoKm;
            totalLitrosConsumidos += litrosConsumidos;
            totalCostoCombustible += costoCombustible;
        }

        // ================================
        // 5️⃣ Cargos por gestión
        // ================================
        double cargosGestion = cantidadTramos * tarifa.getCostoPorTramo();

        // ================================
        // 6️⃣ Costo estimado total
        // ================================
        return totalCostoKm + totalCostoCombustible + cargosGestion;
    }

    public List<TramoDTO> verTramos(Long idSolicitud) {

        // 1️⃣ Buscar solicitud
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // 2️⃣ Obtener ruta
        RutaDTO ruta = envioApiClient.getRutaById(solicitud.getIdRuta());

        if (ruta.getTramos() == null || ruta.getTramos().isEmpty()) {
            throw new RuntimeException("La ruta no tiene tramos asociados");
        }

        // 3️⃣ Recorrer tramos y traer los reales uno por uno
        List<TramoDTO> tramosReales = new ArrayList<>();

        for (TramoDTO tramoMin : ruta.getTramos()) {
            tramosReales.add(tramoMin);
        }

        return tramosReales;
    }


}
