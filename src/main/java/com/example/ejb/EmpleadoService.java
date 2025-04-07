package com.example.ejb;

import com.example.model.Empleado;
import jakarta.ejb.Stateless;
import jakarta.jws.WebService;
import jakarta.persistence.*;

@Stateless
public class EmpleadoService {
    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    public Empleado registrar(Empleado e) {
         em.persist(e);
        return e;
    }

    public void actualizar(Empleado e) {
        // em.merge(e);
    }

    public Empleado buscar(Long id) {
        return em.find(Empleado.class, id);
    }

    // public List<Empleado> listar() {
    // }
}
