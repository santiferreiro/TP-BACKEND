package com.example.Dto;

public class DistanciaResponse {
    private double distanciaKm;

    public DistanciaResponse(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }
}
