package com.example.Service;

import com.example.Models.EstadoContenedor;
import com.example.Models.Seguimiento;
import com.example.Repository.SeguimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.Models.Contenedor;
import com.example.Repository.ContenedorRepository;

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

        // Estado inicial
        contenedor.setEstado(EstadoContenedor.CREADO);

        // Primero guardás el contenedor
        Contenedor nuevoContenedor = contenedorRepository.save(contenedor);

        // Crear seguimiento inicial
        Seguimiento seguimientoInicial = new Seguimiento();
        seguimientoInicial.setEstado(EstadoContenedor.CREADO);
        seguimientoInicial.setFechaRegistro(LocalDateTime.now());
        seguimientoInicial.setContenedor(nuevoContenedor); // 🔥 CLAVE

        // 🔥 Mantener relación bidireccional
        nuevoContenedor.getSeguimientos().add(seguimientoInicial);

        // Guardar seguimiento
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

    public String cambiarEstado(Long idContenedor, EstadoContenedor nuevoEstado) {

        Contenedor contenedor = getById(idContenedor);
        contenedor.setEstado(nuevoEstado);
        contenedorRepository.save(contenedor);

        System.out.println("✔️ Estado del contenedor " + idContenedor + " cambiado a " + nuevoEstado);

        return "Estado del contenedor cambiado a " + nuevoEstado;
    }

}
