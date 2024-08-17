package com.example.poolingaround.services;

import com.example.poolingaround.models.Prenotazione;
import com.example.poolingaround.models.Viaggio;

import java.util.List;

public class PrenotazioneService {

    private List<Prenotazione> prenotazioni;
    private List<Viaggio> viaggi;

    public PrenotazioneService(List<Prenotazione> prenotazioni, List<Viaggio> viaggi) {
        this.prenotazioni = prenotazioni;
        this.viaggi = viaggi;
    }

    
    public void prenotaViaggio(int idViaggio, int idUtente) {
        Viaggio viaggio = trovaViaggio(idViaggio);

        if (viaggio == null || !viaggio.isDisponibile()) {
            System.out.println("Viaggio non disponibile o inesistente.");
            return;
        }

        boolean prenotazioneEsistente = prenotazioni.stream()
                .anyMatch(p -> p.getIdViaggio() == idViaggio);

        if (prenotazioneEsistente) {
            System.out.println("Questo viaggio è già stato prenotato.");
            return;
        }

        Prenotazione nuovaPrenotazione = new Prenotazione(idViaggio, idViaggio, idUtente);

        prenotazioni.add(nuovaPrenotazione);
        viaggio.setDisponibile(false);

        System.out.println("Prenotazione effettuata con successo. ID Prenotazione: " + idViaggio);
    }

    
    public void cancellaPrenotazione(int idPrenotazione) {
        Prenotazione prenotazione = trovaPrenotazione(idPrenotazione);

        if (prenotazione == null) {
            System.out.println("Prenotazione non trovata.");
            return;
        }

        Viaggio viaggio = trovaViaggio(prenotazione.getIdViaggio());
        if (viaggio != null) {
            viaggio.setDisponibile(true);
        }

        prenotazioni.remove(prenotazione);
        System.out.println("Prenotazione cancellata con successo.");
    }

    private Viaggio trovaViaggio(int idViaggio) {
        return viaggi.stream().filter(v -> v.getId() == idViaggio).findFirst().orElse(null);
    }

    private Prenotazione trovaPrenotazione(int idPrenotazione) {
        return prenotazioni.stream().filter(p -> p.getId() == idPrenotazione).findFirst().orElse(null);
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }
}
