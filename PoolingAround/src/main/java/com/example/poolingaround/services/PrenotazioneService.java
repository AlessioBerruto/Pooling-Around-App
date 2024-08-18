package com.example.poolingaround.services;

import com.example.poolingaround.models.Prenotazione;
import com.example.poolingaround.models.Viaggio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PrenotazioneService {

    private List<Prenotazione> prenotazioni;
    private List<Viaggio> viaggi;
    private String prenotazioniFilePath;

    public PrenotazioneService(List<Prenotazione> prenotazioni, List<Viaggio> viaggi, String prenotazioniFilePath) {
        this.prenotazioni = prenotazioni;
        this.viaggi = viaggi;
        this.prenotazioniFilePath = prenotazioniFilePath;
    }

    public void prenotaViaggio(int idViaggio, int idUtente) {        
        Viaggio viaggio = trovaViaggio(idViaggio);

        if (viaggio == null || !viaggio.isDisponibile()) {
            System.out.println("Viaggio non disponibile o inesistente.");
            System.out.println();
            return;
        }

        boolean prenotazioneEsistente = prenotazioni.stream()
                .anyMatch(p -> p.getIdViaggio() == idViaggio);

        if (prenotazioneEsistente) {
            System.out.println("Questo viaggio è già stato prenotato.");
            System.out.println();
            return;
        }
        
        int nuovoId = generaNuovoIdPrenotazione();

        Prenotazione nuovaPrenotazione = new Prenotazione(nuovoId, idViaggio, idUtente);

        prenotazioni.add(nuovaPrenotazione);
        viaggio.setDisponibile(false);

        System.out.println("Prenotazione effettuata con successo. ID Prenotazione: " + nuovoId);
        System.out.println();
    }

    public void cancellaPrenotazione(int idPrenotazione) {
        
        Prenotazione prenotazione = trovaPrenotazione(idPrenotazione);

        if (prenotazione == null) {
            System.out.println("Prenotazione non trovata.");
            System.out.println();
            return;
        }

        Viaggio viaggio = trovaViaggio(prenotazione.getIdViaggio());
        if (viaggio != null) {
            viaggio.setDisponibile(true);
        }

        prenotazioni.remove(prenotazione);
        System.out.println("Prenotazione cancellata con successo.");
        System.out.println();
    }

    private int generaNuovoIdPrenotazione() {
        Optional<Integer> maxId = prenotazioni.stream()
                .map(Prenotazione::getId)
                .max(Integer::compareTo);

        return maxId.map(id -> id + 1).orElse(1);
    }

    public void visualizzaPrenotazioniDaFile() {
        try (FileReader reader = new FileReader(prenotazioniFilePath);
                CSVParser csvParser = new CSVParser(reader,
                        CSVFormat.Builder.create(CSVFormat.DEFAULT)
                                .setDelimiter(';')
                                .setHeader()
                                .setSkipHeaderRecord(true)
                                .build())) {

            System.out.println();
            System.out.printf("%-15s | %-12s | %-10s%n", "ID Prenotazione", "ID Viaggio", "ID Utente");
            
            for (CSVRecord record : csvParser) {
                System.out.printf("%-15s | %-12s | %-10s%n",
                        record.get("ID"),
                        record.get("IDViaggio"),
                        record.get("IDUtente"));
            }
            System.out.println();

        } catch (IOException e) {
            System.out.println("Errore nella lettura del file prenotazioni.csv: " + e.getMessage());
        }
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
