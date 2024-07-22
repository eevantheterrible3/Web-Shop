package com.webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Ponuda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private double cena;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proizvod_id", nullable = false)
    @JsonIgnore
    private Proizvod proizvod;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Kupac kupacKojiJeDaoPonudu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }

    public Kupac getKupacKojiJeDaoPonudu() {
        return kupacKojiJeDaoPonudu;
    }

    public void setKupacKojiJeDaoPonudu(Kupac kupacKojiJeDaoPonudu) {
        this.kupacKojiJeDaoPonudu = kupacKojiJeDaoPonudu;
    }

    public Ponuda(double cena, Proizvod proizvod, Kupac kupacKojiJeDaoPonudu) {

        this.cena = cena;
        this.proizvod = proizvod;
        this.kupacKojiJeDaoPonudu = kupacKojiJeDaoPonudu;
    }

    public Ponuda(){}
}
