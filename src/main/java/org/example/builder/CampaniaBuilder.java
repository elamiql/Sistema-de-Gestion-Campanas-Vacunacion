package org.example.builder;

import java.time.LocalDate;

public class CampaniaBuilder implements Builder {
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    // void hasta que se cree clase Campaña
    public void construir() throws RuntimeException {
        if (nombre == null && descripcion == null && fechaInicio == null && fechaTermino == null) {
            throw new RuntimeException();
        }
        // return new ...
    }
}
