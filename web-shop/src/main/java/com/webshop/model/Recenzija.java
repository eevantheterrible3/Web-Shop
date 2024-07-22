package com.webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Recenzija implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int ocena;

    @Column
    private String komentar;

    @Column
    private LocalDateTime datumRecenzije;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Korisnik recenzijaKorisnik;

    public Recenzija() {}

    public Recenzija(int ocena, String komentar, LocalDateTime datumRecenzije, Korisnik recenzijaKorisnik) {
        this.ocena = ocena;
        this.komentar = komentar;
        this.datumRecenzije = datumRecenzije;
        this.recenzijaKorisnik = recenzijaKorisnik;

    }

    @Override
    public String toString() {
        return "Recenzija{" +
                "id=" + id +
                ", ocena=" + ocena +
                ", komentar='" + komentar + '\'' +
                ", datumRecenzije=" + datumRecenzije +
                ", recenzijaKorisnik=" + recenzijaKorisnik +
                '}';
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


    public Korisnik getRecenzijaKorisnik() {
        return recenzijaKorisnik;
    }

    public void setRecenzijaKorisnik(Korisnik recenzijaKorisnik) {
        this.recenzijaKorisnik = recenzijaKorisnik;
    }
}
