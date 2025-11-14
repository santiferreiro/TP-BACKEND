package com.example.MsEnvio.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🌍 Coordenadas geográficas (definidas por el cliente)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitud", column = @Column(name = "origen_latitud", nullable = false)),
            @AttributeOverride(name = "longitud", column = @Column(name = "origen_longitud", nullable = false))
    })
    private Ubicacion origen;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitud", column = @Column(name = "destino_latitud", nullable = false)),
            @AttributeOverride(name = "longitud", column = @Column(name = "destino_longitud", nullable = false))
    })
    private Ubicacion destino;

    @Enumerated(EnumType.STRING)
    private TipoTramo tipo;

    @Enumerated(EnumType.STRING)
    private EstadoTramo estado;

    private Double costoAproximado;
    private Double costoReal;

    private Double tiempoEstimado;
    private Double tiempoRealSegundos;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    private String camion;  // dominio del camión

    // Relación con Ruta (1 Ruta -> N Tramos)
    @ManyToOne
    @JoinColumn(name = "ruta_id")
    @JsonBackReference(value = "ruta-tramos")
    private Ruta ruta;
}
