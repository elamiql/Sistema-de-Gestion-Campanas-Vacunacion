package org.example.model;

public class Campana {
    private int id;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaTermino;
    private String estado;

    // Constructor con todos los parámetros
    public Campana(int id, String nombre, String descripcion, String fechaInicio, String fechaTermino) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.estado = "ACTIVA";
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Campana{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaTermino='" + fechaTermino + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
