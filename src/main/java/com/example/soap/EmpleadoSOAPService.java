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


@WebService(
        name = "EmpleadoSOAPService",
        serviceName = "EmpleadoService"
)
@SOAPBinding(style = Style.DOCUMENT)
public class EmpleadoSOAPService {

    @EJB
    private EmpleadoService empleadoService;

    @WebMethod(operationName = "registrarEmpleado")
    @WebResult(name = "empleado")
    public Empleado registrarEmpleado(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "puesto") String puesto) {

        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setPuesto(puesto);

        return empleadoService.registrar(empleado);
    }

    @WebMethod(operationName = "obtenerEmpleado")
    @WebResult(name = "empleado")
    public Empleado obtenerEmpleado(@WebParam(name = "id") Long id) {
        return empleadoService.buscar(id);
    }
}