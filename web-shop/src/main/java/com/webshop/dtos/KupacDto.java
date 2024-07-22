package com.webshop.dtos;

import com.webshop.model.Proizvod;
import com.webshop.model.Recenzija;

import java.time.LocalDate;
import java.util.Set;

public class KupacDto {

    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String email;
    private String telefon;
    private LocalDate datumRodjenja;
    private String profilnaSlika;
    private String opis;
    private Set<Proizvod> kupljeniProizvodi;
    private Set<Recenzija> dobijeneRecenzije;
    private Double prosecnaOcena;

    public KupacDto(String ime, String prezime, String korisnickoIme, String email, String telefon, LocalDate datumRodjenja, String profilnaSlika, String opis, Set<Proizvod> kupljeniProizvodi, Set<Recenzija> dobijeneRecenzije, Double prosecnaOcena) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.email = email;
        this.telefon = telefon;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.kupljeniProizvodi = kupljeniProizvodi;
        this.dobijeneRecenzije = dobijeneRecenzije;
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

    public Set<Proizvod> getKupljeniProizvodi() {
        return kupljeniProizvodi;
    }

    public void setKupljeniProizvodi(Set<Proizvod> kupljeniProizvodi) {
        this.kupljeniProizvodi = kupljeniProizvodi;
    }

    public Set<Recenzija> getDobijeneRecenzije() {
        return dobijeneRecenzije;
    }

    public void setDobijeneRecenzije(Set<Recenzija> dobijeneRecenzije) {
        this.dobijeneRecenzije = dobijeneRecenzije;
    }

    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(Double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }
}
