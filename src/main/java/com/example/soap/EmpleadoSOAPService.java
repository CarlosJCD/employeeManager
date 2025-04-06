package com.example.soap;

import com.example.ejb.EmpleadoService;
import com.example.model.Empleado;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public class EmpleadoSOAPService {
    @EJB
    private EmpleadoService service;

    @WebMethod
    public void registrarEmpleado(String nombre, String puesto) {

    }

    // @WebMethod
    // public Empleado obtenerEmpleado(Long id) {
    // }
}