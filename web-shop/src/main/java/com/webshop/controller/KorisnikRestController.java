package com.webshop.controller;

import com.webshop.dtos.*;
import com.webshop.model.*;
import com.webshop.service.*;
import com.webshop.repository.KupacRepository;
import com.webshop.repository.PonudaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
public class KorisnikRestController {
    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KupacService kupacService;

    @Autowired
    private ProdavacService prodavacService;

    @Autowired
    private ProizvodService proizvodService;

    @Autowired
    private PrijavaService prijavaService;

    @Autowired
    private PonudaService ponudaService;

    @Autowired
    private PonudaRepository ponudaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RecenzijaServis recenzijaServis;

    @Autowired
    private KategorijaService kategorijaService;

    @Autowired
    private KupacRepository kupacRepository;


    //Registrovanje korisnika - 1.4
    @PostMapping("api/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistracijaDto dto){
        if(dto.getIme()==""){
            return new ResponseEntity<>("First name is not correct!", HttpStatus.BAD_REQUEST);
        }
        if(dto.getPrezime()==""){
            return new ResponseEntity<>("Last name name is not correct!", HttpStatus.BAD_REQUEST);
        }

        if(korisnikService.existsEmail(dto.getEmail())){
            return new ResponseEntity<>("Mail already used!", HttpStatus.BAD_REQUEST);
        }
        if(korisnikService.existsKorisnickoIme(dto.getUsername())){
            return new ResponseEntity<>("Username already used!", HttpStatus.BAD_REQUEST);
        }
        if(!(dto.getUloga().equalsIgnoreCase("kupac") || dto.getUloga().equalsIgnoreCase("prodavac"))){
            return new ResponseEntity<>("Role is not correct!", HttpStatus.BAD_REQUEST);
        }

        if(dto.getLozinka() == "" || !dto.getLozinka().equals(dto.getLozinkaPonovljena())){
            return new ResponseEntity<>("Password is not correct!", HttpStatus.BAD_REQUEST);

        }
        korisnikService.create(dto);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    //Login korisnika - 1.5
    @PostMapping("api/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpSession session) {
        if (loginDto.getKorisnickoIme().isEmpty() || loginDto.getLozinka().isEmpty()) {
            return new ResponseEntity<>("Invalid login data", HttpStatus.BAD_REQUEST);
        }

        Korisnik loggedKorisnik = korisnikService.login(loginDto.getKorisnickoIme(), loginDto.getLozinka());

        if (loggedKorisnik == null) {
            return new ResponseEntity<>("User does not exist!", HttpStatus.NOT_FOUND);
        }

        session.setAttribute("loggedUser", loggedKorisnik);
        return ResponseEntity.ok(loggedKorisnik);
    }

    @GetMapping("api/check-session")
    public ResponseEntity<?> checkSession(HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("loggedUser");
        if (loggedKorisnik == null) {
            return new ResponseEntity<>("No user is logged in", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(loggedKorisnik);
    }

    //Azuriranje profila - 2.1., 3.1
    @PutMapping("api/korisnik/update")
    public ResponseEntity<?> updateUser(@RequestBody EditDto updateDto,HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("loggedUser");

        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }

        if (loggedKorisnik.getUloga() == Uloga.KUPAC  || loggedKorisnik.getUloga() == Uloga.PRODAVAC ) {

            if(!((updateDto.getLozinka() == null || updateDto.getLozinka().isEmpty())  && (updateDto.getPonovljenaLozinka() == null || updateDto.getPonovljenaLozinka().isEmpty()))) {
                Korisnik azurirani_korisnik = korisnikService.updateUser(loggedKorisnik.getId(), updateDto);
                if(azurirani_korisnik != null) {
                    return ResponseEntity.ok(azurirani_korisnik);
                    //return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Empty field [name / lastname / phone number]!", HttpStatus.BAD_REQUEST);
                }
            }

            if(updateDto.getLozinka() == null || updateDto.getPonovljenaLozinka() == null) {
                return new ResponseEntity<>("Insert password to change username, email or password!", HttpStatus.BAD_REQUEST);
            }

            if (!Objects.equals(loggedKorisnik.getLozinka(), updateDto.getLozinka())) {
                return new ResponseEntity<>("Invalid password", HttpStatus.BAD_REQUEST);
            }

            if (Objects.equals(loggedKorisnik.getLozinka(), updateDto.getLozinka()) && Objects.equals(updateDto.getLozinka(), updateDto.getPonovljenaLozinka())) {
                Korisnik azurirani_korisnik = korisnikService.updateUser(loggedKorisnik.getId(), updateDto);
                if(azurirani_korisnik != null) {
                    return ResponseEntity.ok(azurirani_korisnik);
                    //return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Empty field [username / email] ", HttpStatus.BAD_REQUEST);
                }
            }  else
                return new ResponseEntity<>("Passwords don't match", HttpStatus.BAD_REQUEST);

        } else
            return new ResponseEntity<>("Forbidden user role", HttpStatus.FORBIDDEN);
    }

    @PutMapping("/logged-user/update")
    public ResponseEntity<?> updateUser2(@RequestBody EditDto kupacDto, @RequestParam String logovaniUsername, HttpSession session) {
        Korisnik loggedUser = korisnikService.findOneByUsername(logovaniUsername);

        if (loggedUser == null) {
            return new ResponseEntity<>("Nijedan korisnik nije prijavljen!", HttpStatus.BAD_REQUEST);
        }

        if (loggedUser.getUloga() == Uloga.ADMIN) {
            return new ResponseEntity<>("Ulogovani korisnik nije kupac ni prodavac!", HttpStatus.FORBIDDEN);
        }

        if (kupacDto.getKorisnickoIme() != null || kupacDto.getEmail() != null || kupacDto.getNovaLozinka() != null) {
            if (!korisnikService.checkPassword(loggedUser.getId(), kupacDto.getLozinka())) {
                return new ResponseEntity<>("Trenutna lozinka nije tačna.", HttpStatus.BAD_REQUEST);
            }

            if (kupacDto.getKorisnickoIme() != null) {
                loggedUser.setKorisnickoIme(kupacDto.getKorisnickoIme());
            }

            if (kupacDto.getEmail() != null) {
                loggedUser.setEmail(kupacDto.getEmail());
            }

            if(kupacDto.getNovaLozinka() != null) {
                if(!kupacDto.getNovaLozinka().equals("")) {
                    loggedUser.setLozinka(kupacDto.getNovaLozinka());

                }
            }
        }

        if (kupacDto.getIme() != null)
            loggedUser.setIme(kupacDto.getIme());

        if (kupacDto.getPrezime() != null)
            loggedUser.setPrezime(kupacDto.getPrezime());

        if (kupacDto.getTelefon() != null)
            loggedUser.setTelefon(kupacDto.getTelefon());

        if (kupacDto.getDatumRodjenja() != null)
            loggedUser.setDatumRodjenja(kupacDto.getDatumRodjenja());

        if (kupacDto.getProfilnaSlika() != null)
            loggedUser.setProfilnaSlika(kupacDto.getProfilnaSlika());

        if (kupacDto.getOpis() != null)
            loggedUser.setOpis(kupacDto.getOpis());

        korisnikService.save(loggedUser);
        return new ResponseEntity<>("Korisnik je uspesno azurirao podatke!", HttpStatus.OK);
    }

    //Pregled profila nekog kupca - 2.2, 3.2
    @GetMapping("api/korisnik/view/{username}")
    public ResponseEntity<?> viewKorisnik(@PathVariable String username, HttpSession session){
        Korisnik korisnik = korisnikService.findOneByUsername(username);

        if(korisnik != null) {
            if (korisnik.getUloga() == Uloga.KUPAC) {
                ViewKupacDto dto = kupacService.viewKupac(korisnik.getId());

                return ResponseEntity.ok(dto);
            } else if (korisnik.getUloga() == Uloga.PRODAVAC) {
                ViewProdavacDto dto = prodavacService.viewProdavac(korisnik.getId());
                return ResponseEntity.ok(dto);
            } else {
                Korisnik admin = korisnikService.findOneByUsername(username);
                return ResponseEntity.ok(admin);
            }
        }
        return new ResponseEntity<>("User not found with id: " + korisnik.getId(), HttpStatus.NOT_FOUND);
    }

    //Pregled svih korisnika - 2.2, 3.2
    @GetMapping("api/korisnici")
    public ResponseEntity<?> Korisnici(HttpSession session){
        List<Korisnik> korisnici = korisnikService.findAll();
        return ResponseEntity.ok(korisnici);



    }


    //prijava prodavca - 2.6
    @PostMapping("api/reportseller/{id}")
    public ResponseEntity<?> reportUser(@PathVariable int id, @RequestBody ReportDto dto,@RequestParam String loggedUsername, HttpSession session){
        Korisnik loggedKorisnik = korisnikService.findOneByUsername(loggedUsername);

        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }
        if(loggedKorisnik.getUloga() != Uloga.KUPAC){
            return new ResponseEntity<>("Bad user role!", HttpStatus.FORBIDDEN);
        }

        Kupac kupac = kupacService.findOne(loggedKorisnik.getId());

        // ako prosledjujemo id proizvoda
        Proizvod proizvod = proizvodService.findProduct(id);

        if(proizvod == null){
            return new ResponseEntity<>("Can't find product with id: " + id, HttpStatus.BAD_REQUEST);
        }
        if(!kupac.containsKupljeniProizvod(proizvod)){
            return new ResponseEntity<>("You didn't buy this producst!", HttpStatus.BAD_REQUEST);
        }

        if(proizvod != null) {
            Set<Proizvod> lista_kupljenih = kupac.getKupljeniProizvodi();

            if(lista_kupljenih.contains(proizvod)) {
                dto.setKupac(kupac.getId());
                dto.setDatumPrijave(LocalDate.now());
                dto.setProdavac(proizvod.getProdavac().getId());

                PrijavaProfila prijava = kupacService.reportUser(dto);
                prijavaService.save(prijava);

                return new ResponseEntity<>("Prijava uspesno sacuvana!", HttpStatus.OK);
                //return new ResponseEntity<>("Report sent successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No reason for report", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("Product not found with id: " + id, HttpStatus.NOT_FOUND);
        }

    }


    //prijava kupca - 3.8
    @PostMapping("api/reportbuyer/{id}")
    public ResponseEntity<?> reportBuyer(@PathVariable int id, @RequestBody ReportDto dto, @RequestParam String logovaniUsername, HttpSession session){
        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);

        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }
        if(loggedKorisnik.getUloga() != Uloga.PRODAVAC){
            return new ResponseEntity<>("Bad user role!", HttpStatus.FORBIDDEN);
        }

        Prodavac prodavac = prodavacService.findOne(loggedKorisnik.getId());

        // ako prosledjujemo id proizvoda
        Proizvod proizvod = proizvodService.findProduct(id);
        if(proizvod == null){
            return new ResponseEntity<>("Can't find product with id: " + id, HttpStatus.BAD_REQUEST);
        }

        Kupac kupac = kupacRepository.findByContainKupljeniproizvod(proizvod);

        if(proizvod.getProdavac().getId() != loggedKorisnik.getId()){
            return new ResponseEntity<>("You were not a seller of this product!", HttpStatus.FORBIDDEN);
        }

        if(kupac != null) {
            dto.setKupac(kupac.getId());
            dto.setDatumPrijave(LocalDate.now());
            dto.setProdavac(proizvod.getProdavac().getId());

            PrijavaProfila prijava = prodavacService.reportUser(dto);
            prijavaService.save(prijava);

            return new ResponseEntity<>("Prijava uspesno sacuvana!", HttpStatus.OK);
            //return new ResponseEntity<>("Report sent successfully", HttpStatus.OK);
        } else {
                return new ResponseEntity<>("No reason for report", HttpStatus.FORBIDDEN);
            }
    }

    //Kupovina proizvoda fiksnom cenom - 2.3
    @PostMapping("api/proizvod-fiksnacena/{idProizvoda}")
    public ResponseEntity<?> kupiProizvodFiksnaCena(@PathVariable int idProizvoda, @RequestBody KorisnikDto logovani, HttpSession session){
        //Korisnik loggedKorisnik = (Korisnik) session.getAttribute("loggedUser");

        if(logovani == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }

        /*if(kupacDto.getUloga() != Uloga.KUPAC){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }*/

        Kupac loggedKupac = kupacService.findOneByKorisnickoIme(logovani.getKorisnickoIme());
        Optional<Proizvod> proizvodOptional = proizvodService.findById(idProizvoda);
        if(!proizvodOptional.isPresent()){
            return new ResponseEntity<>("Product is not found with id: " + idProizvoda, HttpStatus.NOT_FOUND);
        }
        Proizvod proizvod = proizvodOptional.get();
        if(proizvod.isProdat()){
            return new ResponseEntity<>("Product is already sold!", HttpStatus.BAD_REQUEST);
        }

        Optional<Prodavac> prodavacOptional = prodavacService.findById(proizvod.getProdavac().getId());

        if(proizvod.getTipProdaje() != TipProdaje.FIKSNACENA){
            return new ResponseEntity<>("Product selling type is wrong!", HttpStatus.BAD_REQUEST);
        }
        if(!prodavacOptional.isPresent()){
            return new ResponseEntity<>("Product must have a seller!: " + idProizvoda, HttpStatus.NOT_FOUND);

        }
        Prodavac prodavac = prodavacOptional.get();
        prodavac.removeProizvodiNaProdajuElement(proizvod);
        proizvod.setProdat(true);
        loggedKupac.addKupljeniProizvodi(proizvod);
        korisnikService.save(loggedKupac);
        korisnikService.save(prodavac);
        proizvodService.save(proizvod);

        String buyerEmail = loggedKupac.getEmail();
        String sellerEmail = prodavac.getEmail();
        String productName = proizvod.getNaziv();

        emailService.sendEmail(buyerEmail, "Purchase Confirmation", "You have successfully purchased the product: " + productName);
        emailService.sendEmail(sellerEmail, "Product Sold", "Your product has been sold: " + productName);

        return new ResponseEntity<>("Product is sold.", HttpStatus.OK);

    }

    //Bidovanje proizvoda aukcijom - 2.3
    @PostMapping("api/proizvod-aukcija/{idProizvoda}")
    public ResponseEntity<?> kupiProizvodAukcija(@PathVariable int idProizvoda, @RequestBody KorisnikDto logovani,@RequestParam Double ponuda, HttpSession session){
        if(logovani == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }



        Kupac loggedKupac = kupacService.findOneByKorisnickoIme(logovani.getKorisnickoIme());
        Optional<Proizvod> proizvodOptional = proizvodService.findById(idProizvoda);
        if(!proizvodOptional.isPresent()){
            return new ResponseEntity<>("Product is not found with id: " + idProizvoda, HttpStatus.NOT_FOUND);
        }
        Proizvod proizvod = proizvodOptional.get();
        if(proizvod.isProdat()){
            return new ResponseEntity<>("Product is sold! Auction is finished.", HttpStatus.BAD_REQUEST);
        }
        if(proizvod.getProdavac() == null){
            return new ResponseEntity<>("Product must have a seller!: " + idProizvoda, HttpStatus.NOT_FOUND);

        }
        Optional<Prodavac> prodavacOptional = prodavacService.findById(proizvod.getProdavac().getId());

        if(proizvod.getTipProdaje() != TipProdaje.AUKCIJA){
            return new ResponseEntity<>("Product selling type is wrong!", HttpStatus.BAD_REQUEST);
        }

        if(ponuda < proizvod.getCena()){
            return new ResponseEntity<>("Bidded price is low", HttpStatus.BAD_REQUEST);
        }

        Prodavac prodavac = prodavacOptional.get();

        Ponuda maxPonuda = ponudaService.findTopByProizvodOrderByCenaDesc(proizvod);
        if(maxPonuda!=null && ponuda <= maxPonuda.getCena()){
            return new ResponseEntity<>("Bidded price is low", HttpStatus.BAD_REQUEST);
        }

        Ponuda novaPonuda = new Ponuda(ponuda, proizvod, loggedKupac);
        proizvod.addPonuda(novaPonuda);

        ponudaService.save(novaPonuda);

        String sellerEmail = prodavac.getEmail();
        String productName = proizvod.getNaziv();
        double novaCena = novaPonuda.getCena();

        emailService.sendEmail(sellerEmail, "New offer for products", "There is new offer for product with id: " + proizvod.getId() + " " + productName + ". New offer is " + novaCena +"." );

        return new ResponseEntity<>("Offer is saved.", HttpStatus.OK);

    }

    //kraj aukcije - 3.4
    @PostMapping("api/aukcija-kraj/{idProizvoda}")
    public ResponseEntity<?> aukcijaKraj(@PathVariable int idProizvoda, @RequestParam String logovaniUsername, HttpSession session){
        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);
        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }

        if(loggedKorisnik.getUloga() != Uloga.PRODAVAC){
            return new ResponseEntity<>("You are not a seller!", HttpStatus.FORBIDDEN);
        }

        Optional<Proizvod> proizvodOptional = proizvodService.findById(idProizvoda);
        if(!proizvodOptional.isPresent()){
            return new ResponseEntity<>("Product is not found with id: " + idProizvoda, HttpStatus.NOT_FOUND);
        }
        Proizvod proizvod = proizvodOptional.get();

        if(proizvod.isProdat()){
            return new ResponseEntity<>("Product is sold! Auction is already finished.", HttpStatus.BAD_REQUEST);
        }

        Optional<Prodavac> loggedProdavacOptional = prodavacService.findById(proizvod.getProdavac().getId());
        if(!loggedProdavacOptional.isPresent()){
            return new ResponseEntity<>("Product must have a seller!: ", HttpStatus.NOT_FOUND);
        }

        Prodavac prodavac = loggedProdavacOptional.get();
        if(prodavac.getId() != loggedKorisnik.getId()){
            return new ResponseEntity<>("You are not owner of this product!", HttpStatus.BAD_REQUEST);
        }

        if(proizvod.ponudeIsEmpty()){
            return new ResponseEntity<>("There are no offers for this product! You cannot end auction!", HttpStatus.BAD_REQUEST);
        }

        Ponuda maxPonuda = ponudaRepository.findTopByProizvodOrderByCenaDesc(proizvod);
        Kupac kupac = maxPonuda.getKupacKojiJeDaoPonudu();
        prodavac.removeProizvodiNaProdajuElement(proizvod);
        proizvod.setProdat(true);
        kupac.addKupljeniProizvodi(proizvod);
        korisnikService.save(kupac);
        korisnikService.save(prodavac);
        proizvodService.save(proizvod);

        String buyerEmail = kupac.getEmail();
        String sellerEmail = prodavac.getEmail();
        String productName = proizvod.getNaziv();

        emailService.sendEmail(buyerEmail, "Purchase Confirmation", "You have successfully purchased the product: " + productName);
        emailService.sendEmail(sellerEmail, "Product Sold", "Your product has been sold: " + productName);

        return new ResponseEntity<>("Auction is successfully ended.", HttpStatus.OK);
    }

    //ocenjivanje prodavca 2.4
    @PostMapping("api/oceni-prodavca")
    public ResponseEntity<?> oceniProdavca(@RequestBody CombinedDto combinedDto, HttpSession session){
        OceniProdavcaDto oceniProdavcaDto = combinedDto.getOceniProdavcaDto();
        KorisnikDto logovani = combinedDto.getLogovani();

        if(logovani == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }
        Kupac kupac = kupacService.findOneByKorisnickoIme(logovani.getKorisnickoIme());
        Korisnik loggedKorisnik = korisnikService.findOneByUsername(kupac.getKorisnickoIme());


        Optional<Proizvod> proizvodOptional = proizvodService.findById(oceniProdavcaDto.getProizvodId());
        if(!proizvodOptional.isPresent()){
            return new ResponseEntity<>("There is no product with forwarded id!", HttpStatus.BAD_REQUEST);
        }
        Proizvod proizvod = proizvodOptional.get();
        Optional<Prodavac> prodavacOptional = prodavacService.findById(proizvod.getProdavac().getId());
        if(!prodavacOptional.isPresent()){
            return new ResponseEntity<>("Seller is not found with that id", HttpStatus.BAD_REQUEST);
        }
        Prodavac prodavac = prodavacOptional.get();
        if(!kupac.containsKupljeniProizvod(proizvod)){
            return new ResponseEntity<>("Buyer doesn't have a product of forwarded seller!", HttpStatus.BAD_REQUEST);
        }
        if(oceniProdavcaDto.getOcena() < 1 || oceniProdavcaDto.getOcena() > 5 ){
            return new ResponseEntity<>("Rating must be a value between 1 and 5!", HttpStatus.BAD_REQUEST);
        }

        if(proizvod.isOstavljenaRecenzijaOdKupca()){
            return new ResponseEntity<>("Rating is already given!", HttpStatus.BAD_REQUEST);
        }

        Recenzija newRecenzija = new Recenzija(oceniProdavcaDto.getOcena(), oceniProdavcaDto.getKomentar(), LocalDateTime.now(), loggedKorisnik);
        prodavac.setProsecnaOcena(1.0*(prodavac.sumOfDobijenihRecenzija() + newRecenzija.getOcena())/(prodavac.brojDobijenihRecenzija() + 1));
        prodavac.addRecenzija(newRecenzija);
        proizvod.setOstavljenaRecenzijaOdKupca(true);

        recenzijaServis.save(newRecenzija);
        korisnikService.save(prodavac);
        proizvodService.save(proizvod);

        return new ResponseEntity<>("Review is successfully saved.", HttpStatus.OK);

    }

    //ocenjivanje kupca 3.6
    @PostMapping("api/oceni-kupca")
    public ResponseEntity<?> oceniKupca(@RequestBody OceniKupcaDto oceniKupcaDto, @RequestParam String logovaniUsername, HttpSession session){
        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);
        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }
        if(loggedKorisnik.getUloga() != Uloga.PRODAVAC){
            return new ResponseEntity<>("You are not a seller!", HttpStatus.FORBIDDEN);
        }
        Optional<Prodavac> prodavacOptional = prodavacService.findById(loggedKorisnik.getId());

        if(!prodavacOptional.isPresent()){
            return new ResponseEntity<>("Seller is not found with that id", HttpStatus.BAD_REQUEST);
        }

        Prodavac prodavac = prodavacOptional.get();

        Optional<Proizvod> proizvodOptional = proizvodService.findById(oceniKupcaDto.getProizvodId());
        if(!proizvodOptional.isPresent()){
            return new ResponseEntity<>("There is no product with forwarded id!", HttpStatus.BAD_REQUEST);
        }
        Proizvod proizvod = proizvodOptional.get();
        Kupac kupac = kupacRepository.findByContainKupljeniproizvod(proizvod);
        if(kupac == null){
            return new ResponseEntity<>("Buyer is not found with that id", HttpStatus.BAD_REQUEST);
        }

        if(!prodavac.getProizvodiNaProdaju().contains(proizvod)){
            return new ResponseEntity<>("It's not a product of this seller!", HttpStatus.BAD_REQUEST);
        }
        if(oceniKupcaDto.getOcena() < 1 || oceniKupcaDto.getOcena() > 5 ){
            return new ResponseEntity<>("Rating must be a value between 1 and 5!", HttpStatus.BAD_REQUEST);
        }

        if(proizvod.isOstavljenaRecenzijaOdProdavca()){
            return new ResponseEntity<>("Rating is already given!", HttpStatus.BAD_REQUEST);
        }

        Recenzija newRecenzija = new Recenzija(oceniKupcaDto.getOcena(), oceniKupcaDto.getKomentar(), LocalDateTime.now(), loggedKorisnik);
        kupac.setProsecnaOcena(1.0*(kupac.sumOfDobijenihRecenzija() + newRecenzija.getOcena())/(kupac.brojDobijenihRecenzija() + 1));
        kupac.addRecenzija(newRecenzija);
        proizvod.setOstavljenaRecenzijaOdProdavca(true);

        recenzijaServis.save(newRecenzija);
        korisnikService.save(kupac);
        proizvodService.save(proizvod);

        return new ResponseEntity<>("Review is successfully saved.", HttpStatus.OK);

    }

    //postavljanje prodaje 3.3
    @PostMapping("api/post-product")
    public ResponseEntity<?> postProduct(@RequestBody PostavljanjeProdajeDto postavljanjeProdajeDto, @RequestParam String logovaniUsername, HttpSession session){
        Korisnik loggedKorisnik = korisnikService.findOneByUsername(logovaniUsername);
        if(loggedKorisnik == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }
        if(loggedKorisnik.getUloga() != Uloga.PRODAVAC){
            return new ResponseEntity<>("You are not a seller!", HttpStatus.FORBIDDEN);
        }
        Optional<Prodavac> prodavacOptional = prodavacService.findById(loggedKorisnik.getId());
        if(!prodavacOptional.isPresent()){
            return new ResponseEntity<>("Seller is not found with that id", HttpStatus.BAD_REQUEST);
        }
        Prodavac prodavac = prodavacOptional.get();

        Proizvod proizvod = new Proizvod();
        proizvod.setProdavac(prodavac);
        proizvod.setCena(postavljanjeProdajeDto.getCena());
        proizvod.setNaziv(postavljanjeProdajeDto.getNazivProizvoda());
        proizvod.setOstavljenaRecenzijaOdKupca(false);
        proizvod.setOstavljenaRecenzijaOdProdavca(false);
        proizvod.setProdat(false);
        proizvod.setOpis(postavljanjeProdajeDto.getOpisProizvoda());
        proizvod.setDatumObjavljivanja(LocalDateTime.now());
        proizvod.setSlika(postavljanjeProdajeDto.getSlika());
        proizvod.setPonude(null);
        if(postavljanjeProdajeDto.getTipProdaje().equalsIgnoreCase("aukcija")){
            proizvod.setTipProdaje(TipProdaje.AUKCIJA);
        } else if(postavljanjeProdajeDto.getTipProdaje().equalsIgnoreCase("fiksnacena")){
            proizvod.setTipProdaje(TipProdaje.FIKSNACENA);
        } else{
            return new ResponseEntity<>("Wrong type of selling!", HttpStatus.BAD_REQUEST);
        }

        Kategorija kategorija = kategorijaService.getKategorija(postavljanjeProdajeDto.getImeKategorije());
        if(kategorija == null){
            Kategorija kategorija1 = new Kategorija(postavljanjeProdajeDto.getImeKategorije());
            proizvod.setKategorija(kategorija1);
            kategorijaService.save(kategorija1);

        } else{
            proizvod.setKategorija(kategorija);
            kategorijaService.save(kategorija);

        }

        prodavac.addProizvod(proizvod);
        proizvodService.save(proizvod);
        korisnikService.save(prodavac);

        return new ResponseEntity<>("New product is successfully saved.", HttpStatus.OK);
    }


    //logout 1.5
    @PostMapping("api/logout")
    public ResponseEntity<?> logout(HttpSession session) {

        Korisnik korisnik = (Korisnik) session.getAttribute("loggedUser");

        if (korisnik == null) {
            return new ResponseEntity<>("Nisi logovan!", HttpStatus.FORBIDDEN);
        }
        session.invalidate();
        return new ResponseEntity<>("Logged out!", HttpStatus.OK);
    }

    @GetMapping("api/korisnik/reviews")
    public ResponseEntity<?> viewReviews(@RequestParam String logovaniUsername, HttpSession session){

        Korisnik korisnik = korisnikService.findOneByUsername(logovaniUsername);

        if(korisnik == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }

        if(korisnik.getUloga() != Uloga.KUPAC && korisnik.getUloga() != Uloga.PRODAVAC){
            return new ResponseEntity<>("Bad user role!", HttpStatus.FORBIDDEN);
        }

        Set<Recenzija> recenzije = korisnik.getDobijeneRecenzije();

        return ResponseEntity.ok(recenzije);
    }

    @GetMapping("api/korisnik/view-review/{id}")
    public ResponseEntity<?> viewReview(@PathVariable int id, HttpSession session){
        Korisnik loggedUser = (Korisnik) session.getAttribute("loggedUser");

        if(loggedUser == null){
            return new ResponseEntity<>("You must be logged in!", HttpStatus.BAD_REQUEST);
        }

        Korisnik korisnik = korisnikService.findOne(id);

        if(korisnik == null)
            return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);

        if(korisnik.getUloga() != Uloga.KUPAC && korisnik.getUloga() != Uloga.PRODAVAC) {
            return new ResponseEntity<>("Bad user role (administrator)!", HttpStatus.FORBIDDEN);
        }

        if(loggedUser.getUloga() == Uloga.KUPAC) {
            if(korisnik.getUloga() == Uloga.PRODAVAC) {
                Set<Recenzija> recenzije = korisnik.getDobijeneRecenzije();

                int dozvoljeno = 0;
                for (Recenzija recenzija : recenzije) {
                    if (recenzija.getRecenzijaKorisnik().getId() == loggedUser.getId()) {
                        System.out.println(loggedUser);
                        dozvoljeno = 1;
                        break;
                    }
                }

                if(dozvoljeno != 1)
                    return new ResponseEntity<>("Can't view reviews of a user you didnt review", HttpStatus.FORBIDDEN);

                return ResponseEntity.ok(recenzije);
            }
            if(korisnik.getUloga() == Uloga.KUPAC)
                return new ResponseEntity<>("Bad user role (kupac)!", HttpStatus.FORBIDDEN);
        }

        if(loggedUser.getUloga() == Uloga.PRODAVAC) {
            if(korisnik.getUloga() == Uloga.KUPAC) {
                Set<Recenzija> recenzije = korisnik.getDobijeneRecenzije();

                int dozvoljeno = 0;
                for (Recenzija recenzija : recenzije) {
                    if (recenzija.getRecenzijaKorisnik().getId() == loggedUser.getId()) {
                        dozvoljeno = 1;
                        break;
                    }
                }

                if(dozvoljeno != 1)
                    return new ResponseEntity<>("Can't view user reviews you didnt review", HttpStatus.FORBIDDEN);

                return ResponseEntity.ok(recenzije);
            }
            if(korisnik.getUloga() == Uloga.PRODAVAC)
                return new ResponseEntity<>("Bad user role (prodavac)!", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Bad user role!", HttpStatus.FORBIDDEN);

    }

}
