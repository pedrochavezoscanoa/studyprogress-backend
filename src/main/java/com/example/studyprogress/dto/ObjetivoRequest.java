package com.example.studyprogress.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ObjetivoRequest {

    @NotBlank(message = "El título del objetivo es obligatorio")
    @Size(
            min = 2,
            max = 150,
            message = "El título debe tener entre 2 y 150 caracteres"
    )
    private String titulo;

    @Size(
            max = 500,
            message = "La descripción no puede superar los 500 caracteres"
    )
    private String descripcion;

    @NotNull(message = "La fecha límite es obligatoria")
    @FutureOrPresent(
            message = "La fecha límite no puede estar en el pasado"
    )
    private LocalDate fechaLimite;

    public ObjetivoRequest() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}