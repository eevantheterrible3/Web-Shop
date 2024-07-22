package com.webshop.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class PrijavaProfila implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String razlogPrijave;

    @Column
    private LocalDate datumPodnosenjaPrijave;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Korisnik korisnikPodneo;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Korisnik korisnikPrijavljen;


    @Enumerated(EnumType.STRING)
    private StatusPrijave statusPrijave;

    public PrijavaProfila(String razlogPrijave, LocalDate datumPodnosenjaPrijave, Korisnik korisnikPodneo, Korisnik korisnikPrijavljen, StatusPrijave statusPrijave) {
        this.razlogPrijave = razlogPrijave;
        this.datumPodnosenjaPrijave = datumPodnosenjaPrijave;
        this.korisnikPodneo = korisnikPodneo;
        this.korisnikPrijavljen = korisnikPrijavljen;
        this.statusPrijave = statusPrijave;
    }

    public PrijavaProfila() {

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

    public Korisnik getKorisnikPodneo() {
        return korisnikPodneo;
    }

    public void setKorisnikPodneo(Korisnik korisnikPodneo) {
        this.korisnikPodneo = korisnikPodneo;
    }

    public Korisnik getKorisnikPrijavljen() {
        return korisnikPrijavljen;
    }

    public void setKorisnikPrijavljen(Korisnik korisnikPrijavljen) {
        this.korisnikPrijavljen = korisnikPrijavljen;
    }

    public StatusPrijave getStatusPrijave() {
        return statusPrijave;
    }

    public void setStatusPrijave(StatusPrijave statusPrijave) {
        this.statusPrijave = statusPrijave;
    }
}
