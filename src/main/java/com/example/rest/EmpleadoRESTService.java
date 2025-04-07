package com.example.rest;

import com.example.model.Empleado;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("empleados")
@Produces(MediaType.APPLICATION_JSON)
public class EmpleadoRESTService {

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    @GET
    public List<Empleado> listar() {
        return em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
    }
}
