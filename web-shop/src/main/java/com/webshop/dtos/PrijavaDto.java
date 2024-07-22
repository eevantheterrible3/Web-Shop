package com.webshop.dtos;

import com.webshop.model.Korisnik;
import com.webshop.model.StatusPrijave;
import jakarta.persistence.*;

import java.time.LocalDate;

public class PrijavaDto {

    private int id;
    private String razlogPrijave;
    private LocalDate datumPodnosenjaPrijave;
    private int korisnikPodneoId;
    private int korisnikPrijavljenId;
    private StatusPrijave statusPrijave;

    public PrijavaDto(int id, String razlogPrijave, LocalDate datumPodnosenjaPrijave, int korisnikPodneoId, int korisnikPrijavljenId, StatusPrijave statusPrijave) {
        this.id = id;
        this.razlogPrijave = razlogPrijave;
        this.datumPodnosenjaPrijave = datumPodnosenjaPrijave;
        this.korisnikPodneoId = korisnikPodneoId;
        this.korisnikPrijavljenId = korisnikPrijavljenId;
        this.statusPrijave = statusPrijave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazlogPrijave() {
        return razlogPrijave;
    }

    public void setRazlogPrijave(String razlogPrijave) {
        this.razlogPrijave = razlogPrijave;
    }

    public LocalDate getDatumPodnosenjaPrijave() {
        return datumPodnosenjaPrijave;
    }

    public void setDatumPodnosenjaPrijave(LocalDate datumPodnosenjaPrijave) {
        this.datumPodnosenjaPrijave = datumPodnosenjaPrijave;
    }

    public int getKorisnikPodneoId() {
        return korisnikPodneoId;
    }

    public void setKorisnikPodneoId(int korisnikPodneoId) {
        this.korisnikPodneoId = korisnikPodneoId;
    }

    public int getKorisnikPrijavljenId() {
        return korisnikPrijavljenId;
    }

    public void setKorisnikPrijavljenId(int korisnikPrijavljenId) {
        this.korisnikPrijavljenId = korisnikPrijavljenId;
    }

    public StatusPrijave getStatusPrijave() {
        return statusPrijave;
    }

    public void setStatusPrijave(StatusPrijave statusPrijave) {
        this.statusPrijave = statusPrijave;
    }
}
