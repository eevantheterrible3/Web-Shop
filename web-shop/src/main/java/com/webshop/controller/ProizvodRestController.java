package com.webshop.controller;

import com.webshop.dtos.ProizvodDto;
import com.webshop.model.Korisnik;
import com.webshop.model.Prodavac;
import com.webshop.model.Proizvod;
import com.webshop.model.Uloga;
import com.webshop.service.KorisnikService;
import com.webshop.service.ProdavacService;
import com.webshop.service.ProizvodService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
public class ProizvodRestController {
    @Autowired
    private ProizvodService proizvodService;

    @Autowired
    private ProdavacService prodavacService;
    @Autowired
    private KorisnikService korisnikService;

    //pregled svih proizvoda - 1.1
    @GetMapping("api/products")
    public ResponseEntity<List<ProizvodDto>> getProizvodi(HttpSession session) {
        Korisnik loggedUser = (Korisnik) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            System.out.println("Nema sesije");
            List<ProizvodDto> proizvodi = proizvodService.findAll();
            return ResponseEntity.ok(proizvodi);
        } else {
            System.out.println(loggedUser);
            List<ProizvodDto> proizvodi = proizvodService.findAll();
            return ResponseEntity.ok(proizvodi);
        }
    }

    //pregled svih proizvoda sa paginacijom - 1.1
    @GetMapping("api/products-page")
    public ResponseEntity<Page<ProizvodDto>> getProizvodiPaginated(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProizvodDto> proizvodPage = proizvodService.findAllPaginated(pageable);
        return ResponseEntity.ok(proizvodPage);
    }

    //pregled jednog proizvoda
    @GetMapping("api/products/{id}")
    public ResponseEntity<?> getProizvod(@PathVariable int id, HttpSession session){
        Proizvod proizvod = proizvodService.findOne(id);
        if(proizvod == null){
            return new ResponseEntity<>("Product is not found!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(proizvod);
    }

    //pretraga proizvoda po imenu i opisu - 1.2
    @GetMapping("api/search-products/{naziv}")
    public ResponseEntity<List<ProizvodDto>> searchProducts(@PathVariable(required = false) String naziv) {
        List<ProizvodDto> dtos;
        if(naziv == null){
             dtos = proizvodService.findAll();
            return ResponseEntity.ok(dtos);

        }
            dtos = proizvodService.searchProizvodi(naziv);
        return ResponseEntity.ok(dtos);



    }

    //filtriranje proizvoda - 1.3
    @GetMapping("api/filter-products")
    public ResponseEntity<List<ProizvodDto>> filterProizvodi(@RequestParam String cenaOd, @RequestParam String cenaDo, @RequestParam String tipProdaje, @RequestParam String kategorija) {

        List<ProizvodDto> dtos = proizvodService.filterProizvodi(cenaOd, cenaDo, tipProdaje, kategorija);

        if (dtos.isEmpty()) {
            return ResponseEntity.ok(dtos);
        } else {
            return ResponseEntity.ok(dtos);
        }
    }

    @PutMapping("api/proizvod/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody ProizvodDto updateDto, @RequestParam String logovaniUsername, HttpSession session) {
        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);

        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }

        if (loggedKorisnik.getUloga() == Uloga.PRODAVAC ) {
            Prodavac prodavac = prodavacService.findOne(loggedKorisnik.getId());
            Proizvod proizvod = proizvodService.findOne(id);
            if(proizvod != null) {
                if(prodavac.getProizvodiNaProdaju().contains(proizvod)) {
                    Proizvod azurirani_proizvod = proizvodService.updateProduct(id, updateDto);
                    if(azurirani_proizvod != null) {
                        return ResponseEntity.ok(azurirani_proizvod);
                        //return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
                    } else{
                        return new ResponseEntity<>("Empty name or tipProdaje field (or a bid has already been placed)", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("Not your product!", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Product unavailable!", HttpStatus.BAD_REQUEST);
            }
        } else
            return new ResponseEntity<>("Forbidden user role", HttpStatus.FORBIDDEN);
    }
}
