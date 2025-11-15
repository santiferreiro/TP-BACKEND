package com.example.MsLogistica.Service;

import com.example.MsLogistica.Models.Tarifa;
import com.example.MsLogistica.Repository.TarifaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    public Tarifa getById(Long idTarifa) {
        return tarifaRepository.findById(idTarifa)
                .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada con ID: " + idTarifa));
    }

    public Tarifa createTarifa(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }


}
