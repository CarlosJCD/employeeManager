package com.example.rest;

import com.example.model.Tarea;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("tareas")
@Produces(MediaType.APPLICATION_JSON)
public class TareaRESTService {


    @POST
    @Path("asignar")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void asignar(@FormParam("empleadoId") Long empleadoId, @FormParam("descripcion") String desc) {
        // tareaService.setEmpleado(new Empleado()); // Se debe buscar el empleado real por ID
        // tareaService.agregarTarea(desc);
    }

    // @GET
    // @Path("pendientes/{id}")
    // public List<Tarea> obtener(@PathParam("id") Long empleadoId) {

    // }
}