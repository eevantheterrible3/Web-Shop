package com.webshop.repository;

import com.webshop.model.Korisnik;
import com.webshop.model.Kupac;
import com.webshop.model.Proizvod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KupacRepository extends JpaRepository<Kupac, Integer> {

    @Query("SELECT k FROM Kupac k JOIN k.kupljeniProizvodi p WHERE p = :proizvod")
    Kupac findByContainKupljeniproizvod(@Param("proizvod") Proizvod proizvod);

    Kupac findByKorisnickoIme(String korisnickoIme);
}
