package com.example.Service;

import com.example.Models.Seguimiento;
import com.example.Repository.SeguimientoRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;

    public SeguimientoService(SeguimientoRepository seguimientoRepository) {
        this.seguimientoRepository = seguimientoRepository;
    }

    public List<Seguimiento> listarPorContenedor(Long contenedorId) {
        return seguimientoRepository.findByContenedorIdOrderByFechaRegistroAsc(contenedorId);
    }
}
