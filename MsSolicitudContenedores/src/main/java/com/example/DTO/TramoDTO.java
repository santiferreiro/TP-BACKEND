package com.example.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TramoDTO {
    private Long id;

    private UbicacionDTO origen;
    private UbicacionDTO destino;

    private String camion;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
}