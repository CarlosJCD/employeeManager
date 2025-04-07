package com.example.rest;

import com.example.model.Empleado;
import com.example.model.Tarea;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("tareas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TareaRESTService {

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    // Asignar tarea 
    @POST
    @Path("asignar")
    @Consumes(MediaType.APPLICATION_JSON)
    public void asignar(Tarea tarea) {
        Empleado empleado = em.find(Empleado.class, tarea.getEmpleado().getId());
        if (empleado != null) {
            tarea.setEmpleado(empleado);
            em.persist(tarea);  
        } else {
            throw new NotFoundException("Empleado no encontrado");
        }
    }

    // Consultar tareas pendientes de x empleado
    @GET
    @Path("pendientes/{id}")
    public List<Tarea> obtenerTareasPendientes(@PathParam("id") Long empleadoId) {
        return em.createQuery("SELECT t FROM Tarea t WHERE t.empleado.id = :empleadoId AND t.completada = false", Tarea.class)
                 .setParameter("empleadoId", empleadoId)
                 .getResultList();
    }
}
