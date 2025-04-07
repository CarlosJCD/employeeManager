package com.example.rest;

import com.example.ejb.EmpleadoService;
import com.example.model.Empleado;

import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("empleados")
@Produces(MediaType.APPLICATION_JSON)
public class EmpleadoRESTService {

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    @EJB
    private EmpleadoService empleadoService;

    @GET
    public List<Empleado> listar() {
        return em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Empleado actualizarEmpleado(Empleado em){
        empleadoService.actualizar(em);

        return em;
    }
}
