package com.webshop.service;

import com.webshop.model.Kategorija;
import com.webshop.model.Korisnik;
import com.webshop.repository.KategorijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KategorijaService {
    @Autowired
    private KategorijaRepository kategorijaRepository;

    public Kategorija getKategorija(String naziv){
        Kategorija kategorija = kategorijaRepository.findByNazivIgnoreCase(naziv);
        return kategorija;
    }

    public List<Kategorija> getKategorijaList() {
        return kategorijaRepository.findAll();
    }

    public Kategorija save(Kategorija kategorija) { return kategorijaRepository.save(kategorija); }

}
