package com.example.studyprogress.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class EvaluacionRequest {

    @NotBlank(message = "El título de la evaluación es obligatorio")
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

    @NotNull(message = "La fecha de la evaluación es obligatoria")
    @FutureOrPresent(
            message = "La fecha de la evaluación no puede estar en el pasado"
    )
    private LocalDate fecha;

    @NotBlank(message = "El tipo de evaluación es obligatorio")
    @Size(
            max = 50,
            message = "El tipo no puede superar los 50 caracteres"
    )
    private String tipo;

    @NotNull(message = "La ponderación es obligatoria")
    @Min(
            value = 0,
            message = "La ponderación no puede ser menor que 0"
    )
    @Max(
            value = 100,
            message = "La ponderación no puede ser mayor que 100"
    )
    private Double ponderacion;

    public EvaluacionRequest() {
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }
}