package com.example.poolingaround.models;

public class Prenotazione {
    private int id;
    private int idViaggio;
    private int idUtente;

    public Prenotazione(int id, int idViaggio, int idUtente) {
        this.id = id;
        this.idViaggio = idViaggio;
        this.idUtente = idUtente;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdViaggio() {
        return idViaggio;
    }

    public void setIdViaggio(int idViaggio) {
        this.idViaggio = idViaggio;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + id +
                ", idViaggio=" + idViaggio +
                ", idUtente=" + idUtente +
                '}';
    }
}
