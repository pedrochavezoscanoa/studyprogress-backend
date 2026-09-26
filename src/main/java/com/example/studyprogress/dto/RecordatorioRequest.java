package com.example.studyprogress.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class RecordatorioRequest {

    @NotBlank(message = "El título del recordatorio es obligatorio")
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

    @NotNull(message = "La fecha y hora son obligatorias")
    @Future(
            message = "La fecha y hora del recordatorio deben estar en el futuro"
    )
    private LocalDateTime fechaHora;

    public RecordatorioRequest() {
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}