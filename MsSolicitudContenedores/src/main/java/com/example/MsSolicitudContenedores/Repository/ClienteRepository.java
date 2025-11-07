package com.example.MsSolicitudContenedores.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.MsSolicitudContenedores.Models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

}
