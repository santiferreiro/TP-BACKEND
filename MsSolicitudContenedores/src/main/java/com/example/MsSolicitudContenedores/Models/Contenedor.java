package com.example.MsSolicitudContenedores.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contenedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contenedor")
    private Long idContenedor;

    @Column(name = "peso", nullable = false)
    private Double peso;

    @Column(name = "volumen", nullable = false)
    private Double volumen;

    @Column(name = "estado", length = 30)
    private String estado;

    // ---- Relaciones ----
    @ManyToOne
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente;
}

