package com.example.studyprogress.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TareaRequest {

    @NotBlank(message = "El título de la tarea es obligatorio")
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

    @NotBlank(message = "La prioridad es obligatoria")
    @Pattern(
            regexp = "ALTA|MEDIA|BAJA",
            message = "La prioridad debe ser ALTA, MEDIA o BAJA"
    )
    private String prioridad;

    public TareaRequest() {
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

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}