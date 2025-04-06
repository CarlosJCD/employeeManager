package com.example.ejb;

import com.example.model.Empleado;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

@Stateless
public class EmpleadoService {
    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    public void registrar(Empleado e) {
        // em.persist(e);
    }

    public void actualizar(Empleado e) {
        // em.merge(e);
    }

    // public Empleado buscar(Long id) {
    // }

    // public List<Empleado> listar() {
    // }
}
