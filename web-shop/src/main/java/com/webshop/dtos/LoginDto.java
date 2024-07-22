package com.webshop.dtos;

public class LoginDto {
    private String korisnickoIme;
    private String lozinka;

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public LoginDto(String korisnickoIme, String lozinka) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public LoginDto(){}

    public LoginDto(LoginDto dto){
        this.korisnickoIme = dto.getKorisnickoIme();
        this.lozinka = dto.getLozinka();
    }
}
