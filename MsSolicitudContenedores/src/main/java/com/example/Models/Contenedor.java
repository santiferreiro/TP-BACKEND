package com.example.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private EstadoContenedor estado;

    // ---- Relaciones ----
    @ManyToOne
    @JoinColumn(name = "cliente", nullable = false)
    @JsonIgnore
    private Cliente cliente;

    @OneToMany(mappedBy = "contenedor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Seguimiento> seguimientos = new ArrayList<>();
}

