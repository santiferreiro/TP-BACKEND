package com.example.MsSolicitudContenedores.Models;

public enum EstadoContenedor {
    CREADO,          // recién asignado
    RETIRADO,        // retirado del cliente
    EN_VIAJE,        // en tránsito
    EN_DEPÓSITO,     // esperando nueva ruta o descarga
    ENTREGADO        // ya llegó al destino
}