package com.example.Service;

import com.example.Models.Cliente;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.Repository.ClienteRepository;

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
    public void delete(String dni) {
        if (!clienteRepository.existsById(dni)) {
            throw new EntityNotFoundException("Cliente no encontrado con DNI: " + dni);
        }
        clienteRepository.deleteById(dni);
    }
    public Cliente update(String dni, Cliente clienteActualizado) {
        Cliente clienteExistente = clienteRepository.findById(dni)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con DNI: " + dni));

        // Actualizá solo los campos que quieras permitir modificar
        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setDni(clienteActualizado.getDni());
        clienteExistente.setDireccion(clienteActualizado.getDireccion());
        clienteExistente.setTelefono(clienteActualizado.getTelefono());

        return clienteRepository.save(clienteExistente);
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
