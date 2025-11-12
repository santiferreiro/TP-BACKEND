package com.example.MsEnvio.Service;

import com.example.MsEnvio.Models.Tramo;
import com.example.MsEnvio.Repository.TramoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TramoService {

    private final TramoRepository tramoRepository;

    public TramoService(TramoRepository tramoRepository) {
        this.tramoRepository = tramoRepository;
    }

    // Crear un nuevo tramo
    public Tramo create(Tramo tramo) {
        return tramoRepository.save(tramo);
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

    // Actualizar un tramo existente
    public Tramo update(Long id, Tramo tramoActualizado) {
        Tramo existente = tramoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tramo no encontrado con ID: " + id));

        existente.setOrigen(tramoActualizado.getOrigen());
        existente.setDestino(tramoActualizado.getDestino());
        existente.setTipo(tramoActualizado.getTipo());
        existente.setEstado(tramoActualizado.getEstado());
        existente.setCostoAproximado(tramoActualizado.getCostoAproximado());
        existente.setCostoReal(tramoActualizado.getCostoReal());
        existente.setFechaHoraInicio(tramoActualizado.getFechaHoraInicio());
        existente.setFechaHoraFin(tramoActualizado.getFechaHoraFin());
        existente.setCamion(tramoActualizado.getCamion());
        existente.setRuta(tramoActualizado.getRuta());

        return tramoRepository.save(existente);
    }

    // Eliminar un tramo por ID
    public void delete(Long id) {
        if (!tramoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tramo no encontrado con ID: " + id);
        }
        tramoRepository.deleteById(id);
    }
}
