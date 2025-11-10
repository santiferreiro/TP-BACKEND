package com.example.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

}
