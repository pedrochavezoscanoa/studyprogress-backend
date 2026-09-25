package com.example.studyprogress.dto;

public class GamificacionResponse {

    private int puntos;
    private String nivel;

    public GamificacionResponse() {
    }

    public GamificacionResponse(int puntos, String nivel) {
        this.puntos = puntos;
        this.nivel = nivel;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}