package com.example.Service;

import com.example.Models.Contenedor;
import com.example.Models.EstadoContenedor;
import com.example.Models.Seguimiento;
import com.example.Repository.ContenedorRepository;
import com.example.Repository.SeguimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Seguimiento crearSeguimiento(Long idContenedor, EstadoContenedor nuevoEstado) {

        Contenedor contenedor = contenedorRepository.findById(idContenedor)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe contenedor con ID: " + idContenedor));

        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setContenedor(contenedor);
        seguimiento.setEstado(nuevoEstado);
        seguimiento.setFechaRegistro(LocalDateTime.now());
        seguimientoRepository.save(seguimiento);

        return seguimiento;
    }

}
