package com.example.studyprogress.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CursoRequest {

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Size(
            min = 2,
            max = 100,
            message = "El nombre del curso debe tener entre 2 y 100 caracteres"
    )
    private String nombre;

    @Size(
            max = 500,
            message = "La descripción no puede superar los 500 caracteres"
    )
    private String descripcion;

    public CursoRequest() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}