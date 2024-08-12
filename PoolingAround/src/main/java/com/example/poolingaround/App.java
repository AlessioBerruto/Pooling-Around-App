package com.example.poolingaround;

import com.example.poolingaround.models.Utente;
import com.example.poolingaround.models.Viaggio;
import com.example.poolingaround.models.Prenotazione;
import com.example.poolingaround.services.CsvService;
import com.example.poolingaround.services.UtenteService;
import com.example.poolingaround.services.ViaggioService;
import com.example.poolingaround.services.PrenotazioneService;

import java.util.List;

public class App {
    public static void main(String[] args) {
        CsvService csvService = new CsvService();

        // Caricamento dei dati dagli CSV
        List<Utente> utenti = csvService.leggiUtenti("resources/utenti.csv");
        List<Viaggio> viaggi = csvService.leggiViaggi("resources/viaggi.csv");
        List<Prenotazione> prenotazioni = csvService.leggiPrenotazioni("resources/prenotazioni.csv");

        // Inizializzazione dei servizi
        UtenteService utenteService = new UtenteService(utenti);
        ViaggioService viaggioService = new ViaggioService(viaggi);
        PrenotazioneService prenotazioneService = new PrenotazioneService(prenotazioni);

        // Operazioni varie
        // Ad esempio, salvare i viaggi disponibili
        List<Viaggio> viaggiDisponibili = viaggioService.getViaggiDisponibili();
        csvService.esportaViaggiDisponibili(viaggiDisponibili, "resources/viaggi_disponibili.csv");

        // Aggiungi qui altre operazioni che desideri eseguire
    }
}
