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
    public Contenedor update(Long id, Contenedor contenedorActualizado) {
        Contenedor existente = contenedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contenedor no encontrado con ID: " + id));

        // Actualizá los campos que correspondan
        existente.setPeso(contenedorActualizado.getPeso());
        existente.setVolumen(contenedorActualizado.getVolumen());
        existente.setEstado(contenedorActualizado.getEstado());

        Contenedor contenedorModificado = contenedorRepository.save(existente);

        // Registrar el cambio de estado en Seguimiento (opcional, si cambia estado)
        Seguimiento nuevoSeguimiento = new Seguimiento();
        nuevoSeguimiento.setEstado(contenedorModificado.getEstado());
        nuevoSeguimiento.setFechaRegistro(LocalDateTime.now());
        nuevoSeguimiento.setContenedor(contenedorModificado);
        seguimientoRepository.save(nuevoSeguimiento);

        return contenedorModificado;
    }

    // Eliminar un contenedor por su ID
    public void delete(Long id) {
        if (!contenedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Contenedor no encontrado con ID: " + id);
        }
        contenedorRepository.deleteById(id);
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
