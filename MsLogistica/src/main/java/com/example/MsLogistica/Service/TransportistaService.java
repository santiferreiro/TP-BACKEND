package com.example.MsLogistica.Service;

import com.example.MsLogistica.Client.EnvioApiClient;
import com.example.MsLogistica.Client.SolicitudApiClient;
import com.example.MsLogistica.Models.Transportista;
import com.example.MsLogistica.Repository.TransportistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportistaService {

    private final TransportistaRepository transportistaRepository;
    private final EnvioApiClient envioApiClient;
    private final SolicitudApiClient solicitudApiClient;


    public TransportistaService(TransportistaRepository transportistaRepository,
                                EnvioApiClient envioApiClient,
                                SolicitudApiClient solicitudApiClient) {
        this.transportistaRepository = transportistaRepository;
        this.envioApiClient = envioApiClient;
        this.solicitudApiClient = solicitudApiClient;
    }

    // Buscar todos los transportistas
    public List<Transportista> buscarTodos() {
        return transportistaRepository.findAll();
    }

    // Crear un nuevo transportista
    public Transportista crearTransportista(Transportista transportista) {
        return transportistaRepository.save(transportista);
    }
    // MÉTODO PRINCIPAL
    public String iniciarTramo(Long idTramo, Long idTransportista, Long idContenedor) {

        // 1️⃣ Validar que el tramo exista
        envioApiClient.obtenerTramoPorId(idTramo);
        System.out.println("✔️ Tramo verificado correctamente.");

        // 2️⃣ Validar que el transportista asignado coincida
        boolean valido = envioApiClient.validarTransportistaEnTramo(idTramo, idTransportista);

        if (!valido) {
            throw new SecurityException("❌ El transportista NO está asignado a este tramo.");
        }
        System.out.println("✔️ Transportista validado correctamente.");

        // 3️⃣ Cambiar estado del tramo a INICIADO
        envioApiClient.iniciarTramo(idTramo);
        solicitudApiClient.cambiarEstadoContenedor(idContenedor, "EN_VIAJE");

        return "✔️ Tramo " + idTramo + " iniciado correctamente por el transportista " + idTransportista;

    }

    public String finalizarTramo(Long idTramo, Long idTransportista, Long idContenedor) {

        // 1️⃣ Validar que el tramo exista
        envioApiClient.obtenerTramoPorId(idTramo);
        System.out.println("✔️ Tramo verificado correctamente.");

        // 2️⃣ Validar transportista asignado
        boolean valido = envioApiClient.validarTransportistaEnTramo(idTramo, idTransportista);

        if (!valido) {
            throw new SecurityException("❌ El transportista NO está asignado a este tramo.");
        }
        System.out.println("✔️ Transportista validado correctamente.");

        // 3️⃣ Cambiar estado a FINALIZADO
        envioApiClient.finalizarTramo(idTramo);
        solicitudApiClient.cambiarEstadoContenedor(idContenedor, "EN_DESTINO");
        return "✔️ Tramo " + idTramo + " finalizado correctamente por el transportista " + idTransportista;
    }

}
