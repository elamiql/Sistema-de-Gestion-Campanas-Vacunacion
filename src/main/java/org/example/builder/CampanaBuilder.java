package org.example.builder;

import org.example.model.Campana;

public class CampanaBuilder implements Builder {
    private int contadorId = 0;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaTermino;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public Campana construir() throws RuntimeException {
        if (nombre == null && descripcion == null && fechaInicio == null && fechaTermino == null) {
            throw new RuntimeException();
        }
        return new Campana(++contadorId, nombre, descripcion, fechaInicio, fechaTermino);
    }
}
