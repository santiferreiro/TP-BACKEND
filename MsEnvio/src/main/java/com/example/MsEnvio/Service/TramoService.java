package com.example.MsEnvio.Service;

import com.example.MsEnvio.Models.Tramo;
import com.example.MsEnvio.Repository.TramoRepository;
import com.example.MsEnvio.Repository.RutaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TramoService {

    private final TramoRepository tramoRepository;
    private final RutaRepository rutaRepository;

    public TramoService(TramoRepository tramoRepository, RutaRepository rutaRepository) {
        this.tramoRepository = tramoRepository;
        this.rutaRepository = rutaRepository;
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
}
