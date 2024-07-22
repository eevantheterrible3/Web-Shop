package com.webshop.repository;

import com.webshop.model.Ponuda;
import com.webshop.model.Proizvod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PonudaRepository extends JpaRepository<Ponuda, Integer> {

    @Query("SELECT p FROM Ponuda p WHERE p.proizvod = :proizvod")
    List<Ponuda> findAllByProizvod(@Param("proizvod") Proizvod proizvod);

    //@Query(value = "SELECT * FROM Ponuda p WHERE p.proizvod_id = :proizvod ORDER BY p.cena DESC LIMIT 1", nativeQuery = true)
    @Query("SELECT p FROM Ponuda p WHERE p.proizvod = :proizvod ORDER BY p.cena DESC LIMIT 1")
    Ponuda findTopByProizvodOrderByCenaDesc(@Param("proizvod") Proizvod proizvod);
}


