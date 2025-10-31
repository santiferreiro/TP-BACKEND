package com.example.MsSolicitudContenedores.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarifa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private Long idTarifa;

    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;

    @Column(name = "peso_min")
    private Double pesoMin;

    @Column(name = "peso_max")
    private Double pesoMax;

    @Column(name = "volumen_min")
    private Double volumenMin;

    @Column(name = "volumen_max")
    private Double volumenMax;

    @Column(name = "costo_por_km_base")
    private Double costoPorKmBase;

    @Column(name = "costo_litro_combustible")
    private Double costoLitroCombustible;

    @Column(name = "costo_estadia")
    private Double costoEstadia;
}

