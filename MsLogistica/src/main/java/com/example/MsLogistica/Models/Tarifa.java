package com.example.MsLogistica.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private Long idTarifa;

    private String descripcion;

    private Double pesoMin;
    private Double pesoMax;

    private Double volumenMin;
    private Double volumenMax;

    private Double costoPorKMBase;
    private Double costoLitroCombustible;
    private Double costoEstadia;
}
