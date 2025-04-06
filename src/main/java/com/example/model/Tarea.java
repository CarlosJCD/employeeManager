package com.example.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Tarea implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private boolean completada;

    @ManyToOne
    private Empleado empleado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;}

    public String getDescripcion() { return descripcion;}
    public void setDescripcion(String descripcion) { this.descripcion = descripcion;}

    public boolean isCompletada() { return completada;}
    public void setCompletada(boolean completada) { this.completada = completada;}

    public Empleado getEmpleado() { return empleado;}
    public void setEmpleado(Empleado empleado) {this.empleado = empleado;}
}