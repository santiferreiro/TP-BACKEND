package com.example.MsEnvio.Models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos definidos en el modelo mínimo del TP
    private Integer cantidadTramos;
    private Integer cantidadDepositos;

    // Relación 1:N con Tramo
    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tramo> tramos;
}
