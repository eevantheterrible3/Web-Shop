package com.webshop.service;

import com.webshop.dtos.ReportDto;
import com.webshop.dtos.ViewProdavacDto;
import com.webshop.model.Kupac;
import com.webshop.model.PrijavaProfila;
import com.webshop.model.Prodavac;
import com.webshop.model.StatusPrijave;
import com.webshop.repository.KupacRepository;
import com.webshop.repository.ProdavacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProdavacService {

    @Autowired
    private ProdavacRepository prodavacRepository;

    @Autowired
    private KupacRepository kupacRepository;

    public Prodavac findOne(int id){
        Optional<Prodavac> foundProdavac = prodavacRepository.findById(id);
        if (foundProdavac.isPresent()) {
            return foundProdavac.get();
        }
        return null;
    }

    public PrijavaProfila reportUser(ReportDto dto) {

        Optional<Prodavac> prodavacOptional = prodavacRepository.findById(dto.getProdavac());
        if (!prodavacOptional.isPresent()) {
            return null;
        }
        Prodavac prodavac = prodavacOptional.get();

        Optional<Kupac> kupacOptional = kupacRepository.findById(dto.getKupac());
        if (!kupacOptional.isPresent()) {
            return null;
        }
        Kupac kupac = kupacOptional.get();


        PrijavaProfila prijava = new PrijavaProfila(dto.getRazlogPrijave(), dto.getDatumPrijave(), prodavac, kupac, StatusPrijave.PODNETA);

        return prijava;
    }

    public Optional<Prodavac> findById(int id){
        return prodavacRepository.findById(id);
    }

    public ViewProdavacDto viewProdavac(int id) {
        Prodavac prodavac = findOne(id);
        ViewProdavacDto dto = new ViewProdavacDto(prodavac.getIme(), prodavac.getPrezime(), prodavac.getKorisnickoIme(), prodavac.getUloga().toString(), prodavac.getEmail(), prodavac.getTelefon(), prodavac.getDatumRodjenja(), prodavac.getProfilnaSlika(), prodavac.getOpis(), prodavac.getProizvodiNaProdaju(), prodavac.getProsecnaOcena());

        return dto;
    }
}

