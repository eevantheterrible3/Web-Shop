package com.webshop.service;

import com.webshop.dtos.PrijavaDto;
import com.webshop.dtos.RecenzijaDto;
import com.webshop.dtos.ReportDto;
import com.webshop.model.*;
import com.webshop.repository.KupacRepository;
import com.webshop.repository.PrijavaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PrijavaService {

    @Autowired
    private PrijavaRepository prijavaRepository;

    public List<PrijavaDto> getAll() {
        List<PrijavaProfila> prijave = prijavaRepository.findAll();
        List<PrijavaDto> prijaveDto = new ArrayList<>();

        for (PrijavaProfila prijavaProfila : prijave) {
            PrijavaDto prijavaDto = new PrijavaDto(prijavaProfila.getId(), prijavaProfila.getRazlogPrijave(), prijavaProfila.getDatumPodnosenjaPrijave(), prijavaProfila.getKorisnikPodneo().getId(), prijavaProfila.getKorisnikPrijavljen().getId(), prijavaProfila.getStatusPrijave());
            prijaveDto.add(prijavaDto);
        }

        return prijaveDto;
    }

    public PrijavaProfila findOne(int id){
        Optional<PrijavaProfila> foundPrijava = prijavaRepository.findById(id);
        if (foundPrijava.isPresent()) {
            return foundPrijava.get();
        }
        return null;
    }


    public PrijavaProfila save(PrijavaProfila prijava) { return prijavaRepository.save(prijava); }
}
