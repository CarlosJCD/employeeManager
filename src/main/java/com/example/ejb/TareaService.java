package com.example.ejb;

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

    // public List<Tarea> obtenerTareas() {

    // }
}