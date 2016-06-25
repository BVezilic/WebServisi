(import utils.*)
(import model.*)
(import model.TipPopusta)
(import model.UlogaKorisnika)

(import java.util.*)

(watch facts)
(deftemplate korisnik
    (declare
        (slot-specific TRUE)
        (from-class Korisnik)
        (include-variables TRUE))
    )

(deftemplate profilKupca
    (declare
        (slot-specific TRUE)
        (from-class ProfilKupca)
        (include-variables TRUE))
    )

(deftemplate kategorijaKupca
    (declare
        (slot-specific TRUE)
        (from-class KategorijaKupca)
        (include-variables TRUE))
    )

(deftemplate racun
    (declare
        (slot-specific TRUE)
        (from-class Racun)
        (include-variables TRUE))
    )

(deftemplate kategorijaArtikla
    (declare
        (slot-specific TRUE)
        (from-class KategorijaArtikla)
        (include-variables TRUE))
    )

(deftemplate artikal
    (declare
 
        (from-class Artikal)
        (include-variables TRUE))
    )

(deftemplate akcijskiDogadjaj
    (declare
 
        (from-class AkcijskiDogadjaj)
        (include-variables TRUE))
    )

(deftemplate stavka
    (declare
 
        (from-class StavkaRacuna)
        (include-variables TRUE))
    )

(deftemplate popust
    (declare
        (slot-specific TRUE)
        (from-class Popust)
        (include-variables TRUE))
    )

(deftemplate pragPotrosnje
    (declare
        (slot-specific TRUE)
        (from-class PragPotrosnje)
        (include-variables TRUE))
    )

(deftemplate popustZaPojedinacnuStavku
    (declare
        (slot-specific TRUE)
        (from-class PopustZaPojedinacnuStavku)
        (include-variables TRUE))
    )

;(bind ?kor1 (new Korisnik "FrMujo" "Mujo" "Raciole" "sarma" (UlogaKorisnika.KUPAC) (new Date 112 05 10)))
;(bind ?kak1 (new KategorijaKupca "GLD" "Zlatni" nil))
;(bind ?kak2 (new KategorijaKupca "SIL" "Srebrni" nil))
;(bind ?pro1 (new ProfilKupca ?kor1 "Vjeternik" 0 ?kak1))

;(bind ?rac1 (new Racun "001" (new Date 116 00 12) nil 0 110000 0.0 210000 0))
;(bind ?rac2 (new Racun "002" (new Date 116 00 31) nil 0 21000 0.0 21000 0))

;(bind ?kat1 (new KategorijaArtikla "001" nil "Siroka potrosnja" 0.3))
;(bind ?kat2 (new KategorijaArtikla "002" nil "Higijena" 0.3))

;(bind ?art1 (new Artikal "100" "Sijalica" ?kat1 200 1000 20 (new Date) FALSE TRUE))
;(bind ?art2 (new Artikal "200" "Steker" ?kat1 300 1000 20 (new Date) FALSE TRUE))

;(bind ?sta1 (new StavkaRacuna ?rac1 1 ?art1 200 6 200 0 200))
;(bind ?sta2 (new StavkaRacuna ?rac2 1 ?art2 200 2 400 0 400))

;(bind ?akd1 (new AkcijskiDogadjaj "NGD" "Novogodisnji praznici" (new Date 115 11 25) (new Date 116 00 20) 0.5))
;(call ?akd1 addKategorijaArtiklaSaPopustima ?kat1)

;(bind ?fak1 (definstance racun ?rac1))
;(bind ?fak2 (definstance racun ?rac2))

;(bind ?fak3 (definstance artikal ?art1))
;(bind ?fak4 (definstance artikal ?art2))

;(bind ?fak5 (definstance stavka ?sta1))
;(bind ?fak6 (definstance stavka ?sta2))

;(bind ?fak7 (definstance kategorijaArtikla ?kat1))
;(bind ?fak8 (definstance kategorijaArtikla ?kat2))

;(bind ?fak9 (definstance akcijskiDogadjaj ?akd1))

;(bind ?fak10 (definstance korisnik ?kor1))
;(bind ?fak11 (definstance profilKupca ?pro1))
;(bind ?fak12 (definstance kategorijaKupca ?kak1))
;(bind ?fak13 (definstance kategorijaKupca ?kak2))

;(printout t (call ?rac1 toString) crlf)
;(printout t (call ?rac2 toString) crlf crlf)

;(printout t (call ?sta1 toString) crlf)
;(printout t (call ?sta2 toString) crlf crlf)

;(printout t (call ?akd1 toString) crlf crlf)
;(printout t (call ?akd1 containsKategorija ?kat1) crlf crlf)

;(printout t (call ?kor1 toString) crlf crlf)
