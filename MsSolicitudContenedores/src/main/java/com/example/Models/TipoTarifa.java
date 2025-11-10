package com.example.Models;

public enum TipoTarifa {

    // Categorías base (podés adaptarlas a tus reglas de negocio)
    LIVIANA,         // contenedores de bajo peso y volumen
    MEDIA,           // contenedores intermedios
    PESADA,          // contenedores de gran peso o tamaño

    // Otras que podrías necesitar según el TP
    URBANA,          // traslados cortos dentro de una ciudad
    INTERURBANA,     // entre ciudades cercanas
    LARGA_DISTANCIA; // rutas más largas o complejas
}
