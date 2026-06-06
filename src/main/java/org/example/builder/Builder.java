package org.example.builder;

/**
 * PATRÓN BUILDER — Interfaz del Builder.
 * Define los pasos de construcción de una Campana.
 */
public interface Builder {
    void setNombre(String nombre);
    void setDescripcion(String descripcion);
    void setFechaInicio(String fechaInicio);
    void setFechaTermino(String fechaTermino);
}