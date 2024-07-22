package com.webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@DiscriminatorValue(value = "PRODAVAC")
public class Prodavac extends Korisnik {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;*/

    @OneToMany(mappedBy = "prodavac", fetch = FetchType.EAGER,  orphanRemoval = true)
    @JsonIgnore
    private Set<Proizvod> proizvodiNaProdaju;

    /*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Recenzija> dobijeneRecenzije;*/

    @Column
    private Double prosecnaOcena;

    public Prodavac(String ime, String prezime, String korisnickoIme, String email, String telefon, String lozinka) {
        super(ime, prezime, korisnickoIme, email, telefon, lozinka, Uloga.PRODAVAC);
        this.prosecnaOcena = null;
    }

    public Prodavac(String ime, String prezime, String korisnickoIme, String email, String telefon, String lozinka, Uloga uloga, Set<Proizvod> proizvodiNaProdaju) {
        super(ime, prezime, korisnickoIme, email, telefon, lozinka, uloga);
        this.proizvodiNaProdaju = proizvodiNaProdaju;
        this.prosecnaOcena = null;
    }

    public Prodavac() {
        this.prosecnaOcena = null;
    }

    public Set<Proizvod> getProizvodiNaProdaju() {
        return proizvodiNaProdaju;
    }

    public void setProizvodiNaProdaju(Set<Proizvod> proizvodiNaProdaju) {
        this.proizvodiNaProdaju = proizvodiNaProdaju;
    }

    /*public Set<Recenzija> getDobijeneRecenzije() {
        return dobijeneRecenzije;
    }

    public void setDobijeneRecenzije(Set<Recenzija> dobijeneRecenzije) {
        this.dobijeneRecenzije = dobijeneRecenzije;
    }*/

    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(Double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public void removeProizvodiNaProdajuElement(Proizvod proizvodiNaProdajuElement) {
        proizvodiNaProdaju.remove(proizvodiNaProdajuElement);
    }

    /*public int brojDobijenihRecenzija(){
        return dobijeneRecenzije.size();
    }

    public int sumOfDobijenihRecenzija(){
        int sum = 0;
        for(Recenzija r : dobijeneRecenzije){
            sum += r.getOcena();
        }
        return sum;
    }

    public void addRecenzija(Recenzija r){
        dobijeneRecenzije.add(r);
    }*/

    public void addProizvod(Proizvod proizvod){
        proizvodiNaProdaju.add(proizvod);
    }

    public void updateProsekOcena(){
        double sum = 0;
        for(Recenzija recenzija : getDobijeneRecenzije()){
            sum += recenzija.getOcena();
        }
        prosecnaOcena = sum/getDobijeneRecenzije().size();
    }
}
