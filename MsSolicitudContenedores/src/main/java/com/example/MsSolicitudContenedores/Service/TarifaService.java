package com.example.MsSolicitudContenedores.Service;

import org.springframework.stereotype.Service;
import com.example.MsSolicitudContenedores.Repository.TarifaRepository;

@Service
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    // Acá vas a ir agregando tus métodos más adelante
}
