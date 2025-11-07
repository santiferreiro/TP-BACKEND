package com.example.MsSolicitudContenedores.Models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una ubicación geográfica mediante coordenadas.
 * Se usa como campo embebido (por ejemplo: origen, destino, depósitos, etc.)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Ubicacion {

    private Double latitud;
    private Double longitud;
}
