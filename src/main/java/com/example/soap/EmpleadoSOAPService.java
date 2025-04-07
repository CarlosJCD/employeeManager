package com.example.soap;

import com.example.model.Empleado;
import com.example.ejb.EmpleadoService;

import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@WebService(
        name = "EmpleadoSOAPService",
        serviceName = "EmpleadoService"
)
@SOAPBinding(style = Style.DOCUMENT)
public class EmpleadoSOAPService {

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    @WebMethod(operationName = "registrarEmpleado")
    @WebResult(name = "empleado")
    @Transactional
    public Empleado registrarEmpleado(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "puesto") String puesto) {

        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setPuesto(puesto);
        
        em.persist(empleado);

        return empleado;
    }

    @WebMethod(operationName = "obtenerEmpleado")
    @WebResult(name = "empleado")
    @Transactional
    public Empleado obtenerEmpleado(@WebParam(name = "id") Long id) {
        return em.find(Empleado.class, id);
    }
}