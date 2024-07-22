package com.webshop.dtos;

import java.time.LocalDate;

public class EditDto {

    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String email;
    private String telefon;
    private LocalDate datumRodjenja;
    private String profilnaSlika;
    private String opis;
    private String lozinka;
    private String ponovljenaLozinka;
    private String novaLozinka;

    public EditDto(String name, String lastname, String username, String email, String contact, LocalDate dateOfBirth, String picture, String bio, String pass, String rPass, String nPass) {
        this.ime = name;
        this.prezime = lastname;
        this.korisnickoIme = username;
        this.email = email;
        this.telefon = contact;
        this.profilnaSlika = picture;
        this.opis = bio;
        this.datumRodjenja = dateOfBirth;
        this.lozinka = pass;
        this.ponovljenaLozinka = rPass;
        this.novaLozinka = nPass;
    }

    public EditDto(EditDto dto){
        this.ime = dto.getIme();
        this.prezime = dto.getPrezime();
        this.korisnickoIme = dto.getKorisnickoIme();
        this.email = dto.getEmail();
        this.telefon = dto.getTelefon();
        this.datumRodjenja = dto.getDatumRodjenja();
        this.profilnaSlika = dto.getProfilnaSlika();
        this.opis = dto.getOpis();
        this.lozinka = dto.getLozinka();
        this.ponovljenaLozinka = dto.getPonovljenaLozinka();
        this.novaLozinka = dto.getNovaLozinka();

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

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getPonovljenaLozinka() {
        return ponovljenaLozinka;
    }

    public void setPonovljenaLozinka(String ponovljenaLozinka) {
        this.ponovljenaLozinka = ponovljenaLozinka;
    }

    public String getNovaLozinka() {
        return novaLozinka;
    }

    public void setNovaLozinka(String novaLozinka) {
        this.novaLozinka = novaLozinka;
    }
}
