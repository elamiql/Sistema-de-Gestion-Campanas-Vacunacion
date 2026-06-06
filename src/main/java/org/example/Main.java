package org.example;

import org.example.builder.CampanaBuilder;
import org.example.model.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Prueba de getHistorialVacunacion() con 2 Personas ===\n");

        // Crear campanas
        CampanaBuilder cb = new CampanaBuilder();

        cb.setNombre("Campana COVID-19");
        cb.setDescripcion("Vacunacion contra COVID-19");
        cb.setFechaInicio("2024-01-01");
        cb.setFechaTermino("2024-12-31");
        Campana campana1 = cb.construir();

        cb.setNombre("Campana Influenza");
        cb.setDescripcion("Vacunacion contra Influenza");
        cb.setFechaInicio("2024-03-01");
        cb.setFechaTermino("2024-08-31");
        Campana campana2 = cb.construir();

        // Crear centro de vacunacion
        CentroVacunacion centro = new CentroVacunacion(
            1,
            "Centro de Salud Central",
            "Publico",
            "Av. Principal 123",
            "Santiago",
            "Metropolitana"
        );
        System.out.println("Centro de vacunacion: " + centro.getNombre() + "\n");

        // ===== PERSONA 1: JUAN PEREZ =====
        System.out.println("=== PERSONA 1: JUAN PEREZ ===\n");
        Persona persona1 = new Persona(
            "12345678-9",
            "Juan",
            "Perez",
            "1990-05-15",
            "juan@example.com",
            "987654321"
        );
        System.out.println("Creada: " + persona1.getNombres() + " " + persona1.getApellidos() + "\n");

        // Citas para Juan (sin vacunaciones aun)
        Cita cita1_p1 = new Cita(1, "2024-02-15 10:00", "COMPLETADA", campana1, centro);
        Cita cita2_p1 = new Cita(2, "2024-03-20 14:30", "COMPLETADA", campana1, centro);

        persona1.agregarCita(cita1_p1);
        persona1.agregarCita(cita2_p1);
        System.out.println("Citas agregadas: " + persona1.getCitas().size() + "\n");

        // Registrar vacunaciones usando registrarVacunacion()
        System.out.println("Registrando vacunaciones para Juan...");
        centro.registrarVacunacion(cita1_p1, campana1, "Primera dosis COVID-19", persona1);
        centro.registrarVacunacion(cita2_p1, campana1, "Segunda dosis COVID-19", persona1);

        // Mostrar historial de Juan
        System.out.println("--- HISTORIAL DE VACUNACION ---");
        List<Vacunacion> historial1 = persona1.getHistorialVacunacion();
        System.out.println("Total vacunaciones: " + historial1.size() + "\n");
        for (int i = 0; i < historial1.size(); i++) {
            Vacunacion vac = historial1.get(i);
            System.out.println("[" + (i + 1) + "] " + vac.getFechaHora() + " - " + vac.getObservaciones() + " (" + vac.getCampana().getNombre() + ")");
        }

        System.out.println("\n" + "=".repeat(60) + "\n");

        // ===== PERSONA 2: MARIA GARCIA =====
        System.out.println("=== PERSONA 2: MARIA GARCIA ===\n");
        Persona persona2 = new Persona(
            "98765432-1",
            "Maria",
            "Garcia",
            "1985-11-20",
            "maria@example.com",
            "912345678"
        );
        System.out.println("Creada: " + persona2.getNombres() + " " + persona2.getApellidos() + "\n");

        // Citas para Maria (sin vacunaciones aun)
        Cita cita1_p2 = new Cita(3, "2024-01-10 09:00", "COMPLETADA", campana1, centro);
        Cita cita2_p2 = new Cita(4, "2024-02-10 11:30", "COMPLETADA", campana1, centro);
        Cita cita3_p2 = new Cita(5, "2024-04-15 15:00", "COMPLETADA", campana2, centro);

        persona2.agregarCita(cita1_p2);
        persona2.agregarCita(cita2_p2);
        persona2.agregarCita(cita3_p2);
        System.out.println("Citas agregadas: " + persona2.getCitas().size() + "\n");

        // Registrar vacunaciones usando registrarVacunacion()
        System.out.println("Registrando vacunaciones para Maria...");
        centro.registrarVacunacion(cita1_p2, campana1, "Primera dosis COVID-19", persona2);
        centro.registrarVacunacion(cita2_p2, campana1, "Segunda dosis COVID-19", persona2);
        centro.registrarVacunacion(cita3_p2, campana2, "Dosis Influenza", persona2);

        // Mostrar historial de Maria
        System.out.println("--- HISTORIAL DE VACUNACION ---");
        List<Vacunacion> historial2 = persona2.getHistorialVacunacion();
        System.out.println("Total vacunaciones: " + historial2.size() + "\n");
        for (int i = 0; i < historial2.size(); i++) {
            Vacunacion vac = historial2.get(i);
            System.out.println("[" + (i + 1) + "] " + vac.getFechaHora() + " - " + vac.getObservaciones() + " (" + vac.getCampana().getNombre() + ")");
        }

        System.out.println("\n" + "=".repeat(60) + "\n");
        System.out.println("Prueba completada exitosamente");
        System.out.println("Juan tiene: " + historial1.size() + " vacunaciones");
        System.out.println("Maria tiene: " + historial2.size() + " vacunaciones");

        // ===== PRUEBA DE REGISTRAR VACUNACION =====
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== PRUEBA: REGISTRAR NUEVA VACUNACION ===");
        System.out.println("=".repeat(60) + "\n");

        // Crear una nueva cita para Juan
        Cita citaNueva = new Cita(
            10,
            "2024-05-20 10:00",
            "AGENDADA",
            campana1,  // Usa campana1 que ya tiene historial en Juan
            centro
        );

        System.out.println("Registrando nueva vacunacion para Juan...\n");
        boolean resultado = centro.registrarVacunacion(
            citaNueva,
            campana1,
            "Dosis de refuerzo COVID-19",
            persona1
        );

        if (resultado) {
            System.out.println("\nHistorial actualizado de Juan:");
            List<Vacunacion> historialActualizado = persona1.getHistorialVacunacion();
            System.out.println("Total vacunaciones: " + historialActualizado.size() + "\n");
            for (int i = 0; i < historialActualizado.size(); i++) {
                Vacunacion vac = historialActualizado.get(i);
                System.out.println("[" + (i + 1) + "] " + vac.getFechaHora() + " - " + vac.getObservaciones());
            }
        } else {
            System.out.println("\nError al registrar vacunacion");
        }
    }
}
