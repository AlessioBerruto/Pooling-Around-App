package com.example.poolingaround.services;

import com.example.poolingaround.models.Utente;
import com.example.poolingaround.models.Viaggio;
import com.example.poolingaround.models.Prenotazione;

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
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .build())) {
            

            for (CSVRecord csvRecord : csvParser) {               

                if (csvRecord.size() < 6) {
                    System.err.println("Riga vuota o malformattata: " + csvRecord);
                    System.out.println();
                    continue;
                }                

                LocalDate dataDiNascita;
                try {
                    dataDiNascita = LocalDate.parse(csvRecord.get("Data di nascita"), FORMATTER);
                } catch (Exception e) {
                    System.err.println("Data di nascita non valida per il record: " + csvRecord);
                    System.out.println();
                    continue;
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
                    System.out.println();
                    continue;
                }

                LocalDate data;
                String dataStr = csvRecord.get("Data");
                if (dataStr == null || dataStr.trim().isEmpty()) {
                    System.err.println("Data non valida per il record: " + csvRecord);
                    System.out.println();
                    continue;
                }

                try {
                    data = LocalDate.parse(dataStr, FORMATTER);
                } catch (Exception e) {
                    System.err.println("Errore di parsing per la data: " + dataStr + " nel record: " + csvRecord);
                    System.out.println();
                    continue;
                }

                Viaggio viaggio = new Viaggio(
                        Integer.parseInt(csvRecord.get("ID")),
                        data,
                        Integer.parseInt(csvRecord.get("Durata")),
                        csvRecord.get("Partenza"),
                        csvRecord.get("Arrivo"),
                        csvRecord.get("Disponibile").equalsIgnoreCase("SI"));
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
                        .setHeader("ID", "IDViaggio", "IDUtente")
                        .setSkipHeaderRecord(true)
                        .build())) {

            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.size() < 3) {
                    System.err.println("Riga vuota o malformattata: " + csvRecord);
                    System.out.println();
                    continue;
                }

                Prenotazione prenotazione = new Prenotazione(
                        Integer.parseInt(csvRecord.get("ID")),
                        Integer.parseInt(csvRecord.get("IDViaggio")),
                        Integer.parseInt(csvRecord.get("IDUtente")));
                prenotazioni.add(prenotazione);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prenotazioni;
    }

    public void salvaViaggi(String filePath, List<Viaggio> viaggi) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create()
                        .setDelimiter(';')
                        .setHeader("ID", "Data", "Durata", "Partenza", "Arrivo", "Disponibile")
                        .build())) {

            for (Viaggio viaggio : viaggi) {
                csvPrinter.printRecord(
                        viaggio.getId(),
                        viaggio.getData().format(FORMATTER),
                        viaggio.getDurata(),
                        viaggio.getPartenza(),
                        viaggio.getArrivo(),
                        viaggio.isDisponibile() ? "SI" : "NO");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvaPrenotazioni(String filePath, List<Prenotazione> prenotazioni) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create()
                        .setDelimiter(';')
                        .setHeader("ID", "IDViaggio", "IDUtente")
                        .build())) {

            for (Prenotazione prenotazione : prenotazioni) {
                csvPrinter.printRecord(
                        prenotazione.getId(),
                        prenotazione.getIdViaggio(),
                        prenotazione.getIdUtente());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvaUtenti(String filePath, List<Utente> utenti) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create()
                        .setDelimiter(';')
                        .setHeader("ID", "Nome", "Cognome", "Data di nascita", "Indirizzo", "Documento ID")
                        .build())) {

            for (Utente utente : utenti) {
                csvPrinter.printRecord(utente.getId(), utente.getNome(), utente.getCognome(),
                        utente.getDataNascita().format(FORMATTER),
                        utente.getIndirizzo(), utente.getDocumentoId());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void esportaViaggiDisponibili(List<Viaggio> viaggiDisponibili, String nomeFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create()
                        .setHeader("ID", "Data", "Durata", "Partenza", "Arrivo")
                        .build())) {

            for (Viaggio viaggio : viaggiDisponibili) {
                csvPrinter.printRecord(
                        viaggio.getId(),
                        viaggio.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 
                        viaggio.getDurata(),
                        viaggio.getPartenza(),
                        viaggio.getArrivo());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            System.out.println("Errore durante l'esportazione: " + e.getMessage());
            System.out.println();
        }
    }

}
