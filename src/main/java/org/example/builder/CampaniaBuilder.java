package org.example.builder;

import org.example.model.Campana;

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

    public Campana construir() {
        if (nombre == null || descripcion == null || fechaInicio == null || fechaTermino == null) {
            throw new RuntimeException("Faltan campos obligatorios para construir la Campana");
        }
        return new Campana(
                (int) System.currentTimeMillis() % 10000,
                nombre,
                descripcion,
                fechaInicio.toString(),
                fechaTermino.toString(),
                "ACTIVA"
        );
    }
}
