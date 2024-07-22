package com.webshop.service;

import com.webshop.model.Korisnik;
import com.webshop.model.Ponuda;
import com.webshop.model.Proizvod;
import com.webshop.repository.PonudaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PonudaService {
    @Autowired
    private PonudaRepository ponudaRepository;

    public Ponuda save(Ponuda ponuda) { return ponudaRepository.save(ponuda); }

    public Ponuda findTopByProizvodOrderByCenaDesc(Proizvod proizvod){
        return ponudaRepository.findTopByProizvodOrderByCenaDesc(proizvod);
    }
}
