package com.example.MsSolicitudContenedores.Repository;

import com.example.MsSolicitudContenedores.Models.EstadoSolicitud;
import com.example.MsSolicitudContenedores.Models.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstado(EstadoSolicitud estado);

    List<Solicitud> findByCliente_Dni(String dni);

    List<Solicitud> findByEstadoAndCliente_Dni(EstadoSolicitud estado, String dni);
}
