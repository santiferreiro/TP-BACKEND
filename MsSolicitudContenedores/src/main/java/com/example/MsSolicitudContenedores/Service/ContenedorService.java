package com.example.MsSolicitudContenedores.Service;

import com.example.MsSolicitudContenedores.Models.EstadoContenedor;
import com.example.MsSolicitudContenedores.Models.Seguimiento;
import com.example.MsSolicitudContenedores.Repository.SeguimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.MsSolicitudContenedores.Models.Contenedor;
import com.example.MsSolicitudContenedores.Repository.ContenedorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContenedorService {

    private final ContenedorRepository contenedorRepository;
    private final SeguimientoRepository seguimientoRepository;

    public ContenedorService(ContenedorRepository contenedorRepository,
                             SeguimientoRepository seguimientoRepository) {
        this.contenedorRepository = contenedorRepository;
        this.seguimientoRepository = seguimientoRepository;
    }
    // Crear un nuevo contenedor
    public Contenedor create(Contenedor contenedor) {
        // Lógica simple: siempre crear un nuevo contenedor
        contenedor.setEstado(EstadoContenedor.CREADO);
        Contenedor nuevoContenedor = contenedorRepository.save(contenedor);

        // Creamos el registro de seguimiento inicial
        Seguimiento seguimientoInicial = new Seguimiento();
        seguimientoInicial.setEstado(EstadoContenedor.CREADO);
        seguimientoInicial.setFechaRegistro(LocalDateTime.now());
        seguimientoInicial.setContenedor(nuevoContenedor);

        // Persistimos el seguimiento
        seguimientoRepository.save(seguimientoInicial);

        return nuevoContenedor;
    }

    // Listar todos los contenedores
    public List<Contenedor> getAll() {
        return contenedorRepository.findAll();
    }
    // Buscar un contenedor por su ID
    public Contenedor getById(Long id) {
        return contenedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contenedor no encontrado con ID: " + id));
    }
}
