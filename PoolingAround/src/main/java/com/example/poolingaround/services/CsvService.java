package com.example.poolingaround.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.poolingaround.models.Utente;
import com.example.poolingaround.models.Viaggio;
import com.example.poolingaround.models.Prenotazione;

public class CsvService {

    public List<Utente> leggiUtenti(String filePath) {
        List<Utente> utenti = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create()
                     .setDelimiter(';')
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build())) {

            for (CSVRecord csvRecord : csvParser) {
                Utente utente = new Utente(
                    Integer.parseInt(csvRecord.get("ID")),
                    csvRecord.get("Nome"),
                    csvRecord.get("Cognome"),
                    LocalDate.parse(csvRecord.get("Data di nascita")),
                    csvRecord.get("Indirizzo"),
                    csvRecord.get("Documento ID")
                );
                utenti.add(utente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return utenti;
    }

    public List<Viaggio> leggiViaggi(String filePath) {
        List<Viaggio> viaggi = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create()
                     .setDelimiter(';')
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build())) {

            for (CSVRecord csvRecord : csvParser) {
                Viaggio viaggio = new Viaggio(
                    Integer.parseInt(csvRecord.get("ID")),
                    LocalDate.parse(csvRecord.get("Data")),
                    Integer.parseInt(csvRecord.get("Durata")),
                    csvRecord.get("Partenza"),
                    csvRecord.get("Arrivo"),
                    Boolean.parseBoolean(csvRecord.get("Disponibile"))
                );
                viaggi.add(viaggio);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return viaggi;
    }

    public List<Prenotazione> leggiPrenotazioni(String filePath) {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create()
                     .setDelimiter(';')
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build())) {

            for (CSVRecord csvRecord : csvParser) {
                Prenotazione prenotazione = new Prenotazione(
                    Integer.parseInt(csvRecord.get("ID")),
                    Integer.parseInt(csvRecord.get("ID Viaggio")),
                    Integer.parseInt(csvRecord.get("ID Utente"))
                );
                prenotazioni.add(prenotazione);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prenotazioni;
    }

    public void esportaViaggiDisponibili(List<Viaggio> viaggiDisponibili, String filePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create()
                     .setDelimiter(';')
                     .setHeader("ID", "Data", "Durata", "Partenza", "Arrivo")
                     .build())) {

            for (Viaggio viaggio : viaggiDisponibili) {
                csvPrinter.printRecord(
                    viaggio.getId(),
                    viaggio.getData(),
                    viaggio.getDurata(),
                    viaggio.getPartenza(),
                    viaggio.getArrivo()
                );
            }

            csvPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
