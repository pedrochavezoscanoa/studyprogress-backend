package com.example.studyprogress.dto;

public class ProgresoCursoResponse {

    private Long cursoId;
    private String nombreCurso;

    private int totalTemas;
    private int temasCompletados;

    private int totalTareas;
    private int tareasCompletadas;

    private double porcentajeProgreso;

    private int evaluacionesCalificadas;
    private Double promedioEvaluaciones;

    public ProgresoCursoResponse() {
    }

    public ProgresoCursoResponse(
            Long cursoId,
            String nombreCurso,
            int totalTemas,
            int temasCompletados,
            int totalTareas,
            int tareasCompletadas,
            double porcentajeProgreso,
            int evaluacionesCalificadas,
            Double promedioEvaluaciones
    ) {
        this.cursoId = cursoId;
        this.nombreCurso = nombreCurso;
        this.totalTemas = totalTemas;
        this.temasCompletados = temasCompletados;
        this.totalTareas = totalTareas;
        this.tareasCompletadas = tareasCompletadas;
        this.porcentajeProgreso = porcentajeProgreso;
        this.evaluacionesCalificadas = evaluacionesCalificadas;
        this.promedioEvaluaciones = promedioEvaluaciones;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public int getTotalTemas() {
        return totalTemas;
    }

    public void setTotalTemas(int totalTemas) {
        this.totalTemas = totalTemas;
    }

    public int getTemasCompletados() {
        return temasCompletados;
    }

    public void setTemasCompletados(int temasCompletados) {
        this.temasCompletados = temasCompletados;
    }

    public int getTotalTareas() {
        return totalTareas;
    }

    public void setTotalTareas(int totalTareas) {
        this.totalTareas = totalTareas;
    }

    public int getTareasCompletadas() {
        return tareasCompletadas;
    }

    public void setTareasCompletadas(int tareasCompletadas) {
        this.tareasCompletadas = tareasCompletadas;
    }

    public double getPorcentajeProgreso() {
        return porcentajeProgreso;
    }

    public void setPorcentajeProgreso(double porcentajeProgreso) {
        this.porcentajeProgreso = porcentajeProgreso;
    }

    public int getEvaluacionesCalificadas() {
        return evaluacionesCalificadas;
    }

    public void setEvaluacionesCalificadas(int evaluacionesCalificadas) {
        this.evaluacionesCalificadas = evaluacionesCalificadas;
    }

    public Double getPromedioEvaluaciones() {
        return promedioEvaluaciones;
    }

    public void setPromedioEvaluaciones(Double promedioEvaluaciones) {
        this.promedioEvaluaciones = promedioEvaluaciones;
    }
}