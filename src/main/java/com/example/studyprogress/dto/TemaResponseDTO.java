package com.example.studyprogress.dto;

public class TemaResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private boolean completado;
    private Long cursoId;

    public TemaResponseDTO() {
    }

    public TemaResponseDTO(
            Long id,
            String nombre,
            String descripcion,
            boolean completado,
            Long cursoId
    ) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.completado = completado;
        this.cursoId = cursoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}