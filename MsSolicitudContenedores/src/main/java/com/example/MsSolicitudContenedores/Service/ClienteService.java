package com.example.MsSolicitudContenedores.Service;

import com.example.MsSolicitudContenedores.Models.Cliente;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.MsSolicitudContenedores.Repository.ClienteRepository;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Acá vas a ir agregando tus métodos más adelante
    public Cliente crear(Cliente cliente) {
        // Buscamos si ya existe un cliente con el mismo DNI
        var clienteExistente = clienteRepository.findById(cliente.getDni());
        if (clienteExistente.isPresent()) {
            // Si ya existe, lo devolvemos
            return clienteExistente.get();
        }
        // Si no existe, lo guardamos y devolvemos el nuevo
        return clienteRepository.save(cliente);
    }

    // GET /api/clientes → obtener todos los clientes
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    // GET /api/clientes/{dni} → obtener un cliente por su DNI
    public Cliente getByDni(String dni) {
        return clienteRepository.findById(dni)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con DNI: " + dni));
    }


}
