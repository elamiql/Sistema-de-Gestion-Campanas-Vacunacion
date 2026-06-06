package org.example;

import org.example.builder.CampanaBuilder;
import org.example.manager.CitaManager;
import org.example.model.*;
import org.example.observer.EmailNotificacionListener;
import org.example.observer.LogAuditoriaListener;
import org.example.observer.SMSNotificacionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CONTROLADOR PRINCIPAL — GRASP: Controlador de Fachada
 * Coordina el proceso BPMN "Agenda de Citas" de extremo a extremo.
 * No contiene lógica de negocio: delega a los objetos del dominio.
 *
 * Proceso BPMN implementado:
 *   1. Registrar Campaña       (patrón Builder)
 *   2. Registrar Centro        (modelo de dominio)
 *   3. Registrar Paciente      (modelo de dominio)
 *   4. Agendar Cita            (patrón Observer via CitaManager)
 *   5. Registrar Vacunación    (diagrama de comunicación creacional)
 *   6. Mostrar Historial       (diagrama de comunicación de consulta)
 */
public class Main2 {

    // Estado en memoria del sistema (simula persistencia)
    static List<Campana> campanas = new ArrayList<>();
    static List<CentroVacunacion> centros = new ArrayList<>();
    static List<Persona> personas = new ArrayList<>();

    // PATRÓN OBSERVER — CitaManager es el sujeto observable
    // Se suscriben tres listeners al arrancar el sistema
    static CitaManager citaManager = new CitaManager();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // PATRÓN OBSERVER — suscripción de observers al inicio del proceso
        citaManager.subscribe(new EmailNotificacionListener());
        citaManager.subscribe(new SMSNotificacionListener());
        citaManager.subscribe(new LogAuditoriaListener());

        // PATRÓN BUILDER — reutilizable entre registros de campaña
        CampanaBuilder cb = new CampanaBuilder();

        OutputOptions o = new OutputOptions();
        boolean sistemaFuncionando = true;

        System.out.println("=== Sistema de Gestión de Campaña de Vacunación ===\n");

        while (sistemaFuncionando) {
            o.initialOptions();
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {

                // ----------------------------------------------------------------
                // CASO 1 — Registrar Campaña
                // PATRÓN BUILDER: CampanaBuilder construye el objeto Campana
                // paso a paso y valida que todos los campos estén presentes
                // antes de instanciar.
                // ----------------------------------------------------------------
                case 1 -> {
                    System.out.print("Nombre de la campaña: ");
                    cb.setNombre(sc.nextLine());
                    System.out.print("Descripción de la campaña: ");
                    cb.setDescripcion(sc.nextLine());
                    System.out.print("Fecha de inicio (dd/MM/yyyy): ");
                    cb.setFechaInicio(sc.nextLine());
                    System.out.print("Fecha de término (dd/MM/yyyy): ");
                    cb.setFechaTermino(sc.nextLine());

                    // PATRÓN BUILDER — punto de construcción del producto
                    Campana nuevaCampana = cb.construir();
                    campanas.add(nuevaCampana);
                    System.out.println("[OK] Campaña registrada: " + nuevaCampana.getNombre() + "\n");
                }

                // ----------------------------------------------------------------
                // CASO 2 — Registrar Centro de Vacunación
                // GRASP: Creador — Main2 crea CentroVacunacion porque
                // centraliza los datos de inicialización del centro.
                // BPMN: actividad "Entregar campañas y centros activos"
                // requiere que los centros existan previamente.
                // ----------------------------------------------------------------
                case 2 -> {
                    if (campanas.isEmpty()) {
                        System.out.println("[ERROR] Debe registrar al menos una campaña primero.\n");
                        break;
                    }

                    System.out.print("Nombre del centro: ");
                    String nombre = sc.nextLine();
                    System.out.print("Tipo (hospital/consultorio/otro): ");
                    String tipo = sc.nextLine();
                    System.out.print("Dirección: ");
                    String direccion = sc.nextLine();
                    System.out.print("Comuna: ");
                    String comuna = sc.nextLine();
                    System.out.print("Región: ");
                    String region = sc.nextLine();

                    int idCentro = centros.size() + 1;
                    // GRASP: Creador — Main2 tiene todos los datos de inicialización
                    CentroVacunacion centro = new CentroVacunacion(idCentro, nombre, tipo, direccion, comuna, region);

                    // PATRÓN COMPOSITE — asociar el centro a la campaña seleccionada
                    System.out.println("Campañas disponibles:");
                    for (int i = 0; i < campanas.size(); i++) {
                        System.out.println("  " + (i + 1) + " - " + campanas.get(i).getNombre());
                    }
                    System.out.print("Asociar a campaña (número): ");
                    int idxCampana = sc.nextInt() - 1;
                    sc.nextLine();

                    if (idxCampana >= 0 && idxCampana < campanas.size()) {
                        // PATRÓN COMPOSITE — agregarCentro() añade la hoja al nodo
                        campanas.get(idxCampana).agregarCentro(centro);
                        System.out.println("[OK] Centro asociado a campaña: " + campanas.get(idxCampana).getNombre());
                    }

                    centros.add(centro);
                    System.out.println("[OK] Centro registrado: " + nombre + "\n");
                }

                // ----------------------------------------------------------------
                // CASO 3 — Registrar Paciente
                // GRASP: Creador — Main2 crea Persona con los datos ingresados.
                // BPMN: actividad "Entrega credenciales" / "Valida credenciales"
                // (en este prototipo se registra directamente sin autenticación externa).
                // ----------------------------------------------------------------
                case 3 -> {
                    System.out.print("RUT del paciente: ");
                    String rut = sc.nextLine();
                    System.out.print("Nombres: ");
                    String nombres = sc.nextLine();
                    System.out.print("Apellidos: ");
                    String apellidos = sc.nextLine();
                    System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
                    String fechaNac = sc.nextLine();
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = sc.nextLine();

                    // GRASP: Creador
                    Persona persona = new Persona(rut, nombres, apellidos, fechaNac, correo, telefono);
                    personas.add(persona);
                    System.out.println("[OK] Paciente registrado: " + nombres + " " + apellidos + "\n");
                }

                // ----------------------------------------------------------------
                // CASO 4 — Agendar Cita
                // BPMN: subproceso completo "Agenda de Citas":
                //   - Seleccionar centro y campaña activa
                //   - Filtrar días disponibles
                //   - Revisar disponibilidad de horarios (compuerta)
                //   - Seleccionar horario
                //   - Verificar información de contacto
                //   - Registrar cita → notificar (Observer)
                // PATRÓN OBSERVER — CitaManager.agendarCita() dispara notify()
                // a todos los listeners suscritos (Email, SMS, LogAuditoria).
                // ----------------------------------------------------------------
                case 4 -> {
                    if (personas.isEmpty() || centros.isEmpty() || campanas.isEmpty()) {
                        System.out.println("[ERROR] Debe haber al menos un paciente, un centro y una campaña.\n");
                        break;
                    }

                    // BPMN: "Seleccionar centro y campaña activa"
                    System.out.println("Pacientes registrados:");
                    for (int i = 0; i < personas.size(); i++) {
                        System.out.println("  " + (i + 1) + " - " + personas.get(i).getNombres()
                                + " " + personas.get(i).getApellidos());
                    }
                    System.out.print("Seleccionar paciente (número): ");
                    int idxPersona = sc.nextInt() - 1;
                    sc.nextLine();

                    System.out.println("Campañas activas:");
                    for (int i = 0; i < campanas.size(); i++) {
                        System.out.println("  " + (i + 1) + " - " + campanas.get(i).getNombre());
                    }
                    System.out.print("Seleccionar campaña (número): ");
                    int idxCampana = sc.nextInt() - 1;
                    sc.nextLine();

                    List<CentroVacunacion> centrosDeCampana = campanas.get(idxCampana).getCentros();
                    if (centrosDeCampana.isEmpty()) {
                        System.out.println("[ERROR] La campaña seleccionada no tiene centros asociados.\n");
                        break;
                    }

                    System.out.println("Centros de la campaña:");
                    for (int i = 0; i < centrosDeCampana.size(); i++) {
                        System.out.println("  " + (i + 1) + " - " + centrosDeCampana.get(i).getNombre()
                                + " (" + centrosDeCampana.get(i).getComuna() + ")");
                    }
                    System.out.print("Seleccionar centro (número): ");
                    int idxCentro = sc.nextInt() - 1;
                    sc.nextLine();

                    // BPMN: "Filtrar y entregar días de campaña"
                    System.out.println("Días disponibles:");
                    String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
                    for (int i = 0; i < dias.length; i++) {
                        System.out.println("  " + (i + 1) + " - " + dias[i]);
                    }
                    System.out.print("Seleccionar día (número): ");
                    int idxDia = sc.nextInt() - 1;
                    sc.nextLine();

                    // BPMN: "Revisar disponibilidad de horarios" (compuerta)
                    String[] horarios = {"09:00", "10:00", "11:00", "12:00", "15:00", "16:00"};
                    System.out.println("Horarios disponibles para " + dias[idxDia] + ":");

                    // BPMN: compuerta — ¿hay horarios disponibles?
                    if (horarios.length == 0) {
                        // BPMN: camino NO — "Avisar que no hay horarios"
                        System.out.println("[AVISO] No hay horarios disponibles para ese día.\n");
                        break;
                    }

                    // BPMN: camino SÍ — "Mostrar horarios disponibles"
                    for (int i = 0; i < horarios.length; i++) {
                        System.out.println("  " + (i + 1) + " - " + horarios[i]);
                    }
                    System.out.print("Seleccionar horario (número): ");
                    int idxHorario = sc.nextInt() - 1;
                    sc.nextLine();

                    String fechaHora = dias[idxDia] + " " + horarios[idxHorario];

                    // BPMN: "Verificar información de contacto"
                    Persona paciente = personas.get(idxPersona);
                    System.out.println("\nVerificación de contacto:");
                    System.out.println("  Nombre : " + paciente.getNombres() + " " + paciente.getApellidos());
                    System.out.println("  Correo : " + paciente.getCorreo());
                    System.out.println("  Teléfono: " + paciente.getTelefono());
                    System.out.print("¿Confirmar cita? (s/n): ");
                    String confirmar = sc.nextLine();

                    if (!confirmar.equalsIgnoreCase("s")) {
                        System.out.println("[CANCELADO] Cita no registrada.\n");
                        break;
                    }

                    // BPMN: "Registrar cita" + "Enviar correo de confirmación"
                    // PATRÓN OBSERVER — agendarCita() llama internamente a notificar()
                    // que dispara onCitaAgendada() en Email, SMS y LogAuditoria
                    citaManager.agendarCita(fechaHora);
                    Cita nuevaCita = citaManager.getCitas().getLast();

                    nuevaCita.setCampana(campanas.get(idxCampana));
                    nuevaCita.setCentroVacunacion(centrosDeCampana.get(idxCentro));

                    // Asociar cita al paciente y al centro (Composite)
                    paciente.agregarCita(nuevaCita);
                    centrosDeCampana.get(idxCentro).agregarCita(nuevaCita);

                    System.out.println("[OK] Cita agendada para " + fechaHora + "\n");
                }

                // ----------------------------------------------------------------
                // CASO 5 — Registrar Vacunación
                // GRASP: Controlador de Fachada — CentroVacunacion.registrarVacunacion()
                // coordina el flujo del diagrama de comunicación creacional:
                //   1.1 getPersona(), 1.2 getCitas(), 1.3 getVacunacion(),
                //   1.4 getCampana(), 1.5 generarRegistro(), 1.5.1 <<create>>,
                //   1.6 agregarVacunacion()
                // PATRÓN COMPOSITE — al registrar, la cita queda en el centro (hoja)
                // y el conteo sube automáticamente a la campaña (nodo).
                // ----------------------------------------------------------------
                case 5 -> {
                    if (personas.isEmpty() || centros.isEmpty()) {
                        System.out.println("[ERROR] Debe haber pacientes y centros registrados.\n");
                        break;
                    }

                    System.out.println("Pacientes con citas agendadas:");
                    List<Persona> conCitas = personas.stream()
                            .filter(p -> !p.getCitas().isEmpty()).toList();

                    if (conCitas.isEmpty()) {
                        System.out.println("[ERROR] No hay pacientes con citas agendadas.\n");
                        break;
                    }

                    for (int i = 0; i < conCitas.size(); i++) {
                        System.out.println("  " + (i + 1) + " - " + conCitas.get(i).getNombres()
                                + " " + conCitas.get(i).getApellidos());
                    }
                    System.out.print("Seleccionar paciente (número): ");
                    int idxPersona = sc.nextInt() - 1;
                    sc.nextLine();

                    Persona paciente = conCitas.get(idxPersona);

                    System.out.println("Citas del paciente:");
                    List<Cita> citasPaciente = paciente.getCitas().stream()
                            .filter(c -> c.getVacunacion() == null).toList();

                    if (citasPaciente.isEmpty()) {
                        System.out.println("[INFO] Este paciente ya tiene todas sus citas vacunadas.\n");
                        break;
                    }

                    for (int i = 0; i < citasPaciente.size(); i++) {
                        System.out.println("  " + (i + 1) + " - " + citasPaciente.get(i).getFechaHora()
                                + " [" + citasPaciente.get(i).getEstado() + "]");
                    }
                    System.out.print("Seleccionar cita (número): ");
                    int idxCita = sc.nextInt() - 1;
                    sc.nextLine();

                    System.out.print("Observaciones de la vacunación: ");
                    String observaciones = sc.nextLine();

                    Cita citaSeleccionada = citasPaciente.get(idxCita);
                    CentroVacunacion centroAsignado = citaSeleccionada.getCentroVacunacion();
                    Campana campanaAsignada = citaSeleccionada.getCampana();

                    if (centroAsignado == null || campanaAsignada == null) {
                        System.out.println("[ERROR] La cita no tiene centro o campaña asignada.\n");
                        break;
                    }

                    // GRASP: Controlador de Fachada — se delega a CentroVacunacion
                    // Diagrama de comunicación creacional completo:
                    // 1.1→1.6 ejecutados dentro de registrarVacunacion()
                    boolean exito = centroAsignado.registrarVacunacion(
                            citaSeleccionada, campanaAsignada, observaciones, paciente);

                    if (exito) {
                        // PATRÓN COMPOSITE — al agregar la vacunacion a la campaña,
                        // getVacunas() en Campana suma automáticamente los de sus centros hijos
                        campanaAsignada.agregarVacunacion(citaSeleccionada.getVacunacion());
                        System.out.println("[OK] Vacunación registrada correctamente.\n");
                        System.out.println("[COMPOSITE] Total citas en campaña '"
                                + campanaAsignada.getNombre() + "': " + campanaAsignada.getCitas());
                        System.out.println("[COMPOSITE] Total vacunas en campaña '"
                                + campanaAsignada.getNombre() + "': " + campanaAsignada.getVacunas() + "\n");
                    }
                }

                // ----------------------------------------------------------------
                // CASO 6 — Mostrar Historial del Paciente
                // GRASP: Experto en Información — Persona es la experta que
                // conoce sus propias citas (getHistorialVacunacion()).
                // Diagrama de comunicación de consulta:
                //   getHistorialVacunacion() → 1.* getCitas() →
                //   1.1 getInfoVacuna() → 1.1.1 getDescripcion() →
                //   1.1.1.1 getNombre() [Campana] → 1.1.2 getNombre() [Centro]
                // ----------------------------------------------------------------
                case 6 -> {
                    if (personas.isEmpty()) {
                        System.out.println("[ERROR] No hay pacientes registrados.\n");
                        break;
                    }

                    System.out.println("Pacientes registrados:");
                    for (int i = 0; i < personas.size(); i++) {
                        System.out.println("  " + (i + 1) + " - " + personas.get(i).getNombres()
                                + " " + personas.get(i).getApellidos());
                    }
                    System.out.print("Seleccionar paciente (número): ");
                    int idxPersona = sc.nextInt() - 1;
                    sc.nextLine();

                    Persona paciente = personas.get(idxPersona);

                    // GRASP: Experto en Información
                    // Diagrama consulta — mensaje: getHistorialVacunacion()
                    List<Vacunacion> historial = paciente.getHistorialVacunacion();

                    System.out.println("\n=== Historial de vacunación: "
                            + paciente.getNombres() + " " + paciente.getApellidos() + " ===");

                    if (historial.isEmpty()) {
                        System.out.println("  Sin vacunaciones registradas.\n");
                        break;
                    }

                    for (Vacunacion v : historial) {
                        // Diagrama consulta — mensaje 1.1.1: getDescripcion() / getObservaciones()
                        System.out.println("  Fecha     : " + v.getFechaHora());
                        System.out.println("  Observ.   : " + v.getObservaciones());

                        // Diagrama consulta — mensaje 1.1.1.1: getNombre() [Campana]
                        if (v.getCampana() != null) {
                            System.out.println("  Campaña   : " + v.getCampana().getNombre());
                        }

                        // Diagrama consulta — mensaje 1.1.2: getNombre() [CentroVacunacion]
                        if (v.getCita() != null && v.getCita().getCentroVacunacion() != null) {
                            System.out.println("  Centro    : " + v.getCita().getCentroVacunacion().getNombre());
                        }

                        System.out.println("  ----------");
                    }
                    System.out.println();
                }

                case 7 -> {
                    System.out.println("Saliendo del sistema...");
                    sistemaFuncionando = false;
                }

                default -> System.out.println("[ERROR] Opción no válida.\n");
            }
        }

        sc.close();
    }
}

/**
 * GRASP: Alta Cohesión — clase auxiliar responsable únicamente
 * de imprimir las opciones del menú principal.
 */
class OutputOptions {
    public void initialOptions() {
        System.out.println("1 - Registrar Campaña         [BUILDER]");
        System.out.println("2 - Registrar Centro          [COMPOSITE]");
        System.out.println("3 - Registrar Paciente");
        System.out.println("4 - Agendar Cita              [OBSERVER]");
        System.out.println("5 - Registrar Vacunación      [COMPOSITE + GRASP Controlador]");
        System.out.println("6 - Mostrar Historial         [GRASP Experto]");
        System.out.println("7 - Salir\n");
        System.out.print("Opción: ");
    }
}