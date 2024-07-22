package com.webshop.repository;

import com.webshop.model.Korisnik;
import com.webshop.model.Kupac;
import com.webshop.model.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {

    @Query("SELECT k FROM Korisnik k JOIN k.dobijeneRecenzije r WHERE r.id = :recenzijaId")
    Korisnik findByRecenzijaId(@Param("recenzijaId") int recenzijaId);
    Korisnik findByKorisnickoIme(String korisnickoIme);
    List<Korisnik> findAll();

    Korisnik getByKorisnickoIme(String korisnickoIme);
    Korisnik getById(int id);
    Boolean existsByEmail(String email);
    Boolean existsByLozinka(String lozinka);
    Boolean existsByKorisnickoIme(String username);



}
