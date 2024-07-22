package com.webshop.service;

import com.webshop.dtos.ReportDto;
import com.webshop.dtos.ViewKupacDto;
import com.webshop.model.*;
import com.webshop.repository.KupacRepository;
import com.webshop.repository.ProdavacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class KupacService {

    @Autowired
    private KupacRepository kupacRepository;

    @Autowired
    private ProdavacRepository prodavacRepository;

    public Kupac findOneByKorisnickoIme(String korisnickoIme){
        Kupac foundKupac = kupacRepository.findByKorisnickoIme(korisnickoIme);

        return foundKupac;
    }
    public Kupac findOne(int id){
        Optional<Kupac> foundKupac = kupacRepository.findById(id);
        if (foundKupac.isPresent()) {
            return foundKupac.get();
        }
        return null;
    }

    public ViewKupacDto viewKupac(int id) {
        Kupac kupac = findOne(id);
        ViewKupacDto dto = new ViewKupacDto(kupac.getIme(), kupac.getPrezime(), kupac.getKorisnickoIme(), kupac.getUloga().toString(), kupac.getEmail(), kupac.getTelefon(), kupac.getDatumRodjenja(), kupac.getProfilnaSlika(), kupac.getOpis(), kupac.getKupljeniProizvodi(), kupac.getProsecnaOcena());

        return dto;
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


        PrijavaProfila prijava = new PrijavaProfila(dto.getRazlogPrijave(), dto.getDatumPrijave(), kupac, prodavac, StatusPrijave.PODNETA);

        return prijava;
    }

    public Optional<Kupac> findById(int id){
        return kupacRepository.findById(id);
    }


}
