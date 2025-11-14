package com.example.MsLogistica.Service;

import com.example.MsLogistica.Models.Transportista;
import com.example.MsLogistica.Repository.TransportistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportistaService {

    private final TransportistaRepository transportistaRepository;

    public TransportistaService(TransportistaRepository transportistaRepository) {
        this.transportistaRepository = transportistaRepository;
    }

    // Buscar todos los transportistas
    public List<Transportista> buscarTodos() {
        return transportistaRepository.findAll();
    }

    // Crear un nuevo transportista
    public Transportista crearTransportista(Transportista transportista) {
        return transportistaRepository.save(transportista);
    }
}
