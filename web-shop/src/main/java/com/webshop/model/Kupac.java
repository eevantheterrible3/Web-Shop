package com.webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webshop.repository.ProizvodRepository;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@DiscriminatorValue(value = "KUPAC")
public class Kupac extends Korisnik{

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;*/

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Proizvod> kupljeniProizvodi;

    /*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Recenzija> dobijeneRecenzije;*/


    private Double prosecnaOcena;

    /*
    public Kupac(String ime, String prezime, String korisnickoIme, String email, String telefon, String lozinka, Uloga uloga, Set<Proizvod> kupljeniProizvodi, Double prosecnaOcena) {
        super(ime, prezime, korisnickoIme, email, telefon, lozinka, uloga);
        this.kupljeniProizvodi = kupljeniProizvodi;
        this.prosecnaOcena = prosecnaOcena;
    }

    public Kupac(Set<Proizvod> kupljeniProizvodi, Double prosecnaOcena) {
        this.kupljeniProizvodi = kupljeniProizvodi;
        this.prosecnaOcena = prosecnaOcena;
    }

     */

    public Kupac(String ime, String prezime, String korisnickoIme, String email, String telefon, String lozinka, Uloga uloga, Set<Proizvod> kupljeniProizvodi) {
        super(ime, prezime, korisnickoIme, email, telefon, lozinka, uloga);
        this.kupljeniProizvodi = kupljeniProizvodi;
        this.prosecnaOcena = prosecnaOcena;
    }

    public Kupac(String ime, String prezime, String korisnickoIme, String email, String telefon, String lozinka) {
        super(ime, prezime, korisnickoIme, email, telefon, lozinka, Uloga.KUPAC);
        this.prosecnaOcena = null;
    }
    public Kupac() {
        prosecnaOcena = null;
    }



    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(Double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public Set<Proizvod> getKupljeniProizvodi() {
        return kupljeniProizvodi;
    }

    public void setKupljeniProizvodi(Set<Proizvod> kupljeniProizvodi) {
        this.kupljeniProizvodi = kupljeniProizvodi;
    }

    public void addKupljeniProizvodi(Proizvod proizvod) {
        kupljeniProizvodi.add(proizvod);
    }

    public boolean containsKupljeniProizvodiWithProdavacId(int prodavacId) {
        for(Proizvod proizvod : kupljeniProizvodi) {
            if(proizvod.getProdavac().getId() == prodavacId) {
                return true;
            }
        }
        return false;
    }

    public boolean containsKupljeniProizvod(Proizvod proizvod){
        return kupljeniProizvodi.contains(proizvod);
    }

    /*public void addRecenzija(Recenzija recenzija){
        dobijeneRecenzije.add(recenzija);

    }*/

    public void updateProsekOcena(){
        double sum = 0;
        for(Recenzija recenzija : getDobijeneRecenzije()){
            sum += recenzija.getOcena();
        }
        prosecnaOcena = sum/getDobijeneRecenzije().size();
    }

}
