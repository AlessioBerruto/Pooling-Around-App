package com.example.poolingaround.services;

import com.example.poolingaround.models.Utente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UtenteService {

    private List<Utente> utenti;
    
    public UtenteService(List<Utente> utenti) {
        this.utenti = utenti;
    }

    public void aggiungiUtente() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Inserisci il nome:");
            String nome = scanner.nextLine();
            System.out.println("Inserisci il cognome:");
            String cognome = scanner.nextLine();
            System.out.println("Inserisci la data di nascita (dd/MM/yyyy):");
            String dataNascitaStr = scanner.nextLine();
            LocalDate dataNascita = LocalDate.parse(dataNascitaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.println("Inserisci l'indirizzo:");
            String indirizzo = scanner.nextLine();
            System.out.println("Inserisci il documento ID:");
            String documentoId = scanner.nextLine();
    
            int id = utenti.size() + 1; // genera un nuovo ID basato sulla dimensione della lista
            Utente nuovoUtente = new Utente(id, nome, cognome, dataNascita, indirizzo, documentoId);
            utenti.add(nuovoUtente);
    
            System.out.println("Utente aggiunto con successo.");
        } finally {
            scanner.close(); // Chiude lo scanner per evitare perdite di risorse
        }
    }
    

    public List<Utente> getUtenti() {
        return utenti;
    }
}
