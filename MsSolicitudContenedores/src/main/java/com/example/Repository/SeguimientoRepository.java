package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Models.Seguimiento;

import java.util.List;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
    List<Seguimiento> findByContenedorIdOrderByFechaRegistroAsc(Long contenedorId);
}