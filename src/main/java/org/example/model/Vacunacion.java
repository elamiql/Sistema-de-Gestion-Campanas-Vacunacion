package org.example.model;

public class Vacunacion {
    private int id;
    private String fechaHora;
    private String observaciones;
    private Campana campana;
    private Cita cita;

    // Constructor con todos los parámetros
    public Vacunacion(int id, String fechaHora, String observaciones) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.observaciones = observaciones;
    }

    // Constructor con campaña
    public Vacunacion(int id, String fechaHora, String observaciones, Campana campana) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.observaciones = observaciones;
        this.campana = campana;
    }

    // Constructor solo con fechaHora
    public Vacunacion(String fechaHora) {
        this.fechaHora = fechaHora;
        this.observaciones = "";
    }

    // Constructor con cita
    public Vacunacion(int id, String fechaHora, String observaciones, Campana campana, Cita cita) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.observaciones = observaciones;
        this.campana = campana;
        this.cita = cita;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Campana getCampana() {
        return campana;
    }

    public void setCampana(Campana campana) {
        this.campana = campana;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    @Override
    public String toString() {
        return "Vacunacion{" +
                "id=" + id +
                ", fechaHora='" + fechaHora + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", campana=" + campana +
                ", cita=" + (cita != null ? cita.getId() : null) +
                '}';
    }
}
