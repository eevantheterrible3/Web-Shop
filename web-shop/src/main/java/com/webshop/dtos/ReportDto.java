package com.webshop.dtos;

import com.webshop.model.*;

import java.time.LocalDate;

public class ReportDto {

    private String razlogPrijave;
    private int kupacId;
    private int  prodavacId;
    private LocalDate datumPrijave;

    public ReportDto(String razlogPrijave, int kupacId, int  prodavacId, LocalDate datumPrijave) {
        this.razlogPrijave = razlogPrijave;
        this.kupacId = kupacId;
        this.prodavacId = prodavacId;
        this.datumPrijave = datumPrijave;
    }

    public String getRazlogPrijave() {
        return razlogPrijave;
    }

    public void setRazlogPrijave(String razlogPrijave) {
        this.razlogPrijave = razlogPrijave;
    }

    public int getKupac() {
        return kupacId;
    }

    public void setKupac(int kupacId) {
        this.kupacId = kupacId;
    }

    public int getProdavac() {
        return prodavacId;
    }

    public void setProdavac(int  prodavacId) {
        this.prodavacId = prodavacId;
    }

    public LocalDate getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(LocalDate datumPrijave) {
        this.datumPrijave = datumPrijave;
    }
}
