package com.example.poolingaround.services;

import com.example.poolingaround.models.Utente;

import java.time.LocalDate;
import java.util.List;

public class UtenteService {
    private List<Utente> utenti;

    public UtenteService(List<Utente> utenti) {
        this.utenti = utenti;
    }

    public void aggiungiUtente(Utente utente) {
        utenti.add(utente);
        System.out.println("Utente aggiunto: " + utente);
    }

    public List<Utente> getUtenti() {
        return utenti;
    }

    public Utente trovaUtentePerId(int id) {
        return utenti.stream()
                .filter(utente -> utente.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    // Metodo per creare un nuovo Utente da dati CSV
    public Utente creaUtente(int id, String nome, String cognome, LocalDate dataNascita, String indirizzo, String documentoId) {
    return new Utente(id, nome, cognome, dataNascita, indirizzo, documentoId);
}

}
