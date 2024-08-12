package com.example.poolingaround.models;

import java.time.LocalDate;

public class Viaggio {
    private int id;
    private LocalDate data;
    private int durata;
    private String partenza;
    private String arrivo;
    private boolean disponibile;

    public Viaggio(int id, LocalDate data, int durata, String partenza, String arrivo, boolean disponibile) {
        this.id = id;
        this.data = data;
        this.durata = durata;
        this.partenza = partenza;
        this.arrivo = arrivo;
        this.disponibile = disponibile;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getPartenza() {
        return partenza;
    }

    public void setPartenza(String partenza) {
        this.partenza = partenza;
    }

    public String getArrivo() {
        return arrivo;
    }

    public void setArrivo(String arrivo) {
        this.arrivo = arrivo;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    @Override
    public String toString() {
        return "Viaggio{" +
                "id=" + id +
                ", data=" + data +
                ", durata=" + durata +
                ", partenza='" + partenza + '\'' +
                ", arrivo='" + arrivo + '\'' +
                ", disponibile=" + disponibile +
                '}';
    }
}
