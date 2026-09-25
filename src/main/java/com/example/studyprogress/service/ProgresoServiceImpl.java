package com.example.studyprogress.service;

import com.example.studyprogress.dto.ProgresoCursoResponse;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Evaluacion;
import com.example.studyprogress.model.Tarea;
import com.example.studyprogress.model.Tema;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgresoServiceImpl implements ProgresoService {

    private final TemaService temaService;
    private final TareaService tareaService;
    private final EvaluacionService evaluacionService;

    public ProgresoServiceImpl(
            TemaService temaService,
            TareaService tareaService,
            EvaluacionService evaluacionService
    ) {
        this.temaService = temaService;
        this.tareaService = tareaService;
        this.evaluacionService = evaluacionService;
    }

    @Override
    public ProgresoCursoResponse calcularProgresoCurso(Curso curso) {

        List<Tema> temas =
                temaService.listarTemasPorCurso(curso);

        List<Tarea> tareas =
                tareaService.listarTareasPorCurso(curso);

        List<Evaluacion> evaluaciones =
                evaluacionService.listarEvaluacionesPorCurso(curso);

        int totalTemas = temas.size();
        int temasCompletados = 0;

        for (Tema tema : temas) {
            if (tema.isCompletado()) {
                temasCompletados++;
            }
        }

        int totalTareas = tareas.size();
        int tareasCompletadas = 0;

        for (Tarea tarea : tareas) {
            if (tarea.isCompletada()) {
                tareasCompletadas++;
            }
        }

        int totalActividades =
                totalTemas + totalTareas;

        int actividadesCompletadas =
                temasCompletados + tareasCompletadas;

        double porcentajeProgreso = 0.0;

        if (totalActividades > 0) {
            porcentajeProgreso =
                    ((double) actividadesCompletadas
                            / totalActividades) * 100;
        }

        porcentajeProgreso =
                Math.round(porcentajeProgreso * 100.0)
                        / 100.0;

        int evaluacionesCalificadas = 0;
        double sumaCalificaciones = 0.0;

        for (Evaluacion evaluacion : evaluaciones) {

            if (evaluacion.getCalificacion() != null) {

                sumaCalificaciones +=
                        evaluacion.getCalificacion();

                evaluacionesCalificadas++;
            }
        }

        Double promedioEvaluaciones = null;

        if (evaluacionesCalificadas > 0) {

            promedioEvaluaciones =
                    sumaCalificaciones
                            / evaluacionesCalificadas;

            promedioEvaluaciones =
                    Math.round(
                            promedioEvaluaciones * 100.0
                    ) / 100.0;
        }

        return new ProgresoCursoResponse(
                curso.getId(),
                curso.getNombre(),
                totalTemas,
                temasCompletados,
                totalTareas,
                tareasCompletadas,
                porcentajeProgreso,
                evaluacionesCalificadas,
                promedioEvaluaciones
        );
    }
}