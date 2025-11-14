package com.example.MsLogistica.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deposito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deposito")
    private Long idDeposito;

    private String nombre;
    private String direccion;
    private Double latitud;
    private Double longitud;

    // FK hacia otro microservicio → NO usar relación JPA
    private Long id_contenedor;
}