package com.webshop.dtos;

import com.webshop.model.Uloga;

public class RegistracijaDto {
    private String uloga;
    private String ime;
    private String prezime;
    private String username;
    private String email;
    private String telefon;
    private String lozinka;
    private String lozinkaPonovljena;

    public RegistracijaDto(String uloga, String ime, String prezime, String username, String email, String telefon, String password, String lozinkaPonovljena) {
        this.uloga = uloga;
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.email = email;
        this.telefon = telefon;
        this.lozinka = password;
        this.lozinkaPonovljena = lozinkaPonovljena;
    }

    public RegistracijaDto(RegistracijaDto dto){
        this.uloga = dto.uloga;
        this.ime = dto.ime;
        this.prezime = dto.prezime;
        this.username = dto.username;
        this.email = dto.email;
        this.telefon = dto.telefon;
        this.lozinka = dto.lozinka;
        this.lozinkaPonovljena = dto.lozinkaPonovljena;
    }

    public RegistracijaDto(){}

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String password) {
        this.lozinka = password;
    }

    public String getLozinkaPonovljena() {
        return lozinkaPonovljena;
    }

    public void setPasswordRepeat(String lozinkaPonovljena) {
        this.lozinkaPonovljena = lozinkaPonovljena;
    }
}
