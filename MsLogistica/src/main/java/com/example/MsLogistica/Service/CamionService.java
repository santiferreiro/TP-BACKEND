package com.example.MsLogistica.Service;

import com.example.MsLogistica.Client.SolicitudApiClient;
import com.example.MsLogistica.Dto.ContenedorDTO;
import com.example.MsLogistica.Models.Camion;
import com.example.MsLogistica.Repository.CamionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CamionService {

    private final CamionRepository camionRepository;
    private final SolicitudApiClient solicitudApiClient;

    public CamionService(CamionRepository camionRepository, SolicitudApiClient solicitudApiClient) {
        this.camionRepository = camionRepository;
        this.solicitudApiClient = solicitudApiClient;
    }
    public Camion crearCamion(Camion camion) {
        return camionRepository.save(camion);
    }

    public Camion buscarPorPatente(String patente) {
        return camionRepository.findById(patente)
                .orElse(null);
    }
    public String validarDisponibilidad(String patente) {

        Camion camion = buscarPorPatente(patente);

        if (camion.getDisponible() == null || !camion.getDisponible()) {
            throw new IllegalArgumentException(
                    "El camión con patente " + patente + " NO está disponible."
            );
        }

        System.out.println("✔️ El camión " + patente + " está DISPONIBLE.");
        return "El camión está disponible";
    }

    public void validarCapacidad(String patente, Long idContenedor) {
        // 1️⃣ Buscar camión
        Camion camion = buscarPorPatente(patente);
        // 2️⃣ Obtener capacidades del camión
        Double capPeso = camion.getCapacidadPeso();
        Double capVolumen = camion.getCapacidadVolumen();

        // 3️⃣ Consultar contenedor en el otro microservicio
        ContenedorDTO contenedor = solicitudApiClient.obtenerContenedor(idContenedor);

        if (contenedor == null) {
            throw new EntityNotFoundException(
                    "Contenedor no encontrado con ID: " + idContenedor
            );
        }
        Double pesoCont = contenedor.getPeso();
        Double volumenCont = contenedor.getVolumen();

        // 4️⃣ Validar peso
        if (pesoCont > capPeso) {
            throw new IllegalArgumentException(
                    "El contenedor pesa " + pesoCont + " kg y supera la capacidad del camión (" + capPeso + " kg)."
            );
        }

        // 5️⃣ Validar volumen
        if (volumenCont > capVolumen) {
            throw new IllegalArgumentException(
                    "El contenedor tiene " + volumenCont + " m3 y supera la capacidad de volumen del camión (" + capVolumen + " m3)."
            );
        }

        System.out.println("✔️ Validación exitosa: el contenedor es compatible con el camión " + patente);
    }
    public String ocuparCamion(String patente) {
        Camion camion = buscarPorPatente(patente);
        // Cambiar disponibilidad
        camion.setDisponible(false);
        // Guardar cambios
        camionRepository.save(camion);
        System.out.println("✔️ Camión " + patente + " ahora está OCUPADO.");

        return "Camión " + patente + " marcado como OCUPADO";
    }


}
