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
         Empleado empleado = em.find(Empleado.class, id);
        if (empleado != null) {
            empleado.setId(e.getId);
            empleado.setNombre(e.getNombre);
            empleado.setPuesto(e.getPuesto);
            
            em.merge(empleado);
        } else {
            throw new IllegalArgumentException("Empleado no encontrado");
        }
    }

    public Empleado buscar(Long id) {
        return em.find(Empleado.class, id);
    }

    // public List<Empleado> listar() {
    // }
}
