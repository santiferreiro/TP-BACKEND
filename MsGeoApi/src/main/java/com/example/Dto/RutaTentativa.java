package com.example.Dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RutaTentativa {

    private double origenLat;
    private double origenLon;

    private double destinoLat;
    private double destinoLon;

    private double distanciaMetros; // OSRM distance
    private double tiempoSegundos;  // OSRM duration
}
