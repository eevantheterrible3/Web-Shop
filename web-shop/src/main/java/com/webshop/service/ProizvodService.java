package com.webshop.service;

import com.webshop.dtos.ProizvodDto;
import com.webshop.model.*;
import com.webshop.repository.KategorijaRepository;
import com.webshop.repository.ProizvodRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.webshop.model.TipProdaje.FIKSNACENA;

@Service
public class ProizvodService {
    @Autowired
    private ProizvodRepository proizvodRepository;

    @Autowired
    private KategorijaService kategorijaService;

    public Page<ProizvodDto> findAllPaginated(Pageable pageable) {
        Page<Proizvod> proizvodPage = proizvodRepository.findAll(pageable);
        return proizvodPage.map(this::convertToDto);
    }

    private ProizvodDto convertToDto(Proizvod proizvod) {
        return new ProizvodDto(proizvod);
    }

    public List<ProizvodDto> findAll() {
        List<Proizvod> proizvodi = proizvodRepository.findAll();
        List<ProizvodDto> proizvodDtos = new ArrayList<>();
        for (Proizvod proizvod : proizvodi) {
            ProizvodDto dto = new ProizvodDto(proizvod);
            proizvodDtos.add(dto);
        }
        return proizvodDtos;
    }

    /*
    public ProizvodDto findOne(int id) {
        Optional<Proizvod> foundProduct = proizvodRepository.findById(id);
        if(foundProduct.isPresent()) {
            return new ProizvodDto(foundProduct.get());
        }
        return null;
    }

     */

    public Proizvod findOne(int id){
        Optional<Proizvod> foundProizvod = proizvodRepository.findById(id);
        if (foundProizvod.isPresent()) {
            return foundProizvod.get();
        }
        return null;
    }

    @Transactional
    public void delete(Proizvod proizvod) {
        if (proizvod != null) {
            proizvodRepository.delete(proizvod);
        }
    }

    public Proizvod findProduct(int id) {
        Optional<Proizvod> foundProduct = proizvodRepository.findById(id);
        if(foundProduct.isPresent()) {
            return foundProduct.get();
        }
        return null;
    }

    public Optional<Proizvod> findById(int id) {
        return proizvodRepository.findById(id);
    }

    public List<ProizvodDto> searchProizvodi(String string){
        List<ProizvodDto> proizvodi = findAll();

        List<ProizvodDto> proizvodIzdvojeni = new ArrayList<>();
        for(ProizvodDto proizvod : proizvodi){
            if(proizvod.getNaziv().toLowerCase().contains(string.toLowerCase()) || proizvod.getOpis().toLowerCase().contains(string.toLowerCase())){
                proizvodIzdvojeni.add(proizvod);
            }
        }

        return proizvodIzdvojeni;
    }

    public List<ProizvodDto> filterProizvodi(String cenaOd, String cenaDo, String tipProdaje, String kategorija){
        List<ProizvodDto> proizvodi = findAll();
        List<ProizvodDto> proizvodIzdvojeni = new ArrayList<>();
        for(ProizvodDto proizvod : proizvodi){
            if(Double.parseDouble(cenaOd) != -1 && Double.parseDouble(cenaDo) != -1){
                if(proizvod.getCena() >= Double.parseDouble(cenaOd) && proizvod.getCena() <= Double.parseDouble(cenaDo)){
                    if(tipProdaje.toLowerCase().equals("aukcija") && !kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == TipProdaje.AUKCIJA && proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.toLowerCase().equals("fiksnacena") && !kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == FIKSNACENA && proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                            proizvodIzdvojeni.add(proizvod);
                        }

                    } else if(tipProdaje.equals("-1") && !kategorija.equals("-1")){
                        if(proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.toLowerCase().equals("aukcija") && kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == TipProdaje.AUKCIJA){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.toLowerCase().equals("fiksnacena") && kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == FIKSNACENA){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.equals("-1") && kategorija.equals("-1")){
                        proizvodIzdvojeni.add(proizvod);
                    }

                }

            } else if(Double.parseDouble(cenaOd) == -1 && Double.parseDouble(cenaDo) != -1){
                if(proizvod.getCena() >= 0 && proizvod.getCena() <= Double.parseDouble(cenaDo)){
                    if(tipProdaje.toLowerCase().equals("aukcija") && !kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == TipProdaje.AUKCIJA && proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.toLowerCase().equals("fiksnacena") && !kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == FIKSNACENA && proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                            proizvodIzdvojeni.add(proizvod);
                        }

                    } else if(tipProdaje.equals("-1") && !kategorija.equals("-1")){
                        if(proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.toLowerCase().equals("aukcija") && kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == TipProdaje.AUKCIJA){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.toLowerCase().equals("fiksnacena") && kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == FIKSNACENA){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.equals("-1") && kategorija.equals("-1")){
                        proizvodIzdvojeni.add(proizvod);
                    }

                }

            } else if(Double.parseDouble(cenaOd) != -1 && Double.parseDouble(cenaDo) == -1){
                if(proizvod.getCena() >= Double.parseDouble(cenaOd)){
                    if(tipProdaje.toLowerCase().equals("aukcija") && !kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == TipProdaje.AUKCIJA && proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.toLowerCase().equals("fiksnacena") && !kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == FIKSNACENA && proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                            proizvodIzdvojeni.add(proizvod);
                        }

                    } else if(tipProdaje.equals("-1") && !kategorija.equals("-1")){
                        if(proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.toLowerCase().equals("aukcija") && kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == TipProdaje.AUKCIJA){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.toLowerCase().equals("fiksnacena") && kategorija.equals("-1")){
                        if(proizvod.getTipProdaje() == FIKSNACENA){
                            proizvodIzdvojeni.add(proizvod);
                        }
                    } else if(tipProdaje.equals("-1") && kategorija.equals("-1")){
                        proizvodIzdvojeni.add(proizvod);
                    }

                }

            } else if(Double.parseDouble(cenaOd) == -1 && Double.parseDouble(cenaDo) == -1){

                if(tipProdaje.toLowerCase().equals("aukcija") && !kategorija.equals("-1")){
                    if(proizvod.getTipProdaje() == TipProdaje.AUKCIJA && proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                        proizvodIzdvojeni.add(proizvod);
                    }
                } else if(tipProdaje.toLowerCase().equals("fiksnacena") && !kategorija.equals("-1")){
                    if(proizvod.getTipProdaje() == FIKSNACENA && proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                        proizvodIzdvojeni.add(proizvod);
                    }

                } else if(tipProdaje.equals("-1") && !kategorija.equals("-1")){
                    if(proizvod.getKategorija().getNaziv().equalsIgnoreCase(kategorija)){
                        proizvodIzdvojeni.add(proizvod);
                    }
                } else if(tipProdaje.toLowerCase().equals("aukcija") && kategorija.equals("-1")){
                    if(proizvod.getTipProdaje() == TipProdaje.AUKCIJA){
                        proizvodIzdvojeni.add(proizvod);
                    }
                } else if(tipProdaje.toLowerCase().equals("fiksnacena") && kategorija.equals("-1")){
                    if(proizvod.getTipProdaje() == FIKSNACENA){
                        proizvodIzdvojeni.add(proizvod);
                    }
                } else if(tipProdaje.equals("-1") && kategorija.equals("-1")){
                    proizvodIzdvojeni.add(proizvod);
                }
            }

        }

        return proizvodIzdvojeni;
    }

    public Proizvod save(Proizvod proizvod) { return proizvodRepository.save(proizvod); }

    public Proizvod updateProduct(int id, ProizvodDto dto) {
        Proizvod proizvod = findOne(id);

        if(proizvod.getTipProdaje() == TipProdaje.FIKSNACENA) {
            if(!dto.getNaziv().isEmpty() && dto.getTipProdaje() != null) {
                proizvod.setNaziv(dto.getNaziv());
                proizvod.setCena(dto.getCena());
                proizvod.setOpis(dto.getOpis());
                Kategorija kategorija = kategorijaService.getKategorija(dto.getKategorija().getNaziv());
                if(kategorija != null) {
                    proizvod.setKategorija(kategorija);

                }
                proizvod.setSlika(dto.getSlika());
                proizvod.setTipProdaje(dto.getTipProdaje());

                return save(proizvod);
            } else
                return null;
        } else if(proizvod.getTipProdaje() == TipProdaje.AUKCIJA){
            // provera da li vec postoji neka ponuda
            if(proizvod.getPonude().isEmpty()) {
                if (!dto.getNaziv().isEmpty() && dto.getTipProdaje() != null) {
                    proizvod.setNaziv(dto.getNaziv());
                    proizvod.setCena(dto.getCena());
                    proizvod.setOpis(dto.getOpis());
                    Kategorija kategorija = kategorijaService.getKategorija(dto.getKategorija().getNaziv());
                    if(kategorija != null) {
                        proizvod.setKategorija(kategorija);

                    }
                    proizvod.setSlika(dto.getSlika());
                    proizvod.setTipProdaje(dto.getTipProdaje());

                    return save(proizvod);
                } else
                    return null;
            }
        }
        return null;
    }
}
