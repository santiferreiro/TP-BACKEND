package com.example.DTO;

import lombok.Data;

@Data
public class TarifaDTO {
    private Long idTarifa;
    private String descripcion;
    private Double costoPorKMBase;
    private Double costoLitroCombustible;
    private Double costoEstadia;
    private Double costoPorTramo;  // 🔥 nuevo campo
}