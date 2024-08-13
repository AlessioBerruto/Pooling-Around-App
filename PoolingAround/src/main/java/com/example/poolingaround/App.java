package com.example.poolingaround;

import com.example.poolingaround.models.Utente;
import com.example.poolingaround.models.Viaggio;
import com.example.poolingaround.models.Prenotazione;
import com.example.poolingaround.services.CsvService;
import com.example.poolingaround.services.UtenteService;
import com.example.poolingaround.services.ViaggioService;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class App {
    // Definisci il formato per la data
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        CsvService csvService = new CsvService();

        // Caricamento dei dati dagli CSV
        List<Utente> utenti = csvService.leggiUtenti("resources/utenti.csv");
        List<Viaggio> viaggi = csvService.leggiViaggi("resources/viaggi.csv");
        List<Prenotazione> prenotazioni = csvService.leggiPrenotazioni("resources/prenotazioni.csv");

        // Inizializzazione dei servizi
        UtenteService utenteService = new UtenteService(utenti);
        ViaggioService viaggioService = new ViaggioService(viaggi, prenotazioni);
        

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;

        while (true) {
            System.out.println("Seleziona un comando:");
            System.out.println("1 - Visualizzare tutti i viaggi");
            System.out.println("2 - Prenotare un viaggio");
            System.out.println("3 - Disdire una prenotazione");
            System.out.println("4 - Aggiungere un nuovo utente");
            System.out.println("5 - Esportare i viaggi disponibili");
            System.out.println("0 - Uscire");

            try {
                input = reader.readLine();

                switch (input) {
                    case "1":
                        viaggioService.visualizzaViaggi();
                        break;
                    case "2":
                        viaggioService.prenotaViaggio();
                        break;
                    case "3":
                        viaggioService.cancellaPrenotazione();
                        break;

                    case "4":
                        System.out.println("Inserisci ID Utente:");
                        int id = Integer.parseInt(reader.readLine());
                        System.out.println("Inserisci Nome:");
                        String nome = reader.readLine();
                        System.out.println("Inserisci Cognome:");
                        String cognome = reader.readLine();
                        System.out.println("Inserisci Data di Nascita (dd/MM/yyyy):");
                        String dataNascita = reader.readLine();
                        System.out.println("Inserisci Indirizzo:");
                        String indirizzo = reader.readLine();
                        System.out.println("Inserisci Documento ID:");
                        String documentoId = reader.readLine();

                        Utente utente = new Utente(id, nome, cognome, LocalDate.parse(dataNascita, FORMATTER),
                                indirizzo, documentoId);
                        utenteService.aggiungiUtente(utente);
                        break;

                    case "5":
                        csvService.esportaViaggiDisponibili(viaggioService.getViaggiDisponibili(),
                                csvService.getNomeFileEsportazione());
                        break;
                    case "0":
                        System.out.println("Uscita dal programma...");
                        return;
                    default:
                        System.out.println("Comando non riconosciuto.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Errore di input: " + e.getMessage());
            }
        }
    }
}
