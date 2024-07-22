package com.webshop.dtos;

import com.webshop.model.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

public class ProizvodDto {
    private int id;
    private String naziv;
    private String opis;
    private String slika;
    private Kategorija kategorija;
    private double cena;
    private TipProdaje tipProdaje;


    public ProizvodDto(int id, String naziv, String opis, String slika, Kategorija kategorija, double cena, TipProdaje tipProdaje) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.slika = slika;
        this.kategorija = kategorija;
        this.cena = cena;
        this.tipProdaje = tipProdaje;
    }

    public ProizvodDto(Proizvod proizvod) {
        this.id = proizvod.getId();
        this.naziv = proizvod.getNaziv();
        this.opis = proizvod.getOpis();
        this.slika = proizvod.getSlika();
        this.kategorija = proizvod.getKategorija();
        this.cena = proizvod.getCena();
        this.tipProdaje = proizvod.getTipProdaje();
    }

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
}
