package com.webshop.repository;

import com.webshop.model.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Integer> {
    List<Recenzija> findAll();

}
