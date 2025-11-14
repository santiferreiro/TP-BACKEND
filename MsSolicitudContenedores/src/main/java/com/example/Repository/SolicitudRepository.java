package com.example.Repository;

import com.example.Models.Solicitud;
import com.example.Models.EstadoContenedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    // Filtrar SOLO por estado del contenedor
    List<Solicitud> findByContenedor_Estado(EstadoContenedor estado);

    // Filtrar SOLO por DNI del cliente
    List<Solicitud> findByCliente_Dni(String dni);

    // Filtrar por estado del contenedor + DNI del cliente
    List<Solicitud> findByContenedor_EstadoAndCliente_Dni(EstadoContenedor estado, String dni);
}
