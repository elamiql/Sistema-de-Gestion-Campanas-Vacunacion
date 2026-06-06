package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Persona {
    private String rut;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String correo;
    private String telefono;
    private List<Cita> citas;

    // Constructor vacío
    public Persona() {
        this.citas = new ArrayList<>();
    }

    // Constructor con todos los parámetros
    public Persona(String rut, String nombres, String apellidos, String fechaNacimiento, String correo, String telefono) {
        this.rut = rut;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.telefono = telefono;
        this.citas = new ArrayList<>();
    }

    // Getters y Setters
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public void agregarCita(Cita cita) {
        this.citas.add(cita);
    }

    public void removeCita(Cita cita) {
        this.citas.remove(cita);
    }

    /**
     * Obtiene el historial de vacunación de la persona
     * Recorre todas las citas y extrae las vacunaciones realizadas
     */
    public List<Vacunacion> getHistorialVacunacion() {
        List<Vacunacion> historial = new ArrayList<>();

        // 1. Para cada cita [cita.getVacuna != null] cita = siguiente()
        for (Cita cita : this.citas) {
            // 1.1. getInfoVacuna()
            if (cita.getVacunacion() != null) {
                Vacunacion vacunacion = cita.getVacunacion();
                historial.add(vacunacion);
            }
        }

        return historial;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "rut='" + rut + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
