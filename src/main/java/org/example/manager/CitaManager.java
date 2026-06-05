package org.example.manager;

import org.example.model.Cita;
import org.example.observer.CitaListener;
import java.util.ArrayList;
import java.util.List;

public class CitaManager {
    private List<CitaListener> listeners = new ArrayList<>();
    private List<Cita> citas = new ArrayList<>();
    private int contadorId = 1;

    public void subscribe(CitaListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(CitaListener listener) {
        listeners.remove(listener);
    }

    private void notificar(Cita cita, String evento) {
        for (CitaListener l : listeners) {
            switch (evento) {
                case "AGENDADA" -> l.onCitaAgendada(cita);
                case "CANCELADA" -> l.onCitaCancelada(cita);
                case "REPROGRAMADA" -> l.onCitaReprogramada(cita);
            }
        }
    }

    public void agendarCita(String fechaHora) {
        Cita cita = new Cita(contadorId++, fechaHora);
        citas.add(cita);
        notificar(cita, "AGENDADA");
    }

    public void cancelarCita(int id) {
        Cita cita = buscarPorId(id);
        if (cita == null) { System.out.println("Cita no encontrada."); return; }
        cita.setEstado("CANCELADA");
        notificar(cita, "CANCELADA");
    }

    public void reprogramarCita(int id, String nuevaFecha) {
        Cita cita = buscarPorId(id);
        if (cita == null) { System.out.println("Cita no encontrada."); return; }
        cita.setFechaHora(nuevaFecha);
        cita.setEstado("REPROGRAMADA");
        notificar(cita, "REPROGRAMADA");
    }

    public void listarCitas() {
        if (citas.isEmpty()) { System.out.println("No hay citas registradas."); return; }
        citas.forEach(System.out::println);
    }

    public List<Cita> getCitas() { return citas; }

    private Cita buscarPorId(int id) {
        return citas.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }
}