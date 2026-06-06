package org.example.builder;

import org.example.model.Campana;

/**
 * PATRÓN BUILDER (Creacional)
 * Responsabilidad: construir un objeto Campana paso a paso,
 * validando que todos los campos obligatorios estén presentes
 * antes de instanciar el producto.
 *
 * Problema que resuelve: evita constructores telescópicos y
 * centraliza la validación de campos requeridos antes de crear la Campana.
 */
public class CampanaBuilder implements Builder {

    // GRASP: Experto en Información — CampanaBuilder posee los datos
    // necesarios para construir una Campana
    private int contadorId = 0;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaTermino;

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    /**
     * PATRÓN BUILDER — punto de construcción del producto.
     * Valida que ningún campo obligatorio sea nulo o vacío antes de instanciar.
     * @return nueva instancia de Campana con estado "ACTIVA"
     * @throws RuntimeException si algún campo obligatorio es nulo o vacío
     */
    public Campana construir() throws RuntimeException {
        if (nombre == null || nombre.isEmpty()
                || descripcion == null || descripcion.isEmpty()
                || fechaInicio == null || fechaInicio.isEmpty()
                || fechaTermino == null || fechaTermino.isEmpty()) {
            throw new RuntimeException("Faltan campos obligatorios para construir la Campana.");
        }
        // PATRÓN BUILDER — se instancia el producto solo cuando todos los campos son válidos
        return new Campana(++contadorId, nombre, descripcion, fechaInicio, fechaTermino);
    }
}