package com.example.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @Column(name = "dni", length = 20)
    private String dni;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "direccion", length = 150)
    private String direccion;

    // ---- Relaciones ----
    @OneToMany(mappedBy = "cliente")
    private List<Solicitud> solicitudes;

    @OneToMany(mappedBy = "cliente")
    private List<Contenedor> contenedores;
}
