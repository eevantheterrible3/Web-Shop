--Kategorije
INSERT INTO Kategorija (naziv) VALUES ( 'Nameštaj' ), ('Odeća'), ('Alati'), ('Knjige'), ('Tehnika');

--Administratori
INSERT INTO Korisnik (ime, prezime, korisnicko_ime, email, telefon, lozinka, datum_rodjenja, profilna_slika, opis, uloga, blokiran, DTYPE) VALUES ('Ivan', 'Ristić', 'eevantheterrible', 'ivan.ristic@gmail.com', '0612345678', '***', '2003-01-01', 'images/profile/img1', 'administrator 1', 'ADMIN', false, 'ADMIN');
INSERT INTO Korisnik (ime, prezime, korisnicko_ime, email, telefon, lozinka, datum_rodjenja, profilna_slika, opis, uloga, blokiran, DTYPE) VALUES ('Mirko', 'Pavlovic', 'mire123', 'mirko.mirko@gmail.com', '06422886644', 'mirecar', '1998-02-02', 'images/profile/img6', 'administrator 2', 'ADMIN', false, 'ADMIN');
INSERT INTO Korisnik (ime, prezime, korisnicko_ime, email, telefon, lozinka, datum_rodjenja, profilna_slika, opis, uloga, blokiran, DTYPE) VALUES ('Marija', 'Pavic', 'marijapavic97', 'mara.pav@gmail.com', '065224621', 'maricacarica', '1997-09-11', 'images/profile/img7', 'administrator 3', 'ADMIN', false, 'ADMIN');

--Kupci
INSERT INTO Korisnik (ime, prezime, korisnicko_ime, email, telefon, lozinka, datum_rodjenja, profilna_slika, opis, uloga, blokiran, DTYPE) VALUES ('Ivan', 'Petrović', 'peramobil', 'ppetrovic@gmail.com', '0601234567', 'perazdera', '1992-12-12', 'images/profile/img3', 'polovni automobili', 'KUPAC', true, 'KUPAC');
INSERT INTO Korisnik (ime, prezime, korisnicko_ime, email, telefon, lozinka, datum_rodjenja, profilna_slika, opis, uloga, blokiran, DTYPE) VALUES ('Marko', 'Lalic', 'mare', 'marecare@gmail.com', '062222444666', '123', '1995-12-12', 'images/profile/img4', 'masina', 'KUPAC', false, 'KUPAC');
INSERT INTO Korisnik (ime, prezime, korisnicko_ime, email, telefon, lozinka, datum_rodjenja, profilna_slika, opis, uloga, blokiran, DTYPE) VALUES ('Milos', 'Milojevic', 'miloje123', 'miloje@gmail.com', '062115687', 'mililili', '1995-12-12', 'images/profile/img9', 'ves masina', 'KUPAC', true, 'KUPAC');

--Prodavci
INSERT INTO Korisnik (ime, prezime, korisnicko_ime, email, telefon, lozinka, datum_rodjenja, profilna_slika, opis, uloga, blokiran, DTYPE) VALUES ('Emilija', 'Krstonošić', 'izomerilija', 'ekrstonosic@gmail.com', '0638824063', '**', '2004-01-27', 'images/profile/img2', 'student 2', 'PRODAVAC', false, 'PRODAVAC');
INSERT INTO Korisnik (ime, prezime, korisnicko_ime, email, telefon, lozinka, datum_rodjenja, profilna_slika, opis, uloga, blokiran, DTYPE) VALUES ('Milorad', 'Mitrovic', 'milimilorad', 'miloorad@gmail.com', '0626411589', 'lepimilorad', '1987-01-27', 'images/profile/img10', 'poljoprivreda', 'PRODAVAC', false, 'PRODAVAC');
INSERT INTO Korisnik (ime, prezime, korisnicko_ime, email, telefon, lozinka, datum_rodjenja, profilna_slika, opis, uloga, blokiran, DTYPE) VALUES ('Mihajlo', 'Pantic', 'mica123', 'micamica@gmail.com', '06385441463', 'micalepi', '2000-01-27', 'images/profile/img11', 'stripovi', 'PRODAVAC', false, 'PRODAVAC');

--Ponude
/*INSERT INTO Ponuda (cena, kupac_koji_je_dao_ponudu_id) VALUES (1000, 4);
INSERT INTO Ponuda (cena, kupac_koji_je_dao_ponudu_id) VALUES (2000, 5);
INSERT INTO Ponuda (cena, kupac_koji_je_dao_ponudu_id) VALUES (3000, 6);*/

-- Prijave
/*INSERT INTO PRIJAVA_PROFILA (KORISNIK_PODNEO_ID, korisnik_prijavljen_id, STATUS_PRIJAVE, DATUM_PODNOSENJA_PRIJAVE, RAZLOG_PRIJAVE) VALUES (4, 7, 'PRIHVACENA', '2024-04-10', 'Govor mržnje');
INSERT INTO PRIJAVA_PROFILA (korisnik_podneo_id, korisnik_prijavljen_id, STATUS_PRIJAVE, DATUM_PODNOSENJA_PRIJAVE, RAZLOG_PRIJAVE) VALUES (5, 8, 'PRIHVACENA', '2023-03-09', 'Duple objave');
INSERT INTO PRIJAVA_PROFILA (korisnik_podneo_id, korisnik_prijavljen_id, STATUS_PRIJAVE, DATUM_PODNOSENJA_PRIJAVE, RAZLOG_PRIJAVE) VALUES (9, 6, 'PRIHVACENA', '2022-11-04', 'Lažni opisi proizvoda');
*/
-- Proizvodi
INSERT INTO Proizvod (naziv, opis, slika, kategorija_id, cena, tip_prodaje, datum_objavljivanja, prodavac_id, ostavljena_recenzija_od_kupca, ostavljena_recenzija_od_prodavca, prodat) VALUES ('Kauč', 'Super stanje', 'https://s.cdnshm.com/catalog/rs/t/42374386/matis-kauc-orion-bez-mp1009.jpg', 1, 10000, 'FIKSNACENA', '2024-09-04', 8, false, false, false);
INSERT INTO Proizvod (naziv, opis, slika, kategorija_id, cena, tip_prodaje, datum_objavljivanja, prodavac_id, ostavljena_recenzija_od_kupca, ostavljena_recenzija_od_prodavca, prodat) VALUES ('Pantalone', 'Nosene', 'https://www.dalavoro.com/c/1940-medium_default/isacco-chef-pants.jpg', 2, 10000, 'FIKSNACENA', '2024-09-04', 8, false, false, false);
INSERT INTO Proizvod (naziv, opis, slika, kategorija_id, cena, tip_prodaje, datum_objavljivanja, prodavac_id, ostavljena_recenzija_od_kupca, ostavljena_recenzija_od_prodavca, prodat) VALUES ('Busilica', 'Nova', 'https://s.cdnshm.com/catalog/rs/t/48456514/raider-aku-busilica-rdp-sbcd20-20v-2-2ah-030215-2853.jpg', 3, 10000, 'AUKCIJA', '2024-09-04', 9, false, false, false);
INSERT INTO Proizvod (naziv, opis, slika, kategorija_id, cena, tip_prodaje, datum_objavljivanja, prodavac_id, ostavljena_recenzija_od_kupca, ostavljena_recenzija_od_prodavca, prodat) VALUES ('Sorts', 'Dobro', 'https://s.cdnshm.com/catalog/rs/t/50070307/kupaci-sorts-aqua-speed-finn.jpg', 2, 2000, 'FIKSNACENA', '2024-09-04', 8, false, false, false);
INSERT INTO Proizvod (naziv, opis, slika, kategorija_id, cena, tip_prodaje, datum_objavljivanja, prodavac_id, ostavljena_recenzija_od_kupca, ostavljena_recenzija_od_prodavca, prodat) VALUES ('Haljina', 'Ocuvana', 'https://s.cdnshm.com/catalog/rs/t/2271293/crno-bela-haljina-sa-detaljima-cipke.jpg', 2, 1000, 'FIKSNACENA', '2024-09-04', 7, false, false, false);
INSERT INTO Proizvod (naziv, opis, slika, kategorija_id, cena, tip_prodaje, datum_objavljivanja, prodavac_id, ostavljena_recenzija_od_kupca, ostavljena_recenzija_od_prodavca, prodat) VALUES ('Laptop', 'Nov', 'https://konovo.rs/wp-content/uploads/2024/04/2a14d58e6c5f813a323dbddd297dec78.jpg', 5, 50000, 'AUKCIJA', '2024-09-04', 9, false, false, false);

-- Proizvod_ponude
/*INSERT INTO Proizvod_ponude (ponude_id, proizvod_id) VALUES (1, 3);
INSERT INTO Proizvod_ponude (ponude_id, proizvod_id) VALUES (2, 3);
INSERT INTO Proizvod_ponude (ponude_id, proizvod_id) VALUES (3, 3);*/

-- RECENZIJA
/*INSERT INTO Recenzija (korisnik_recenzija_id, ocena, datum_recenzije, komentar) VALUES (4, 5, '2024-04-09', 'saradnja 10++');
INSERT INTO Recenzija (korisnik_recenzija_id, ocena, datum_recenzije, komentar) VALUES (5, 4, '2024-04-10', 'preporuke');
INSERT INTO Recenzija (korisnik_recenzija_id, ocena, datum_recenzije, komentar) VALUES (6, 3, '2024-04-11', 'može bolje');*/

--aa


