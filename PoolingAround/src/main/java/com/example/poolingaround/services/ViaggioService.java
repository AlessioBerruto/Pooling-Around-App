package com.example.poolingaround.services;

import com.example.poolingaround.models.Viaggio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViaggioService {

    private List<Viaggio> viaggi;
    private CsvService csvService;  
    private static final DateTimeFormatter VISUAL_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    
    public ViaggioService(List<Viaggio> viaggi, CsvService csvService) {
        this.viaggi = viaggi;
        this.csvService = csvService;
    }

    public void visualizzaViaggi() {
        System.out.printf("%-5s | %-10s | %-7s | %-15s | %-15s | %-10s\n", "ID", "Data", "Durata", "Partenza", "Arrivo",
                "Disponibile");
        for (Viaggio viaggio : viaggi) {
            String formattedDate = viaggio.getData().format(VISUAL_FORMATTER);
            System.out.printf("%-5d | %-10s | %-7d | %-15s | %-15s | %-10s\n", viaggio.getId(), formattedDate,
                    viaggio.getDurata(), viaggio.getPartenza(), viaggio.getArrivo(),
                    viaggio.isDisponibile() ? "SI" : "NO");
        }
    }

    public List<Viaggio> getViaggiDisponibili() {
        return viaggi.stream().filter(Viaggio::isDisponibile).toList();
    }

    public List<Viaggio> getViaggi() {
        return viaggi;
    }

    public String getNomeFileEsportazione() {
        LocalDate oggi = LocalDate.now();
        return "viaggi_disponibili_" + oggi.toString().replace("-", "_") + ".csv";
    }

    public void esportaViaggiDisponibili(String nomeFile) {
        List<Viaggio> viaggiDisponibili = getViaggiDisponibili(); 
        csvService.esportaViaggiDisponibili(viaggiDisponibili, nomeFile);
    }
}
