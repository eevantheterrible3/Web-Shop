package com.webshop.dtos;

import com.webshop.model.Proizvod;
import com.webshop.model.Uloga;

import java.time.LocalDate;
import java.util.Set;

public class ViewProdavacDto {

    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String uloga;
    private String email;
    private String telefon;
    private LocalDate datumRodjenja;
    private String profilnaSlika;
    private String opis;
    private Set<Proizvod> proizvodi;
    private Double prosecnaOcena;

    public ViewProdavacDto(String ime, String prezime, String korisnickoIme, String uloga, String email, String telefon, LocalDate datumRodjenja, String profilnaSlika, String opis, Set<Proizvod> proizvodi, Double prosecnaOcena) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.uloga = uloga;
        this.email = email;
        this.telefon = telefon;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.proizvodi = proizvodi;
        this.prosecnaOcena = prosecnaOcena;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getProfilnaSlika() {
        return profilnaSlika;
    }

    public void setProfilnaSlika(String profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Set<Proizvod> getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(Set<Proizvod> proizvodi) {
        this.proizvodi = proizvodi;
    }

    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(Double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }
}
