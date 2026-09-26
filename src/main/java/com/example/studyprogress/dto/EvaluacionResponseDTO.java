package com.example.studyprogress.dto;

import java.time.LocalDate;

public class EvaluacionResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private String tipo;
    private Double calificacion;
    private Double ponderacion;
    private Long cursoId;

    public EvaluacionResponseDTO() {
    }

    public EvaluacionResponseDTO(
            Long id,
            String titulo,
            String descripcion,
            LocalDate fecha,
            String tipo,
            Double calificacion,
            Double ponderacion,
            Long cursoId
    ) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.calificacion = calificacion;
        this.ponderacion = ponderacion;
        this.cursoId = cursoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}