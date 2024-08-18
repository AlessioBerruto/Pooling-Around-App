package com.example.poolingaround.services;

import com.example.poolingaround.models.Utente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.time.format.DateTimeParseException;

public class UtenteService {

    private List<Utente> utenti;

    public UtenteService(List<Utente> utenti) {
        this.utenti = utenti;
    }

    public void aggiungiUtente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();    
        
        String nome = "";
        while (true) {
            System.out.println("Inserisci il nome (solo lettere e spazi):");
            nome = scanner.nextLine();
            if (Pattern.matches("[a-zA-Z ]+", nome)) {
                break;
            } else {
                System.out.println("Nome non valido. Inserisci solo lettere e spazi.");
            }
        }
    
        
        String cognome = "";
        while (true) {
            System.out.println("Inserisci il cognome (solo lettere e spazi):");
            cognome = scanner.nextLine();
            if (Pattern.matches("[a-zA-Z ]+", cognome)) {
                break;
            } else {
                System.out.println("Cognome non valido. Inserisci solo lettere e spazi.");
            }
        }
        
        
        LocalDate dataNascita = null;
        while (true) {
            System.out.println("Inserisci la data di nascita (dd/MM/yyyy):");
            String dataNascitaStr = scanner.nextLine();
            try {
                dataNascita = LocalDate.parse(dataNascitaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Data non valida. Inserisci una data nel formato dd/MM/yyyy.");
            }
        }        
        
        System.out.println("Inserisci l'indirizzo:");
        String indirizzo = scanner.nextLine();        
        
        System.out.println("Inserisci il documento ID:");
        String documentoId = scanner.nextLine();    
        
        int id = utenti.size() + 1;
        Utente nuovoUtente = new Utente(id, nome, cognome, dataNascita, indirizzo, documentoId);
        utenti.add(nuovoUtente);
    
        System.out.println("Utente aggiunto con successo.");
        System.out.println();
    }

    public List<Utente> getUtenti() {
        return utenti;
    }
}
