package org.example.builder;

import java.time.LocalDate;

public interface Builder {
    void setNombre(String nombre);
    void setDescripcion(String descripcion);
    void setFechaInicio(LocalDate fechaInicio);
    void setFechaTermino(LocalDate fechaTermino);
}
