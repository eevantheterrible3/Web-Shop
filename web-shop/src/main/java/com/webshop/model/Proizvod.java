package com.webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Proizvod implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String naziv;

    @Column
    private String opis;


    @Column
    private String slika;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Kategorija kategorija;

    @Column
    private double cena;

    @Enumerated(EnumType.STRING)
    private TipProdaje tipProdaje;

    @Column
    private LocalDateTime datumObjavljivanja;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Ponuda> ponude;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Prodavac prodavac;

    @Column
    private boolean ostavljenaRecenzijaOdKupca;

    @Column
    private boolean ostavljenaRecenzijaOdProdavca;

    @Column
    private boolean prodat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public TipProdaje getTipProdaje() {
        return tipProdaje;
    }

    public void setTipProdaje(TipProdaje tipProdaje) {
        this.tipProdaje = tipProdaje;
    }

    public LocalDateTime getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(LocalDateTime datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }

    public Set<Ponuda> getPonude() {
        return ponude;
    }

    public void setPonude(Set<Ponuda> ponude) {
        this.ponude = ponude;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        this.prodavac = prodavac;
    }

    public boolean isOstavljenaRecenzijaOdKupca() {
        return ostavljenaRecenzijaOdKupca;
    }

    public void setOstavljenaRecenzijaOdKupca(boolean ostavljenaRecenzijaOdKupca) {
        this.ostavljenaRecenzijaOdKupca = ostavljenaRecenzijaOdKupca;
    }

    public boolean isOstavljenaRecenzijaOdProdavca() {
        return ostavljenaRecenzijaOdProdavca;
    }

    public void setOstavljenaRecenzijaOdProdavca(boolean ostavljenaRecenzijaOdProdavca) {
        this.ostavljenaRecenzijaOdProdavca = ostavljenaRecenzijaOdProdavca;
    }

    public boolean isProdat() {
        return prodat;
    }

    public void setProdat(boolean prodat) {
        this.prodat = prodat;
    }

    public void addPonuda(Ponuda ponuda){
        ponude.add(ponuda);
    }

    public boolean ponudeIsEmpty(){
        return ponude.isEmpty();
    }
}
