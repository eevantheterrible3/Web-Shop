package com.webshop.dtos;

import com.webshop.model.Korisnik;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class RecenzijaDto {
    private int id;
    private int ocena;
    private String komentar;
    private LocalDateTime datumRecenzije;
    private int korisnikId;

    public RecenzijaDto(int id, int ocena, String komentar, LocalDateTime datumRecenzije, int korisnikId) {
        this.id = id;
        this.ocena = ocena;
        this.komentar = komentar;
        this.datumRecenzije = datumRecenzije;
        this.korisnikId = korisnikId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public LocalDateTime getDatumRecenzije() {
        return datumRecenzije;
    }

    public void setDatumRecenzije(LocalDateTime datumRecenzije) {
        this.datumRecenzije = datumRecenzije;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }
}
