package com.webshop.service;

import com.webshop.dtos.RegistracijaDto;
import com.webshop.dtos.EditDto;
import com.webshop.model.*;
import com.webshop.repository.KorisnikRepository;
import com.webshop.repository.KupacRepository;
import com.webshop.repository.ProizvodRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService {
    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private ProizvodRepository proizvodRepository;
    @Autowired
    private KupacRepository kupacRepository;

    public Korisnik findOne(int id){
        Optional<Korisnik> foundKorisnik = korisnikRepository.findById(id);
        if (foundKorisnik.isPresent()) {
            return foundKorisnik.get();
        }
        return null;
    }

    public Korisnik findOneByUsername(String username){
        Korisnik foundKorisnik = korisnikRepository.findByKorisnickoIme(username);
        return foundKorisnik;

    }

    public List<Korisnik> findAll(){
        return korisnikRepository.findAll();
    }



    public Boolean existsEmail(String email) { return korisnikRepository.existsByEmail(email); }
    public Boolean existsLozinka(String lozinka) { return korisnikRepository.existsByLozinka(lozinka); }
    public Boolean existsKorisnickoIme(String username) { return korisnikRepository.existsByKorisnickoIme(username); }

    public Korisnik create(RegistracijaDto dto) {

        if(dto.getUloga().equalsIgnoreCase("kupac")){
            Kupac kupac = new Kupac(dto.getIme(), dto.getPrezime(), dto.getUsername(), dto.getEmail(), dto.getTelefon(), dto.getLozinka());
            kupac = save(kupac);
            return kupac;

        } else{
            Prodavac prodavac = new Prodavac(dto.getIme(), dto.getPrezime(), dto.getUsername(), dto.getEmail(), dto.getTelefon(), dto.getLozinka());
            prodavac = save(prodavac);
            return prodavac;
        }

    }

    public Korisnik login(String korisnickoIme, String password) {
        Korisnik korisnik = korisnikRepository.getByKorisnickoIme(korisnickoIme);
        if (korisnik == null || !korisnik.getLozinka().equals(password)) {
            return null;
        }


        return korisnik;
    }

    public Korisnik updateUser(int id, EditDto updateDto){
        Korisnik korisnik = findOne(id);

        if(updateDto.getIme().isEmpty()  || updateDto.getPrezime().isEmpty() || updateDto.getTelefon().isEmpty())
            throw new IllegalArgumentException("Prazno polje");

        // polja koja ne smeju biti prazna
        korisnik.setIme(updateDto.getIme());
        korisnik.setPrezime(updateDto.getPrezime());
        korisnik.setTelefon(updateDto.getTelefon());

        //dodavanje novih
        korisnik.setProfilnaSlika(updateDto.getProfilnaSlika());
        korisnik.setDatumRodjenja(updateDto.getDatumRodjenja());
        korisnik.setOpis(updateDto.getOpis());

        // polja za koja je potrebna lozinka
        if(updateDto.getLozinka() != null) {
            if(updateDto.getNovaLozinka().isEmpty() && !updateDto.getKorisnickoIme().isEmpty() && !updateDto.getEmail().isEmpty()) {
                korisnik.setKorisnickoIme(updateDto.getKorisnickoIme());
                korisnik.setEmail(updateDto.getEmail());
                return save(korisnik);
            }
            if(!updateDto.getKorisnickoIme().isEmpty() && !updateDto.getEmail().isEmpty() && !updateDto.getNovaLozinka().isEmpty()) {
                korisnik.setKorisnickoIme(updateDto.getKorisnickoIme());
                korisnik.setEmail(updateDto.getEmail());
                korisnik.setLozinka(updateDto.getNovaLozinka());
            } else
                throw new IllegalArgumentException("Prazno polje");
        }
        return save(korisnik);
    }

    public boolean checkPassword(int id, String password) {
        Korisnik korisnik = korisnikRepository.findById(id).orElse(null);

        if (korisnik == null) {
            return false;
        }

        return korisnik.getLozinka().equals(password);
    }

    public Korisnik save(Korisnik korisnik) { return korisnikRepository.save(korisnik); }
    public Kupac save(Kupac kupac) { return korisnikRepository.save(kupac); }
    public Prodavac save(Prodavac prodavac) { return korisnikRepository.save(prodavac); }


}
