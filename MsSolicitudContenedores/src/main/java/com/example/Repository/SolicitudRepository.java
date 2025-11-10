package com.example.Repository;

import com.example.Models.EstadoContenedor;
import com.example.Models.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstado(EstadoContenedor estado);

    List<Solicitud> findByCliente_Dni(String dni);

    List<Solicitud> findByEstadoAndCliente_Dni(EstadoContenedor estado, String dni);
}
