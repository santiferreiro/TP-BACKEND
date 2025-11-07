package com.example.MsSolicitudContenedores.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "solicitud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_solicitud")
    private Long nroSolicitud;

    @Column(name = "costo_estimado")
    private Double costoEstimado;

    @Column(name = "tiempo_estimado")
    private Double tiempoEstimado;

    @Column(name = "costo_final")
    private Double costoFinal;

    @Column(name = "tiempo_real")
    private Double tiempoReal;

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
    @Column(name = "estado", nullable = false)
    private EstadoSolicitud estado;

    // ---- Relaciones ----
    @ManyToOne
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "id_contenedor", nullable = false)
    private Contenedor contenedor;

    @ManyToOne
    @JoinColumn(name = "id_tarifa")
    private Tarifa tarifa;

    @Column(name = "id_ruta")
    private Long idRuta;
}
