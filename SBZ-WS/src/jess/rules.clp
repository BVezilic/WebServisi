(batch "./jess/init.clp")
(watch facts)
;PRAVILA ZA STAVKE RACUNA

(defrule popustZaViseOd20
    
    
    ?s <- (stavka (kolicinaKupnjeljihArtikala ?kol &:(> ?kol 20)))
    
    =>
    (call ?s.OBJECT addPrimenjeniPopust (new Popust "001" nil 0.1 (TipPopusta.OSNOVNI)))
    ;(printout t (call (new Popust "001" nil 0.1 (TipPopusta.OSNOVNI)) toString) crlf)
    (printout t "radi mi pravilo " (call ?s.OBJECT toString) crlf)
    
    )

;televizori, racunari ili laptopovi
(defrule popustZa5IzTehnike
   
    ?s <- (stavka (kolicinaKupnjeljihArtikala ?kol &:(> ?kol 5)) (artikal ?a))
    
    (artikal (kategorijaArtikla ?kat) (sifra ?sifra &?a.sifra))
    
    (kategorijaArtikla (sifraKategorije ?kat1 &?kat.sifraKategorije) (naziv ?n |:(or (eq ?n "laptopovi")(eq ?n "racunari")(eq ?n "televizori"))))
    
    
    =>
    
    (printout t "radi mi i drugo pravilo" crlf crlf)
    
    )

;>5000 i siroka potrosnja
(defrule viseOd5000
    
    ?s <- (stavka (artikal ?a) (konacnaCena ?cena &:(> ?cena 5000)))
    
    (artikal (kategorijaArtikla ?kat) (sifra ?sifra &?a.sifra))
    
    (kategorijaArtikla (sifraKategorije ?kat1 &?kat.sifraKategorije) (naziv ?n &"Siroka potrosnja"))
    
    
    
    =>
    
    (printout t "radi mi trece pravilo")
    
    )

;DODATNI POPUSTI

;popust za isti artikal u prethodnih 15 dana
(defrule dodatni1
    
    (stavka (artikal ?artSt1) (racun ?racSt1))
    (stavka (artikal ?artSt2) (racun ?racSt2))
    
    (racun (sifra ?rac1) (sifra ?rac1 &?racSt1.sifra) (datumIzdavanja ?date1))
    ?racP <- (racun (sifra ?rac2 &?racSt2.sifra) (sifra ?rac2 &:(<> ?rac2 ?rac1) ) (datumIzdavanja ?date2))
    (test (and (< (call Utility dateDifference ?date2 ?date1) 15) (> (call Utility dateDifference ?date2 ?date1) 0))) 
    
    (artikal (sifra ?art &?artSt1.sifra) (sifra ?art2 &?artSt2.sifra))
    
    
    =>
    
    (printout t crlf crlf "napravicu onaj 2% popust" crlf )
    
    )

;popust za artikal iste kategorije 30 dana
(defrule dodatni2
    
    (stavka (artikal ?artSt1) (racun ?racSt1))
    (stavka (artikal ?artSt2) (racun ?racSt2))
    
    (racun (sifra ?rac1) (sifra ?rac1 &?racSt1.sifra) (datumIzdavanja ?date1))
    ?racP <- (racun (sifra ?rac2 &?racSt2.sifra) (sifra ?rac2 &:(<> ?rac2 ?rac1) ) (datumIzdavanja ?date2))
    
    (test (and (< (call Utility dateDifference ?date2 ?date1) 30) (> (call Utility dateDifference ?date2 ?date1) 0))) 
    
    (artikal (sifra ?art1 &?artSt1.sifra) (kategorijaArtikla ?kat))
    (artikal (sifra ?art2 &:(<> ?art1 ?art2))(sifra ?art2 &?artSt2.sifra) (kategorijaArtikla ?kat))
    
    =>
    
    (printout t crlf crlf "napravicu onaj 1% popust" crlf)
    
    )

;popust ako pripada nekom akcijskom dogadjaju
(defrule dodatni3
    
    ?s <- (stavka (artikal ?a) (racun ?racSt1))
    
    (racun (sifra ?rac1 &?racSt1.sifra) (datumIzdavanja ?date1))
    
    (artikal (kategorijaArtikla ?kat) (sifra ?sifra &?a.sifra))
    
    ?akd <- (akcijskiDogadjaj (vaziDo ?vaziDo) (vaziOd ?vaziOd) (kategorijaArtiklaSaPopustima ?katList))
    
    ;(bind ?akdO ?akd.OBJECT)
    
    (test (call Utility isWithinDates ?date1 ?vaziOd ?vaziDo))
    ;(test (call ?akdO containsKategorija ?kat))
    (test (call ?katList contains ?kat))
    
    
    
    =>
   	(printout t crlf crlf (call ?s.OBJECT toString) crlf)
    (printout t "Napravicu onaj popust za akcijski dogadjaj u vrednosti od " ?akd.popustZaDogadjaj crlf)
    
    )
/*
*
PRAVILA ZA RACUNE!!!!
*
*/

;5% popusta za preko 200000
(defrule osnovniR1
    
    (racun (originalnaUkupnaCena ?ukupnaCena &:(> ?ukupnaCena 200000)))
    
    =>
    
    (printout t crlf "primenice se 5% popusta na racun za 200000" crlf)
    
    )

;1% na ceo racun ako je kupac duze od dve godine aktivan
(defrule dodatniR1
    
    (korisnik (datumRegistrovanja ?datumRegistrovanja))
    (racun (datumIzdavanja ?datumIzdavanja))
    (test (> (call Utility dateDifference ?datumIzdavanja ?datumRegistrovanja) 730))
    
    =>
    
 
    (printout t crlf "primeniti popust od 2% za stare kupce" crlf)
    
    )

;popust za srebrne/zladne kupce
(defrule dodatniR2
    
    (profilKupca (kategorijaKupca ?kat))
    (kategorijaKupca (sifraKategorije ?sifraKategorije &?kat.sifraKategorije) (sifraKategorije ?sifra &:(or (eq ?sifra "SIL")(eq ?sifra "GLD"))))
    
    =>
    
    (printout t crlf "popust za srebrne/zlatne kupce od 1%" crlf)
    
    )
;(run)