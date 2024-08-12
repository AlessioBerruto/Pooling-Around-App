package com.example.poolingaround.services;

import com.example.poolingaround.models.Prenotazione;

import java.util.List;

public class PrenotazioneService {
    private List<Prenotazione> prenotazioni;

    public PrenotazioneService(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public Prenotazione trovaPrenotazionePerId(int id) {
        return prenotazioni.stream()
                .filter(prenotazione -> prenotazione.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void aggiungiPrenotazione(Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
    }

    public void cancellaPrenotazione(int idPrenotazione) {
        Prenotazione prenotazione = trovaPrenotazionePerId(idPrenotazione);
        if (prenotazione != null) {
            prenotazioni.remove(prenotazione);
        }
    }
}
