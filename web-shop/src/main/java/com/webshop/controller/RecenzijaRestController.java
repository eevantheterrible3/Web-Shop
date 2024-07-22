package com.webshop.controller;

import com.webshop.dtos.RecenzijaDto;
import com.webshop.model.Korisnik;
import com.webshop.model.Uloga;
import com.webshop.service.KorisnikService;
import com.webshop.service.RecenzijaServis;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecenzijaRestController {

    @Autowired
    RecenzijaServis recenzijaServis;
    @Autowired
    private KorisnikService korisnikService;

    //admin pregleda sve recenzije - 4.1
    @GetMapping("api/reviews")
    public ResponseEntity<?> getReviews(@RequestParam String logovaniUsername, HttpSession session) {

        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);
        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in as an administrator!", HttpStatus.UNAUTHORIZED);
        }
        if(loggedKorisnik.getUloga() != Uloga.ADMIN){
            return new ResponseEntity<>("You must be administrator!", HttpStatus.BAD_REQUEST);
        }

        List<RecenzijaDto> recenzije = recenzijaServis.getAll();
        return new ResponseEntity<>(recenzije, HttpStatus.OK);

    }

    //admin brise recenziju - 4.1
    @DeleteMapping("api/delete-review/{recenzijaId}")
    public ResponseEntity<?> deleteReview(HttpSession session,@RequestParam String logovaniUsername, @PathVariable int recenzijaId) {
        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);
        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in as an administrator!", HttpStatus.UNAUTHORIZED);
        }
        if(loggedKorisnik.getUloga() != Uloga.ADMIN){
            return new ResponseEntity<>("You must be administrator!", HttpStatus.BAD_REQUEST);
        }

        if(recenzijaServis.delete(recenzijaId) == null){
            return new ResponseEntity<>("Review not found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Review successfully deleted!", HttpStatus.OK);
    }


    //admin azurira recenziju - 4.1
    @PutMapping("api/update-review")
    public ResponseEntity<?> updateReview(HttpSession session, @RequestParam String logovaniUsername, @RequestBody RecenzijaDto recenzijaDto) {
        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);
        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in as an administrator!", HttpStatus.UNAUTHORIZED);
        }
        if(loggedKorisnik.getUloga() != Uloga.ADMIN){
            return new ResponseEntity<>("You must be administrator!", HttpStatus.BAD_REQUEST);
        }

        if(recenzijaServis.update(recenzijaDto) == null){
            return new ResponseEntity<>("Review not found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Review successfully updated!", HttpStatus.OK);
    }

    @GetMapping("api/onereview/{id}")
    public ResponseEntity<?> getOnereview(HttpSession session, @PathVariable int id) {
        return new ResponseEntity<>(recenzijaServis.get(id), HttpStatus.OK);
    }
}
