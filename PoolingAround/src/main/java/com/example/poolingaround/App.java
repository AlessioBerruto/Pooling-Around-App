package com.example.poolingaround;

import com.example.poolingaround.models.Utente;
import com.example.poolingaround.models.Viaggio;
import com.example.poolingaround.models.Prenotazione;
import com.example.poolingaround.services.CsvService;
import com.example.poolingaround.services.PrenotazioneService;
import com.example.poolingaround.services.UtenteService;
import com.example.poolingaround.services.ViaggioService;

import java.util.List;
import java.util.Scanner;

public class App {

    private static final String FILE_UTENTI = "C:/Users/aless/OneDrive/Desktop/Alessio Lavoro/Progetti/Progetto Java Basics di Alessio Berruto/resources/utenti.csv";
    private static final String FILE_VIAGGI = "C:/Users/aless/OneDrive/Desktop/Alessio Lavoro/Progetti/Progetto Java Basics di Alessio Berruto/resources/viaggi.csv";
    private static final String FILE_PRENOTAZIONI = "C:/Users/aless/OneDrive/Desktop/Alessio Lavoro/Progetti/Progetto Java Basics di Alessio Berruto/resources/prenotazioni.csv";

    public static void main(String[] args) {
        CsvService csvService = new CsvService();

        List<Utente> utenti = csvService.leggiUtenti(FILE_UTENTI);
        List<Viaggio> viaggi = csvService.leggiViaggi(FILE_VIAGGI);
        List<Prenotazione> prenotazioni = csvService.leggiPrenotazioni(FILE_PRENOTAZIONI);

        UtenteService utenteService = new UtenteService(utenti);
        ViaggioService viaggioService = new ViaggioService(viaggi, csvService);
        PrenotazioneService prenotazioneService = new PrenotazioneService(prenotazioni, viaggi);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Scegli un'opzione:");
            System.out.println("1. Visualizza tutti i viaggi");
            System.out.println("2. Prenota un viaggio");
            System.out.println("3. Cancella una prenotazione");
            System.out.println("4. Aggiungi un nuovo utente");
            System.out.println("5. Esporta i viaggi disponibili");
            System.out.println("0. Esci");

            int scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1:
                    viaggioService.visualizzaViaggi();
                    break;
                case 2:
                    System.out.println("Inserisci ID Viaggio:");
                    int idViaggio = Integer.parseInt(scanner.nextLine());
                    System.out.println("Inserisci ID Utente:");
                    int idUtente = Integer.parseInt(scanner.nextLine());
                    prenotazioneService.prenotaViaggio(idViaggio, idUtente);
                    csvService.salvaViaggi(FILE_VIAGGI, viaggi);
                    csvService.salvaPrenotazioni(FILE_PRENOTAZIONI, prenotazioni);
                    break;
                case 3:
                    System.out.println("Inserisci ID Prenotazione:");
                    int idPrenotazione = Integer.parseInt(scanner.nextLine());
                    prenotazioneService.cancellaPrenotazione(idPrenotazione);
                    csvService.salvaViaggi(FILE_VIAGGI, viaggi);
                    csvService.salvaPrenotazioni(FILE_PRENOTAZIONI, prenotazioni);
                    break;
                case 4:
                    utenteService.aggiungiUtente();
                    csvService.salvaUtenti(FILE_UTENTI, utenti);
                    break;
                case 5:
                    String nomeFileEsportazione = viaggioService.getNomeFileEsportazione();
                    viaggioService.esportaViaggiDisponibili(nomeFileEsportazione);
                    System.out.println("Viaggi esportati con successo in " + nomeFileEsportazione);
                    break;
                case 0:
                    exit = true;
                    System.out.println("Uscita dal programma.");
                    break;
                default:
                    System.out.println("Opzione non valida, riprova.");
            }
        }
        scanner.close();
    }
}
