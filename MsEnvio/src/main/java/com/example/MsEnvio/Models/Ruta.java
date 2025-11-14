package com.example.MsEnvio.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference(value = "ruta-tramos")
    private List<Tramo> tramos;
}
