package com.example.poolingaround.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import com.example.poolingaround.models.Utente;
import com.example.poolingaround.models.Viaggio;
import com.example.poolingaround.models.Prenotazione;

public class CsvService {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd/MM/yyyy")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

    public List<Utente> leggiUtenti(String filePath) {
        List<Utente> utenti = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create()
                     .setDelimiter(';')
                     .setHeader("ID", "Nome", "Cognome", "Data di nascita", "Indirizzo", "Documento ID")
                     .setSkipHeaderRecord(true)
                     .build())) {

            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.size() < 6) {
                    System.err.println("Riga vuota o malformattata: " + csvRecord);
                    continue; // Salta righe malformattate
                }

                LocalDate dataDiNascita;
                try {
                    dataDiNascita = LocalDate.parse(csvRecord.get("Data di nascita"), FORMATTER);
                } catch (Exception e) {
                    System.err.println("Data di nascita non valida per il record: " + csvRecord);
                    continue; // Salta il record con data non valida
                }
                Utente utente = new Utente(
                    Integer.parseInt(csvRecord.get("ID")),
                    csvRecord.get("Nome"),
                    csvRecord.get("Cognome"),
                    dataDiNascita,
                    csvRecord.get("Indirizzo"),
                    csvRecord.get("Documento ID"));
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
                     .setHeader("ID", "Data", "Durata", "Partenza", "Arrivo", "Disponibile")
                     .setSkipHeaderRecord(true)
                     .build())) {

            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.size() < 6) {
                    System.err.println("Riga vuota o malformattata: " + csvRecord);
                    continue; // Salta righe malformattate
                }

                LocalDate data;
                String dataStr = csvRecord.get("Data");
                if (dataStr == null || dataStr.trim().isEmpty()) {
                    System.err.println("Data non valida per il record: " + csvRecord);
                    continue; // Salta il record con data non valida
                }

                try {
                    data = LocalDate.parse(dataStr, FORMATTER);
                } catch (Exception e) {
                    System.err.println("Errore di parsing per la data: " + dataStr + " nel record: " + csvRecord);
                    continue; // Salta il record con data non valida
                }

                Viaggio viaggio = new Viaggio(
                        Integer.parseInt(csvRecord.get("ID")),
                        data,
                        Integer.parseInt(csvRecord.get("Durata")),
                        csvRecord.get("Partenza"),
                        csvRecord.get("Arrivo"),
                        Boolean.parseBoolean(csvRecord.get("Disponibile")));
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
                     .setHeader("ID", "ID Viaggio", "ID Utente")
                     .setSkipHeaderRecord(true)
                     .build())) {

            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.size() < 3) {
                    System.err.println("Riga vuota o malformattata: " + csvRecord);
                    continue; // Salta righe malformattate
                }

                try {
                    Prenotazione prenotazione = new Prenotazione(
                            Integer.parseInt(csvRecord.get("ID")),
                            Integer.parseInt(csvRecord.get("ID Viaggio")),
                            Integer.parseInt(csvRecord.get("ID Utente")));
                    prenotazioni.add(prenotazione);
                } catch (Exception e) {
                    System.err.println("Errore di parsing per la prenotazione: " + csvRecord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prenotazioni;
    }

    // Nuovo metodo per esportare i viaggi disponibili
    public void esportaViaggiDisponibili(List<Viaggio> viaggiDisponibili, String nomeFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create()
                     .setHeader("ID", "Data", "Durata", "Partenza", "Arrivo")
                     .build())) {
    
            for (Viaggio viaggio : viaggiDisponibili) {
                csvPrinter.printRecord(viaggio.getId(), viaggio.getData(), viaggio.getDurata(), viaggio.getPartenza(), viaggio.getArrivo());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            System.out.println("Errore durante l'esportazione: " + e.getMessage());
        }
    }
    


    // Metodo per ottenere il nome del file di esportazione
    public String getNomeFileEsportazione() {
        LocalDate oggi = LocalDate.now();
        return "viaggi_disponibili_" + oggi.toString().replace("-", "_") + ".csv";
    }
}
