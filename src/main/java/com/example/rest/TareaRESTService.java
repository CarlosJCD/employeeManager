package com.example.rest;

import com.example.ejb.TareaService;
import com.example.model.Empleado;
import com.example.model.Tarea;

import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.util.List;
import jakarta.ws.rs.core.Response;

@Path("tareas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TareaRESTService {

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    @EJB
    private TareaService tareaService;

    @POST
    @Path("asignar")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void asignar(Tarea tarea) {
        Empleado empleado = em.find(Empleado.class, tarea.getEmpleado().getId());
        if (empleado != null) {
            tarea.setEmpleado(empleado);
            tarea.setFechaTermino(null); // Asignar fecha de término a 7 días
            em.persist(tarea);  
        } else {
            throw new NotFoundException("Empleado no encontrado");
        }
    }

    @GET
    @Path("{id}")
    public List<Tarea> obtenerTareasPendientes(@PathParam("id") Long empleadoId) {
        return em.createQuery("SELECT t FROM Tarea t WHERE t.empleado.id = :empleadoId", Tarea.class)
                 .setParameter("empleadoId", empleadoId)
                 .getResultList();
    }

    @GET
    @Path("{empleadoId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tarea> obtenerTodasLasTareas(@PathParam("empleadoId") Long empleadoId) {
        // Consulta para obtener todas las tareas de un empleado sin importar si están completadas
        return em.createQuery("SELECT t FROM Tarea t WHERE t.empleado.id = :empleadoId", Tarea.class)
                .setParameter("empleadoId", empleadoId)
                .getResultList();
    }

    @PUT
    @Path("{id}/completar")
    public Response completarTarea(@PathParam("id") Long id, Tarea tarea) {
        try {
            tarea.setId(id);
            tarea = tareaService.completarTarea(tarea);
            return Response.ok(tarea).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al completar la tarea: " + e.getMessage())
                           .build();
        }
    }
}
