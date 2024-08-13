package com.example.poolingaround.services;

import com.example.poolingaround.models.Viaggio;
import com.example.poolingaround.models.Prenotazione;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ViaggioService {
    private List<Viaggio> viaggi;
    private List<Prenotazione> prenotazioni;

    public ViaggioService(List<Viaggio> viaggi, List<Prenotazione> prenotazioni) {
        this.viaggi = viaggi;
        this.prenotazioni = prenotazioni;
    }

    public List<Viaggio> getViaggiDisponibili() {
        return viaggi.stream()
                .filter(Viaggio::isDisponibile)
                .collect(Collectors.toList());
    }

    public void visualizzaViaggi() {
        for (Viaggio viaggio : viaggi) {
            System.out.printf("%d | %s | %s | %s | %s | %s%n",
                    viaggio.getId(), viaggio.getData(), viaggio.getDurata(),
                    viaggio.getPartenza(), viaggio.getArrivo(),
                    viaggio.isDisponibile() ? "SI" : "NO");
        }
    }

    public void prenotaViaggio() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Inserisci ID Viaggio:");
            int idViaggio = Integer.parseInt(reader.readLine());
            Viaggio viaggio = trovaViaggioPerId(idViaggio);
            if (viaggio != null && viaggio.isDisponibile()) {
                System.out.println("Inserisci ID Utente:");
                int idUtente = Integer.parseInt(reader.readLine());
                // Aggiungere la prenotazione
                prenotazioni.add(new Prenotazione(prenotazioni.size() + 1, idViaggio, idUtente));
                viaggio.setDisponibile(false);
                System.out.println("Prenotazione effettuata.");
            } else {
                System.out.println("Viaggio non disponibile.");
            }
        } catch (IOException e) {
            System.out.println("Errore di input: " + e.getMessage());
        }
    }

    public void cancellaPrenotazione() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Inserisci ID Prenotazione:");
            int idPrenotazione = Integer.parseInt(reader.readLine());
            Optional<Prenotazione> prenotazione = prenotazioni.stream()
                    .filter(p -> p.getId() == idPrenotazione)
                    .findFirst();
            if (prenotazione.isPresent()) {
                Viaggio viaggio = trovaViaggioPerId(prenotazione.get().getIdViaggio());
                if (viaggio != null) {
                    viaggio.setDisponibile(true);
                }
                prenotazioni.remove(prenotazione.get());
                System.out.println("Prenotazione cancellata.");
            } else {
                System.out.println("Prenotazione non trovata.");
            }
        } catch (IOException e) {
            System.out.println("Errore di input: " + e.getMessage());
        }
    }

    public Viaggio trovaViaggioPerId(int id) {
        return viaggi.stream()
                .filter(viaggio -> viaggio.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
