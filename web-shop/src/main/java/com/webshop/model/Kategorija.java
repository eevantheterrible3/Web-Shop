package com.webshop.model;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
public class Kategorija implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String naziv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Kategorija(String naziv) {
        this.naziv = naziv;
    }

    public Kategorija() {}

    @Override
    public String toString() {
        return "Kategorija{" +
                "naziv='" + naziv + '\'' +
                '}';
    }
}
