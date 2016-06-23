(batch "./jess/init.clp")
(import java.util.ArrayList)
(import java.lang.Integer)
(watch facts)
;PRAVILA ZA STAVKE RACUNA

;(defrule ispisiKorisnika
    
    ;?kor <- (korisnik (profilKupca ?pk))
  ;  =>
  ;  (bind ?lst ?pk.realizovaneKupovine)
    ;(printout t "Prvi element " ?lst)
  ;  
  ;  )

;(defrule ispisKorisnika2
;    ?kor <- (korisnik (profilKupca ?pk))
;    =>
;    (printout t (call ?kor.profilKupca getKategorijaKupca))
;    )


(defrule popustZaViseOd20
    (declare (no-loop TRUE) (salience 50))
    ?s <- (stavka (artikal ?ar) (kolicinaKupnjeljihArtikala ?kol &:(and (> ?kol 20) (<> (call ?ar getNazivKategorije) "Skolski pribor") (<> (call ?ar getNazivNadKategorije) "Skolski pribor"))))
    ;?s <- (stavka (artikal ?ar) (kolicinaKupnjeljihArtikala ?kol &:(and (> ?kol 20) (<> (get (get ?ar kategorijaArtikla) naziv) "Skolski pribor") (<> (call ?ar getNazivNadKategorije) "Skolski pribor"))))
    =>
    (call ?s.OBJECT addPrimenjeniPopust (new PopustZaPojedinacnuStavku "001" (get ?s racun) 0.1 (TipPopusta.OSNOVNI) ?s.OBJECT))
    ;(printout t (call (new Popust "001" nil 0.1 (TipPopusta.OSNOVNI)) toString) crlf)
    ;(printout t "radi mi pravilo " (call ?s.OBJECT toString) crlf)
    ;(printout t " ISPIS: " (call ?ar getNazivKategorije))
    )

;televizori, racunari ili laptopovi
(defrule popustZa5IzTehnike
    (declare (no-loop TRUE) (salience 49))
    ?s <- (stavka (artikal ?ar) (kolicinaKupnjeljihArtikala ?kol &:(and  (> ?kol 5) (or (and (eq (call ?ar getNazivKategorije) "Televizori") (eq (call ?ar getNazivNadKategorije) "Televizori") ) 
               (or (eq (call ?ar getNazivKategorije) "Racunari") (eq (call ?ar getNazivNadKategorije) "Racunari") )
               (or (eq (call ?ar getNazivKategorije) "Laptopovi") (eq (call ?ar getNazivNadKategorije) "Laptopovi") )     ))))
    ;(artikal (kategorijaArtikla ?kat) (sifra ?sifra &?a.sifra))
    ;(kategorijaArtikla (sifraKategorije ?kat1 &?kat.sifraKategorije) (naziv ?n |:(or (eq ?n "laptopovi")(eq ?n "racunari")(eq ?n "televizori"))))
    =>
    (call ?s.OBJECT addPrimenjeniPopust (new PopustZaPojedinacnuStavku "002" (get ?s racun) 0.05 (TipPopusta.OSNOVNI) ?s.OBJECT))
    ;(printout t "radi mi i drugo pravilo" crlf crlf)
    )

;>5000 i siroka potrosnja
(defrule viseOd5000
    (declare (no-loop TRUE) (salience 51))
    ?s <- (stavka (artikal ?ar) (originalnaUkupnaCena ?cena &:(and(> ?cena 5000) (eq (call ?ar getNazivKategorije) "Skolski pribor") (eq (call ?ar getNazivNadKategorije) "Skolski pribor") )))
    ;(artikal (kategorijaArtikla ?kat) (sifra ?sifra &?a.sifra))
    ;(kategorijaArtikla (sifraKategorije ?kat1 &?kat.sifraKategorije) (naziv ?n &"Siroka potrosnja"))
    =>
    (call ?s.OBJECT addPrimenjeniPopust (new PopustZaPojedinacnuStavku "003" (get ?s racun) 0.07 (TipPopusta.OSNOVNI) ?s.OBJECT))
    ;(printout t "radi mi trece pravilo")
    )

;DODATNI POPUSTI

;popust za isti artikal u prethodnih 15 dana
(defrule dodatni1
    (declare (no-loop TRUE) (salience 52))
    ;(stavka (artikal ?artSt1) (racun ?racSt1))
    ;(stavka (artikal ?artSt2) (racun ?racSt2))
    
    ;(racun (sifra ?rac1) (sifra ?rac1 &?racSt1.sifra) (datumIzdavanja ?date1))
    ;?racP <- (racun (sifra ?rac2 &?racSt2.sifra) (sifra ?rac2 &:(<> ?rac2 ?rac1) ) (datumIzdavanja ?date2))
    ;(test (and (< (call Utility dateDifference ?date2 ?date1) 15) (> (call Utility dateDifference ?date2 ?date1) 0))) 
    
    ;(artikal (sifra ?art &?artSt1.sifra) (sifra ?art2 &?artSt2.sifra))
    ?s <- (stavka (racun ?r) (artikal ?ar &:(call Utility getArticlesInSpan ?r 15 ?ar)))
    =>
    ;(printout t crlf crlf "napravicu onaj 2% popust" crlf )
    (call ?s.OBJECT addPrimenjeniPopust (new PopustZaPojedinacnuStavku "004" (get ?s racun) 0.02 (TipPopusta.DODATNI) ?s.OBJECT))
    )

;popust za artikal iste kategorije 30 dana
(defrule dodatni2
    (declare (no-loop TRUE) (salience 53))
    ;(stavka (artikal ?artSt1) (racun ?racSt1))
    ;(stavka (artikal ?artSt2) (racun ?racSt2))
    
    ;(racun (sifra ?rac1) (sifra ?rac1 &?racSt1.sifra) (datumIzdavanja ?date1))
    ;?racP <- (racun (sifra ?rac2 &?racSt2.sifra) (sifra ?rac2 &:(<> ?rac2 ?rac1) ) (datumIzdavanja ?date2))
    
    ;(test (and (< (call Utility dateDifference ?date2 ?date1) 30) (> (call Utility dateDifference ?date2 ?date1) 0))) 
    
    ;(artikal (sifra ?art1 &?artSt1.sifra) (kategorijaArtikla ?kat))
    ;(artikal (sifra ?art2 &:(<> ?art1 ?art2))(sifra ?art2 &?artSt2.sifra) (kategorijaArtikla ?kat))
    ?s <- (stavka (racun ?r) (artikal ?ar &:(call Utility getCatInSpan ?r 30 ?ar)))
    =>
    (call ?s.OBJECT addPrimenjeniPopust (new PopustZaPojedinacnuStavku "005" (get ?s racun) 0.01 (TipPopusta.DODATNI) ?s.OBJECT))
    ;(printout t crlf crlf "napravicu onaj 1% popust" crlf)
    
    )

;popust ako pripada nekom akcijskom dogadjaju
(defrule dodatni3
    (declare (no-loop TRUE) (salience 55))
    ?s <- (stavka (artikal ?a) (racun ?racSt1))
    ;(racun (sifra ?rac1 &?racSt1.sifra) (datumIzdavanja ?date1))
    ;(artikal (kategorijaArtikla ?kat) (sifra ?sifra &?a.sifra))
    ?akd <- (akcijskiDogadjaj (vaziDo ?vaziDo) (vaziOd ?vaziOd) (kategorijaArtiklaSaPopustima ?katList))
    ;(bind ?akdO ?akd.OBJECT)
    (test (call Utility isWithinDates (get ?racSt1 datumIzdavanja) ?vaziOd ?vaziDo))
    ;(test (call ?akdO containsKategorija ?kat))
    (test (or  (call ?katList contains (get (get ?a kategorijaArtikla) nadkategorija)) (call ?katList contains (get ?a kategorijaArtikla))) )
    =>
   	;(printout t crlf crlf (call ?s.OBJECT toString) crlf)
    ;(printout t "Napravicu onaj popust za akcijski dogadjaj u vrednosti od " ?akd.popustZaDogadjaj crlf)
    ;(printout t crlf "AKCIJSKI DOGADJAJ " " " (call ?vaziOd toString) " " (call ?vaziDo toString))
    (call ?s.OBJECT addPrimenjeniPopust (new PopustZaPojedinacnuStavku "006" (get ?s racun) (get ?akd popustZaDogadjaj) (TipPopusta.DODATNI) ?s.OBJECT))
    )

;saberi sve popuste
(defrule saberiPopusteZaStavke
    (declare (no-loop TRUE) (salience 0))
    ?s <-(stavka)
    =>
    (bind ?lst (get ?s primenjeniPopusti))
    (bind ?temp 0)
    (foreach ?x ?lst
		(bind ?temp (+ ?temp (get ?x  procenatUmanjenja))))
    (bind ?max (get (get (get ?s artikal) kategorijaArtikla) maksimalniDozvoljeniPopust))
    (if(<= ?temp ?max) then
		(call ?s.OBJECT setProcenatUmanjenja ?temp)
	else
		(call ?s.OBJECT setProcenatUmanjenja ?max))
    (bind ?temp (* (get ?s jedinicnaCena) (get ?s kolicinaKupnjeljihArtikala) (- 1 (call ?s.OBJECT getProcenatUmanjenja))))
    (call ?s.OBJECT setKonacnaCena ?temp)
    )


/*
*
PRAVILA ZA RACUNE!!!!
*
*/

;5% popusta za preko 200000
(defrule osnovniR1
    (declare (no-loop TRUE) (salience 40))
    ?r<-(racun (originalnaUkupnaCena ?ukupnaCena &:(> ?ukupnaCena 200000)))
    =>
    ;(printout t crlf "primenice se 5% popusta na racun za 200000" crlf)
    (call ?r.OBJECT addPrimenjeniPopust (new Popust "007" ?r.OBJECT 0.05 (TipPopusta.OSNOVNI)))
    )

;1% na ceo racun ako je kupac duze od dve godine aktivan
(defrule dodatniR1
    (declare (no-loop TRUE) (salience 50))
    ;(korisnik (datumRegistrovanja ?datumRegistrovanja))
    ;(racun (datumIzdavanja ?datumIzdavanja))
    ;(test (> (call Utility dateDifference ?datumIzdavanja ?datumRegistrovanja) 730))
    ?r<-(racun)
    (test (call Utility korisniciStaz 2 (get (get (get ?r kupac) korisnik) datumRegistrovanja)))
    =>
    ;(printout t crlf "primeniti popust od 2% za stare kupce" crlf)
    (call ?r.OBJECT addPrimenjeniPopust (new Popust "008" ?r.OBJECT 0.01 (TipPopusta.DODATNI)))
    )

;popust za srebrne/zladne kupce
(defrule dodatniR2
    (declare (no-loop TRUE) (salience 60))
    ;(profilKupca (kategorijaKupca ?kat))
    ;(kategorijaKupca (sifraKategorije ?sifraKategorije &?kat.sifraKategorije) (sifraKategorije ?sifra &:(or (eq ?sifra "SIL")(eq ?sifra "GLD"))))
    ?r<-(racun (kupac ?kup &:(or (eq (get(get ?kup kategorijaKupca) nazivKategorije) "SIL") (eq (get(get ?kup kategorijaKupca) nazivKategorije) "GLD")) ))
    =>
    ;(printout t crlf "popust za srebrne/zlatne kupce od 1%" crlf)
    (call ?r.OBJECT addPrimenjeniPopust (new Popust "009" ?r.OBJECT 0.01 (TipPopusta.DODATNI)))
    )

;popust 3%
(defrule dodatniR3
    (declare (no-loop TRUE) (salience 70))
    ?r <- (racun (originalnaUkupnaCena ?ukupnaCena &:(and(> ?ukupnaCena 50000) (call Utility overHalf (get ?r originalnaUkupnaCena) (get ?r stavkeRacuna) 0.5))))
    =>
    (call ?r.OBJECT addPrimenjeniPopust (new Popust "010" ?r.OBJECT 0.03 (TipPopusta.DODATNI)))
    )

;izracunavanje ukupnog popusta na racunu
(defrule sabriPopusteZaRacune
    (declare (no-loop TRUE) (salience 0))
    ?r<-(racun)
    =>
    (bind ?lst (get ?r primenjeniPopusti))
    (bind ?temp 0)
    (foreach ?x ?lst
		(bind ?temp (+ ?temp (get ?x  procenatUmanjenja))))
	(call ?r.OBJECT setProcenatUmanjenja ?temp)
    (bind ?temp (* (get ?s jedinicnaCena) (get ?s kolicinaKupnjeljihArtikala) (- 1 (call ?s.OBJECT getProcenatUmanjenja))))
    (call ?r.OBJECT setKonacnaCena ?temp)
    )

;proveri da li je potrebno da se poruci jos artikala
(defrule proveriLagerArtikala
    ?a <- (artikal (minimalnoStanjeNaLageru ?ms) (brojnoStanje ?br&:(< ?br ?ms)))
    =>
    ; uradi nesto
    )

;(run)