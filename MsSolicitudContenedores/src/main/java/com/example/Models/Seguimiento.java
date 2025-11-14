package com.example.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoContenedor estado;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "contenedor_id", nullable = false)
    @JsonIgnore
    private Contenedor contenedor;
}
