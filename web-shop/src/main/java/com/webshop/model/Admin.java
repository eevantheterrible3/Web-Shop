package com.webshop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ADMIN")
public class Admin extends Korisnik{
    public Admin(String ime, String prezime, String korisnickoIme, String email, String telefon, String lozinka) {
        super(ime, prezime, korisnickoIme, email, telefon, lozinka, Uloga.ADMIN);
    }
    public Admin() {
        setUloga(Uloga.ADMIN);
    }
}
