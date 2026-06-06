package org.example.model;

public class Cita {
    private int id;
    private String fechaHora;
    private String estado;
    private Campana campana;
    private CentroVacunacion centroVacunacion;
    private Vacunacion vacunacion;

    public Cita(int id, String fechaHora) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.estado = "AGENDADA";
    }

    // Constructor con todos los parámetros
    public Cita(int id, String fechaHora, String estado, Campana campana, CentroVacunacion centroVacunacion) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.campana = campana;
        this.centroVacunacion = centroVacunacion;
        this.vacunacion = null;
    }

    // Constructor con todos los parámetros incluida vacunación
    public Cita(int id, String fechaHora, String estado, Campana campana, CentroVacunacion centroVacunacion, Vacunacion vacunacion) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.campana = campana;
        this.centroVacunacion = centroVacunacion;
        this.vacunacion = vacunacion;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Campana getCampana() {
        return campana;
    }

    public void setCampana(Campana campana) {
        this.campana = campana;
    }

    public CentroVacunacion getCentroVacunacion() {
        return centroVacunacion;
    }

    public void setCentroVacunacion(CentroVacunacion centroVacunacion) {
        this.centroVacunacion = centroVacunacion;
    }

    public Vacunacion getVacunacion() {
        return vacunacion;
    }

    public void setVacunacion(Vacunacion vacunacion) {
        this.vacunacion = vacunacion;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", fechaHora='" + fechaHora + '\'' +
                ", estado='" + estado + '\'' +
                ", campana=" + campana +
                ", centroVacunacion=" + centroVacunacion +
                ", vacunacion=" + vacunacion +
                '}';
    }
}