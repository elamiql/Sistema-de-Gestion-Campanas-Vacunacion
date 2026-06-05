package org.example.model;

public class Cita {
    private int id;
    private String fechaHora;
    private String estado;

    public Cita(int id, String fechaHora) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.estado = "AGENDADA";
    }

    public int getId() { return id; }
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String f) { this.fechaHora = f; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Cita #" + id + " | " + fechaHora + " | " + estado;
    }
}