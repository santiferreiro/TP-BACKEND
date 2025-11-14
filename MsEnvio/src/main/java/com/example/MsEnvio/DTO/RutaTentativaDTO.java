package com.example.MsEnvio.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RutaTentativaDTO {

    private double distanciaMetros;
    private double duracionSegundos;

    private double origenLat;
    private double origenLon;

    private double destinoLat;
    private double destinoLon;
}
