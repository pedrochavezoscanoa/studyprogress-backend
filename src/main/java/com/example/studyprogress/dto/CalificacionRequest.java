package com.example.studyprogress.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class CalificacionRequest {

    @NotNull(message = "La calificación es obligatoria")
    @DecimalMin(
            value = "0.0",
            message = "La calificación no puede ser menor que 0"
    )
    @DecimalMax(
            value = "20.0",
            message = "La calificación no puede ser mayor que 20"
    )
    private Double calificacion;

    public CalificacionRequest() {
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
}