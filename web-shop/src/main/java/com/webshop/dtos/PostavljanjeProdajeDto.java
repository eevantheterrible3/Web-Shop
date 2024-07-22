package com.webshop.dtos;

import com.webshop.model.TipProdaje;

public class PostavljanjeProdajeDto {
    private String nazivProizvoda;
    private String opisProizvoda;
    private String slika;
    private double cena;
    private String imeKategorije;
    private String tipProdaje;

    public PostavljanjeProdajeDto(String nazivProizvoda, String opisProizvoda, String slika, double cena, String imeKategorije, String tipProdaje) {
        this.nazivProizvoda = nazivProizvoda;
        this.opisProizvoda = opisProizvoda;
        this.slika = slika;
        this.cena = cena;
        this.imeKategorije = imeKategorije;
        this.tipProdaje = tipProdaje;
    }

    public String getNazivProizvoda() {
        return nazivProizvoda;
    }

    public void setNazivProizvoda(String nazivProizvoda) {
        this.nazivProizvoda = nazivProizvoda;
    }

    public String getOpisProizvoda() {
        return opisProizvoda;
    }

    public void setOpisProizvoda(String opisProizvoda) {
        this.opisProizvoda = opisProizvoda;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getImeKategorije() {
        return imeKategorije;
    }

    public void setImeKategorije(String imeKategorije) {
        this.imeKategorije = imeKategorije;
    }

    public String getTipProdaje() {
        return tipProdaje;
    }

    public void setTipProdaje(String tipProdaje) {
        this.tipProdaje = tipProdaje;
    }
}
