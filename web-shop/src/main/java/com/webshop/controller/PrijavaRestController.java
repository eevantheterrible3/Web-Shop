package com.webshop.controller;

import com.webshop.dtos.*;
import com.webshop.model.*;
import com.webshop.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PrijavaRestController {
    @Autowired
    private PrijavaService prijavaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ProdavacService prodavacService;

    @Autowired
    private EmailService emailService;

    //administrator pregleda sve prijave - 4.2
    @GetMapping("api/reports")
    public ResponseEntity<?> getReports(HttpSession session, @RequestParam String logovaniUsername) {

        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);
        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in as an administrator!", HttpStatus.UNAUTHORIZED);
        }
        if(loggedKorisnik.getUloga() != Uloga.ADMIN){
            return new ResponseEntity<>("You must be administrator!", HttpStatus.BAD_REQUEST);
        }

        List<PrijavaDto> prijave = prijavaService.getAll();
        return new ResponseEntity<>(prijave, HttpStatus.OK);

    }

    //administrator prihvata prijavu - 4.2
    @Transactional
    @PostMapping("api/acceptreport/{reportId}")
    public ResponseEntity<?> acceptReport(HttpSession session,@RequestParam String logovaniUsername, @PathVariable int reportId) {

        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);
        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in as an administrator!", HttpStatus.UNAUTHORIZED);
        }
        if(loggedKorisnik.getUloga() != Uloga.ADMIN){
            return new ResponseEntity<>("You must be administrator!", HttpStatus.BAD_REQUEST);
        }

        PrijavaProfila prijava = prijavaService.findOne(reportId);
        if(prijava == null){
            return new ResponseEntity<>("Report not found!", HttpStatus.NOT_FOUND);
        }
        Korisnik prijavljen = korisnikService.findOne(prijava.getKorisnikPrijavljen().getId());
        if(prijavljen == null){
            return new ResponseEntity<>("Reported user not found!", HttpStatus.NOT_FOUND);
        }
        if(prijava.getStatusPrijave()  != StatusPrijave.PODNETA){
            return new ResponseEntity<>("Report is not in pending status!", HttpStatus.NOT_FOUND);
        }
        prijavljen.setBlokiran(true);
        if(prijavljen.getUloga() == Uloga.PRODAVAC){
            Prodavac prodavac = prodavacService.findOne(prijava.getKorisnikPrijavljen().getId());
            if(prodavac == null){
                return new ResponseEntity<>("Reported user is not a seller!", HttpStatus.NOT_FOUND);
            }
            prodavac.setBlokiran(true);
           /* Set<Proizvod> proizvodi = prodavac.getProizvodiNaProdaju();
            for (Proizvod proizvod : proizvodi) {
                proizvodService.delete(proizvod);
            }*/
            prodavac.getProizvodiNaProdaju().clear();
            korisnikService.save(prodavac);
        }

        String prijavioEmail = prijava.getKorisnikPodneo().getEmail();
        String prijavljenEmail = prijavljen.getEmail();
        emailService.sendEmail(prijavljenEmail, "Block status", "Your account is blocked!");
        emailService.sendEmail(prijavioEmail, "Report request", "Your report request is accepted! Thank you for reporting!");
        prijava.setStatusPrijave(StatusPrijave.PRIHVACENA);

        prijavaService.save(prijava);
        korisnikService.save(prijavljen);

        return new ResponseEntity<>("Report is accepted!", HttpStatus.OK);

    }

    //administrator odbija prijavu - 4.2
    @PostMapping("api/rejectreport/{reportId}")
    public ResponseEntity<?> rejectReport(HttpSession session,@RequestParam String logovaniUsername, @RequestParam String reason, @PathVariable int reportId) {

        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);
        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in as an administrator!", HttpStatus.UNAUTHORIZED);
        }
        if(loggedKorisnik.getUloga() != Uloga.ADMIN){
            return new ResponseEntity<>("You must be administrator!", HttpStatus.BAD_REQUEST);
        }

        PrijavaProfila prijava = prijavaService.findOne(reportId);
        if(prijava == null){
            return new ResponseEntity<>("Report not found!", HttpStatus.NOT_FOUND);
        }
        Korisnik prijavljen = korisnikService.findOne(prijava.getKorisnikPrijavljen().getId());
        if(prijavljen == null){
            return new ResponseEntity<>("Reported user not found!", HttpStatus.NOT_FOUND);
        }
        if(prijava.getStatusPrijave()  != StatusPrijave.PODNETA){
            return new ResponseEntity<>("Report is not in pending status!", HttpStatus.NOT_FOUND);
        }

        prijava.setStatusPrijave(StatusPrijave.ODBIJENA);
        prijavaService.save(prijava);
        String prijavioEmail = prijava.getKorisnikPodneo().getEmail();
        emailService.sendEmail(prijavioEmail, "Report request", reason+ "Thank you for reporting!");

        return new ResponseEntity<>(reason, HttpStatus.OK);

    }
}
