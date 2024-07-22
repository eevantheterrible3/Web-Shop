package com.webshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
public class Korisnik implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String ime;

    @Column
    private String prezime;

    @Column(unique = true, nullable = false)
    private String korisnickoIme;

    @Column(unique = true)
    private String email;

    @Column
    private String telefon;

    @Column
    private String lozinka;

    @Column
    private LocalDate datumRodjenja;

    @Column
    private String profilnaSlika;

    @Column
    private String opis;

    @Column
    private boolean blokiran;

    //@Enumerated(EnumType.STRING)
    @Enumerated(EnumType.STRING)
    private Uloga uloga;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Recenzija> dobijeneRecenzije;

    public Set<Recenzija> getDobijeneRecenzije() {
        return dobijeneRecenzije;
    }

    public void setDobijeneRecenzije(Set<Recenzija> dobijeneRecenzije) {
        this.dobijeneRecenzije = dobijeneRecenzije;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", datumRodjenja=" + datumRodjenja +
                ", profilnaSlika='" + profilnaSlika + '\'' +
                ", opis='" + opis + '\'' +
                ", blokiran=" + blokiran +
                '}';
    }

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

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
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



    public Korisnik(String ime, String prezime, String korisnickoIme, String email, String telefon, String lozinka, Uloga uloga) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.email = email;
        this.telefon = telefon;
        this.lozinka = lozinka;
        this.uloga = uloga;
    }

    public Korisnik() {
    }

    public int brojDobijenihRecenzija(){
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
    }

    public Recenzija getRecenzijaFromDobijeneRecenzije(Recenzija recenzija){
        for(Recenzija r : dobijeneRecenzije){
            if(r.getId() == recenzija.getId()){
                return r;
            }
        }
        return null;
    }
}
