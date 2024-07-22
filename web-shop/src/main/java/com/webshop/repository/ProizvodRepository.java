package com.webshop.repository;

import com.webshop.model.Proizvod;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;


public interface ProizvodRepository extends JpaRepository<Proizvod, Integer> {
    Page<Proizvod> findAll(Pageable pageable);
}
