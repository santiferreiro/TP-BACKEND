package com.example.MsSolicitudContenedores.Service;

import com.example.MsSolicitudContenedores.Models.Seguimiento;
import com.example.MsSolicitudContenedores.Repository.SeguimientoRepository;
import jakarta.persistence.EntityNotFoundException;
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
