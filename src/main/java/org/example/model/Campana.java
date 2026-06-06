package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Campana implements ComponenteVacunacion {
    private int id;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaTermino;
    private String estado;
    private List<CentroVacunacion> centros = new ArrayList<>();
    private List<Vacunacion> vacunaciones = new ArrayList<>();

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

    // --- Composite: gestión de centros y vacunaciones ---

    public void agregarCentro(CentroVacunacion centro) {
        this.centros.add(centro);
    }

    public void eliminarCentro(CentroVacunacion centro) {
        this.centros.remove(centro);
    }

    public List<CentroVacunacion> getCentros() {
        return centros;
    }

    public void agregarVacunacion(Vacunacion vacunacion) {
        this.vacunaciones.add(vacunacion);
    }

    public List<Vacunacion> getVacunaciones() {
        return vacunaciones;
    }

    // Nodo composite: delega a sus hijos (centros) y suma los resultados
    @Override
    public int getCitas() {
        return centros.stream()
                .mapToInt(ComponenteVacunacion::getCitas)
                .sum();
    }

    // Nodo composite: delega a sus hijos (centros) y suma los resultados
    @Override
    public int getVacunas() {
        return centros.stream()
                .mapToInt(ComponenteVacunacion::getVacunas)
                .sum();
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