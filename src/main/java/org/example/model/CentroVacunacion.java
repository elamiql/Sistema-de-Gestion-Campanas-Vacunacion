package org.example.model;

public class CentroVacunacion {
    private int id;
    private String nombre;
    private String tipo;
    private String direccion;
    private String comuna;
    private String region;

    // Constructor con todos los parámetros
    public CentroVacunacion(int id, String nombre, String tipo, String direccion, String comuna, String region) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.direccion = direccion;
        this.comuna = comuna;
        this.region = region;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Registra una nueva vacunacion para una persona en una cita
     * Diagrama de comunicacion:
     * 1.1: p := getPersona() - obtiene la persona
     * 1.5: [si validacion OK] generarRegistro(obs) - genera el registro
     * 1.5.1: <<create>> (fecha, obs) - crea nueva Vacunacion
     * 1.2: historial := getCitas() - obtiene las citas
     * 1.3: [i = 1, N]; v := getVacunacion() - itera sobre citas
     * 1.4: [si v != null]; c := getCampana() - obtiene campana
     * 1.6: [si validacion OK] agregarVacunacion(nuevaVac)
     * 
     * @param citaActual la cita donde se registra la vacunacion
     * @param campaña la campana asociada
     * @param observaciones notas sobre la vacunacion
     * @param persona la persona que se vacuna
     * @return true si se registro exitosamente, false si hay error
     */
    public boolean registrarVacunacion(Cita citaActual, Campana campana, String observaciones, Persona persona) {
        
        // 1.1: p := getPersona()
        if (persona == null) {
            System.out.println("Error: Persona no registrada en el sistema");
            return false;
        }
        
        // 1.5: [si validacion OK] - Validar datos completos
        if (citaActual == null) {
            System.out.println("Error: Cita no valida");
            return false;
        }
        
        if (campana == null) {
            System.out.println("Error: Campana no especificada");
            return false;
        }
        
        if (observaciones == null || observaciones.isEmpty()) {
            System.out.println("Error: Observaciones requeridas");
            return false;
        }
        
        // 1.5.1: <<create>> (fecha, obs) - Crear nueva Vacunacion
        int vacunaId = (int) System.currentTimeMillis() % 10000;
        Vacunacion nuevaVacunacion = new Vacunacion(
            vacunaId,
            citaActual.getFechaHora(),
            observaciones,
            campana
        );
        
        System.out.println("[Vacunacion creada] ID: " + vacunaId + ", Fecha: " + citaActual.getFechaHora());
        
        // 1.2: historial := getCitas() - Obtener historial de citas
        java.util.List<Cita> historialCitas = persona.getCitas();
        
        // 1.3: [i = 1, N]; v := getVacunacion() - Iterar sobre citas (excluyendo la cita actual)
        boolean validacionOK = false;
        
        // Contar cuántas vacunaciones previas tiene esta persona (excluyendo la cita actual)
        int citasConVacunacion = 0;
        boolean campaanaYaExiste = false;
        
        for (Cita cita : historialCitas) {
            if (cita.getId() != citaActual.getId() && cita.getVacunacion() != null) {
                citasConVacunacion++;
                // 1.4: [si v != null]; c := getCampana() - Obtener campana
                Vacunacion v = cita.getVacunacion();
                Campana c = v.getCampana();
                
                if (c != null && c.getId() == campana.getId()) {
                    campaanaYaExiste = true;
                }
            }
        }
        
        // Si no hay citas previas con vacunación, es la primera vacunación (válida)
        if (citasConVacunacion == 0) {
            validacionOK = true;
            System.out.println("[Validacion OK] Primera vacunacion permitida");
        } else if (campaanaYaExiste) {
            // Si la campaña ya existe en el historial, es válida (dosis posterior)
            validacionOK = true;
            System.out.println("[Validacion OK] Campana " + campana.getNombre() + " existe en historial");
        } else {
            // Si es la primera vez de esta campaña, también es válida (nueva campaña)
            validacionOK = true;
            System.out.println("[Validacion OK] Primera vacunacion de campana " + campana.getNombre());
        }
        
        // 1.6: [si validacion OK] agregarVacunacion(nuevaVac)
        if (validacionOK) {
            citaActual.setVacunacion(nuevaVacunacion);
            nuevaVacunacion.setCita(citaActual);
            System.out.println("[Exito] Vacunacion registrada para " + persona.getNombres());
            return true;
        } else {
            System.out.println("[Validacion Fallida] No se pudo registrar la vacunacion");
            return false;
        }
    }

    @Override
    public String toString() {
        return "CentroVacunacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", comuna='" + comuna + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
