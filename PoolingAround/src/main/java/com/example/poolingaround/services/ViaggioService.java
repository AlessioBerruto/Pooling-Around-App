package com.example.poolingaround.services;

import com.example.poolingaround.models.Viaggio;
import com.example.poolingaround.models.Prenotazione;

import java.util.List;
import java.util.stream.Collectors;

public class ViaggioService {
    private List<Viaggio> viaggi;
    private List<Prenotazione> prenotazioni;

    public ViaggioService(List<Viaggio> viaggi, List<Prenotazione> prenotazioni) {
        this.viaggi = viaggi;
        this.prenotazioni = prenotazioni;
    }

    public List<Viaggio> getViaggi() {
        return viaggi;
    }

    public List<Viaggio> getViaggiDisponibili() {
        return viaggi.stream()
                .filter(Viaggio::isDisponibile)
                .collect(Collectors.toList());
    }

    public Viaggio trovaViaggioPerId(int id) {
        return viaggi.stream()
                .filter(viaggio -> viaggio.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void prenotaViaggio(int idViaggio, int idUtente) {
        Viaggio viaggio = trovaViaggioPerId(idViaggio);
        if (viaggio != null && viaggio.isDisponibile()) {
            viaggio.setDisponibile(false);
            prenotazioni.add(new Prenotazione(prenotazioni.size() + 1, idViaggio, idUtente));
            System.out.println("Prenotazione effettuata: Viaggio ID " + idViaggio + " per Utente ID " + idUtente);
        } else {
            System.out.println("Viaggio non disponibile.");
        }
    }

    public void cancellaPrenotazione(int idPrenotazione) {
        Prenotazione prenotazione = prenotazioni.stream()
                .filter(p -> p.getId() == idPrenotazione)
                .findFirst()
                .orElse(null);

        if (prenotazione != null) {
            Viaggio viaggio = trovaViaggioPerId(prenotazione.getIdViaggio());
            if (viaggio != null) {
                viaggio.setDisponibile(true);
            }
            prenotazioni.remove(prenotazione);
            System.out.println("Prenotazione cancellata: ID " + idPrenotazione);
        } else {
            System.out.println("Prenotazione non trovata.");
        }
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }
}
