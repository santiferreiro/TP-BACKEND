package com.example.Models;

public enum EstadoContenedor {
    CREADO,          // recién asignado
    ASIGNADO,
    RETIRADO,        // retirado del cliente
    EN_VIAJE,        // en tránsito
    EN_DEPÓSITO,     // esperando nueva ruta o descarga
    ENTREGADO        // ya llegó al destino
}