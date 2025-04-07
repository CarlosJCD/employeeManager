package com.example.ejb;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.example.model.Empleado;
import com.example.model.Tarea;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateful
public class TareaService {
    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    private Empleado empleado;

    public void setEmpleado(Empleado e) {
        this.empleado = e;
    }

    public void agregarTarea(String descripcion) {
        // Tarea t = new Tarea();
        // t.setDescripcion(descripcion);
        // t.setCompletada(false);
        // t.setEmpleado(empleado);
        // em.persist(t);
    }

    public Tarea completarTarea(Tarea tarea) {
        Tarea tareaExistente = em.find(Tarea.class, tarea.getId());
        
        if (tareaExistente != null) {
            if(tareaExistente.isCompletada()) {
                throw new IllegalArgumentException("La tarea ya está completada");
            }

            tareaExistente.setCompletada(true);
            tareaExistente.setFechaTermino(LocalDate.now());

            em.merge(tareaExistente);
            return tareaExistente;
        } else {
            throw new IllegalArgumentException("La tarea no existe");
        }
    }

    // public List<Tarea> obtenerTareas() {

    // }
}