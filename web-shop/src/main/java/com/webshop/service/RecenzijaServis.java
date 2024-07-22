package com.webshop.service;

import com.webshop.dtos.RecenzijaDto;
import com.webshop.model.*;
import com.webshop.repository.KorisnikRepository;
import com.webshop.repository.RecenzijaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecenzijaServis {
    @Autowired
    RecenzijaRepository recenzijaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private KupacService kupacService;
    @Autowired
    private ProdavacService prodavacService;

    public Recenzija save(Recenzija recenzija) {
        return recenzijaRepository.save(recenzija);
    }

    public List<RecenzijaDto> getAll() {
        List<Recenzija> recenzije = recenzijaRepository.findAll();
        List<RecenzijaDto> recenzijaDtos = new ArrayList<>();

        for (Recenzija recenzija : recenzije) {
            RecenzijaDto recenzijaDto = new RecenzijaDto(recenzija.getId(), recenzija.getOcena(), recenzija.getKomentar(), recenzija.getDatumRecenzije(), recenzija.getRecenzijaKorisnik().getId());
            recenzijaDtos.add(recenzijaDto);
        }

        return recenzijaDtos;
    }

    public RecenzijaDto get(int id) {
        Optional<Recenzija> recenzijaOptional = recenzijaRepository.findById(id);
        if (!recenzijaOptional.isPresent()) {
            return null;
        }
        Recenzija recenzija = recenzijaOptional.get();
        RecenzijaDto recenzijaDto = new RecenzijaDto(recenzija.getId(), recenzija.getOcena(), recenzija.getKomentar(), recenzija.getDatumRecenzije(), recenzija.getRecenzijaKorisnik().getId());
        return recenzijaDto;
    }



   /* @Transactional
    public Recenzija delete(int recenzijaId) {
        Optional<Recenzija> recenzijaOptional = recenzijaRepository.findById(recenzijaId);
        if(!recenzijaOptional.isPresent()){
            return null;
        }

        Recenzija recenzija = recenzijaOptional.get();


        Korisnik parentKorisnik = findKorisnikHasRecenzija(recenzijaId);
        if (parentKorisnik != null) {
            parentKorisnik.getDobijeneRecenzije().remove(recenzija);
            korisnikRepository.save(parentKorisnik); // Persist the changes
            return recenzija;
        }
        return recenzija;

    }*/

    @Transactional
    public Recenzija delete(int recenzijaId) {
        Optional<Recenzija> recenzijaOptional = recenzijaRepository.findById(recenzijaId);
        if (!recenzijaOptional.isPresent()) {
            return null;
        }

        Recenzija recenzija = recenzijaOptional.get();


        Korisnik parentKorisnik = findKorisnikHasRecenzija(recenzijaId);
        if (parentKorisnik != null) {
            if (parentKorisnik.getUloga() == Uloga.KUPAC) {
                Kupac kupac = kupacService.findOne(parentKorisnik.getId());
                kupac.getDobijeneRecenzije().remove(recenzija);
                recenzijaRepository.save(recenzija);
                korisnikRepository.save(kupac);
                kupac.updateProsekOcena();
                korisnikRepository.save(kupac);
                return recenzija;
            } else {
                Prodavac prodavac = prodavacService.findOne(parentKorisnik.getId());
                prodavac.getDobijeneRecenzije().remove(recenzija);
                recenzijaRepository.save(recenzija);
                korisnikRepository.save(prodavac);
                prodavac.updateProsekOcena();
                korisnikRepository.save(prodavac);
                return recenzija;
            }

        }
        return null;
    }



    public Recenzija update(RecenzijaDto recenzijaDto) {
        Optional<Recenzija> recenzijaOptional = recenzijaRepository.findById(recenzijaDto.getId());
        if(!recenzijaOptional.isPresent()){
            return null;
        }

        Korisnik parentKorisnik = findKorisnikHasRecenzija(recenzijaDto.getId());
        if (parentKorisnik != null) {
            if(parentKorisnik.getUloga() == Uloga.KUPAC){
                Kupac kupac = kupacService.findOne(parentKorisnik.getId());
                Recenzija recenzija = recenzijaOptional.get();
                recenzija.setKomentar(recenzijaDto.getKomentar());
                recenzija.setOcena(recenzijaDto.getOcena());
                recenzijaRepository.save(recenzija);
                korisnikRepository.save(kupac);
                kupac.updateProsekOcena();
                korisnikRepository.save(kupac);
                return recenzija;
            } else {
                Prodavac prodavac = prodavacService.findOne(parentKorisnik.getId());
                Recenzija recenzija = recenzijaOptional.get();
                recenzija.setKomentar(recenzijaDto.getKomentar());
                recenzija.setOcena(recenzijaDto.getOcena());
                recenzijaRepository.save(recenzija);
                korisnikRepository.save(prodavac);
                prodavac.updateProsekOcena();
                korisnikRepository.save(prodavac);
                return recenzija;
            }

        }

        return null;
    }

    public Korisnik findKorisnikHasRecenzija(int recenzijaId){
        return korisnikRepository.findByRecenzijaId(recenzijaId);
    }

}
