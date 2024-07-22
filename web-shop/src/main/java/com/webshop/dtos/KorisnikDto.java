package com.webshop.dtos;

import com.webshop.model.Uloga;
import com.webshop.repository.KorisnikRepository;
import jakarta.persistence.Column;

import java.time.LocalDate;

public class KorisnikDto {
    private int id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String email;
    private String telefon;
    private LocalDate datumRodjenja;
    private String profilnaSlika;
    private String opis;
    private boolean blokiran;
    private Uloga uloga;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isBlokiran() {
        return blokiran;
    }

    public void setBlokiran(boolean blokiran) {
        this.blokiran = blokiran;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    public KorisnikDto(int id, String ime, String prezime, String korisnickoIme, String email, String telefon, LocalDate datumRodjenja, String profilnaSlika, String opis, boolean blokiran, Uloga uloga) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.email = email;
        this.telefon = telefon;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.blokiran = blokiran;
        this.uloga = uloga;
    }

    public KorisnikDto(KorisnikDto dto){
        this.id = dto.id;
        this.ime = dto.getIme();
        this.prezime = dto.getPrezime();
        this.korisnickoIme = dto.getKorisnickoIme();
        this.email = dto.getEmail();
        this.telefon = dto.getTelefon();
        this.datumRodjenja = dto.getDatumRodjenja();
        this.profilnaSlika = dto.getProfilnaSlika();
        this.opis = dto.getOpis();
        this.blokiran = dto.isBlokiran();
        this.uloga = dto.getUloga();

    }


}
