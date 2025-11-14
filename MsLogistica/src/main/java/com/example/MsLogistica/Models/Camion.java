package com.example.MsLogistica.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Camion {

    @Id
    private String patente;

    private Boolean disponible;
    private Double consumoCombustible;
    private Double capacidadVolumen;
    private Double capacidadPeso;

    @ManyToOne
    @JoinColumn(name = "id_transportista")
    private Transportista transportista;
}
