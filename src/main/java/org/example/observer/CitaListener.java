package org.example.observer;

import org.example.model.Cita;

public interface CitaListener {
    void onCitaAgendada(Cita cita);
    void onCitaCancelada(Cita cita);
    void onCitaReprogramada(Cita cita);
}