package com.example.ejb;

import java.time.LocalDate;

import com.example.model.Empleado;
import com.example.model.Tarea;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Stateful
public class TareaService {
    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    private Empleado empleado;

    public void setEmpleado(Empleado e) {
        this.empleado = e;
    }

    @Transactional
    public Tarea completarTarea(Long id) {
        Tarea tareaExistente = em.find(Tarea.class, id);
        
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
}