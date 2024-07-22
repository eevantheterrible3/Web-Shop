package com.webshop.dtos;

import com.webshop.model.Kupac;
import com.webshop.model.Proizvod;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class PonudaDto {
    private double cena;
    private Proizvod proizvod;
    private Kupac kupacKojiJeDaoPonudu;

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

    public PonudaDto(double cena, Proizvod proizvod, Kupac kupacKojiJeDaoPonudu) {
        this.cena = cena;
        this.proizvod = proizvod;
        this.kupacKojiJeDaoPonudu = kupacKojiJeDaoPonudu;
    }
}
