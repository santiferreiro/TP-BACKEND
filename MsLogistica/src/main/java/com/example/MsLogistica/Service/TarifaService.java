package com.example.MsLogistica.Service;

import com.example.MsLogistica.Repository.TarifaRepository;
import org.springframework.stereotype.Service;

@Service
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }
}
