package com.example.Service;

import com.example.Models.Contenedor;
import com.example.Models.Seguimiento;
import com.example.Repository.ContenedorRepository;
import com.example.Repository.SeguimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;
    private final ContenedorRepository contenedorRepository;

    public SeguimientoService(SeguimientoRepository seguimientoRepository,
                              ContenedorRepository contenedorRepository) {
        this.seguimientoRepository = seguimientoRepository;
        this.contenedorRepository = contenedorRepository;
    }

    public List<Seguimiento> listarPorContenedor(Long contenedorId) {

        Contenedor c = contenedorRepository.findById(contenedorId)
                .orElseThrow(() -> new RuntimeException("No existe contenedor con ID: " + contenedorId));

        return seguimientoRepository.findByContenedorOrderByFechaRegistroAsc(c);
    }
}
