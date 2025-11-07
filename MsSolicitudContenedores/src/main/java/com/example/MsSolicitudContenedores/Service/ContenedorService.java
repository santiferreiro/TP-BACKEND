package com.example.MsSolicitudContenedores.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.MsSolicitudContenedores.Models.Contenedor;
import com.example.MsSolicitudContenedores.Repository.ContenedorRepository;

import java.util.List;

@Service
public class ContenedorService {

    private final ContenedorRepository contenedorRepository;

    public ContenedorService(ContenedorRepository contenedorRepository) {
        this.contenedorRepository = contenedorRepository;
    }
    // Crear un nuevo contenedor
    public Contenedor create(Contenedor contenedor) {
        // Lógica simple: siempre crear un nuevo contenedor
        return contenedorRepository.save(contenedor);
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
