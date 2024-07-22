package com.webshop.controller;

import com.webshop.model.Kategorija;
import com.webshop.service.KategorijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KategorijaRestController {
    @Autowired
    private KategorijaService kategorijaService;

    @GetMapping("api/kategorije")
    public ResponseEntity<List<Kategorija>> getKategorije() {
        List<Kategorija> kategorijaList = kategorijaService.getKategorijaList();

        if (kategorijaList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(kategorijaService.getKategorijaList(), HttpStatus.OK);
    }
}
