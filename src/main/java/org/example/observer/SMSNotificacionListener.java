package org.example.observer;

import org.example.model.Cita;

public class SMSNotificacionListener implements CitaListener {
    @Override
    public void onCitaAgendada(Cita cita) {
        System.out.println("[SMS] Cita #" + cita.getId() + " agendada para " + cita.getFechaHora());
    }
    @Override
    public void onCitaCancelada(Cita cita) {
        System.out.println("[SMS] Cita #" + cita.getId() + " cancelada.");
    }
    @Override
    public void onCitaReprogramada(Cita cita) {
        System.out.println("[SMS] Cita #" + cita.getId() + " reprogramada para " + cita.getFechaHora());
    }
}