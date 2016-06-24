(batch "./jess/init.clp")
(import java.util.ArrayList)
(import model.*)
(import model.Popust)
(import model.Racun)
(import model.StanjeRacuna)
(watch facts)
;PRAVILA ZA STAVKE RACUNA

(defrule popustZaViseOd20
    (declare (no-loop TRUE) (salience 50))
    ?s <- (stavka (racun ?r &:(eq (get ?r stanjeRacuna) (StanjeRacuna.PORUCENO))) (artikal ?ar) (kolicinaKupnjeljihArtikala ?kol &:(and (> ?kol 20) (<> (call ?ar getNazivKategorije) "Skolski pribor") (<> (call ?ar getNazivNadKategorije) "Skolski pribor"))))
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
    ?s <- (stavka (racun ?r &:(eq (get ?r stanjeRacuna) (StanjeRacuna.PORUCENO))) (artikal ?ar) (kolicinaKupnjeljihArtikala ?kol &:(and  (> ?kol 5) (or (and (eq (call ?ar getNazivKategorije) "Televizori") (eq (call ?ar getNazivNadKategorije) "Televizori") ) 
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
    ?s <- (stavka (racun ?r &:(eq (get ?r stanjeRacuna) StanjeRacuna.PORUCENO)) (artikal ?ar) (originalnaUkupnaCena ?cena &:(and(> ?cena 5000) (eq (call ?ar getNazivKategorije) "Skolski pribor") (eq (call ?ar getNazivNadKategorije) "Skolski pribor") )))
    ;(artikal (kategorijaArtikla ?kat) (sifra ?sifra &?a.sifra))
    ;(kategorijaArtikla (sifraKategorije ?kat1 &?kat.sifraKategorije) (naziv ?n &"Siroka potrosnja"))
    =>
    (call ?s.OBJECT addPrimenjeniPopust (new PopustZaPojedinacnuStavku "003" (get ?s racun) 0.07 (TipPopusta.OSNOVNI) ?s.OBJECT))
    ;(printout t "radi mi trece pravilo")
    )

;DODATNI POPUSTI

; funkcija koja proveri da li je artikal kupljen u prethdnih 15 dana
(deffunction getArticlesInSpan(?racun ?opseg ?artikal)
    (bind ?retVal (new ArrayList))
    (bind ?rcLst (get (get ?racun kupac) realizovaneKupovine))
    (foreach ?x ?rcLst
		(if(< (call Utility dateDifference (get ?racun datumIzdavanja) (get ?x datumIzdavanja)) ?opseg ) then
            (bind ?lstStv (get ?x stavkeRacuna))
            (foreach ?t ?lstStv
				(call ?retVal add (get ?t artikal))
                )
		)
        )
    return (call ?retVal contains ?artikal)
    )

;popust za isti artikal u prethodnih 15 dana
(defrule dodatni1
    (declare (no-loop TRUE) (salience 52))
    ;(stavka (artikal ?artSt1) (racun ?racSt1))
    ;(stavka (artikal ?artSt2) (racun ?racSt2))
    
    ;(racun (sifra ?rac1) (sifra ?rac1 &?racSt1.sifra) (datumIzdavanja ?date1))
    ;?racP <- (racun (sifra ?rac2 &?racSt2.sifra) (sifra ?rac2 &:(<> ?rac2 ?rac1) ) (datumIzdavanja ?date2))
    ;(test (and (< (call Utility dateDifference ?date2 ?date1) 15) (> (call Utility dateDifference ?date2 ?date1) 0))) 
    
    ;(artikal (sifra ?art &?artSt1.sifra) (sifra ?art2 &?artSt2.sifra))
    ;?s <- (stavka (racun ?r) (artikal ?ar &:(call Utility getArticlesInSpan ?r 15 ?ar)))
    ?s <- (stavka (racun ?r &:(eq (get ?r stanjeRacuna) (StanjeRacuna.PORUCENO))) (artikal ?ar &:(getArticlesInSpan ?r 15 ?ar)))
    =>
    ;(printout t crlf crlf "napravicu onaj 2% popust" crlf )
    (call ?s.OBJECT addPrimenjeniPopust (new PopustZaPojedinacnuStavku "004" (get ?s racun) 0.02 (TipPopusta.DODATNI) ?s.OBJECT))
    )

; proverava da li je proizvod iz iste kategorije kupljen u prethodnih 30 dana
(deffunction getCatInSpan(?racun ?opseg ?artikal)
    (bind ?retVal (new ArrayList))
    (bind ?rcLst (get (get ?racun kupac) realizovaneKupovine))
    (foreach ?x ?rcLst
		(if(< (call Utility dateDifference (get ?racun datumIzdavanja) (get ?x datumIzdavanja)) ?opseg ) then
            (bind ?lstStv (get ?x stavkeRacuna))
            (foreach ?t ?lstStv
				(call ?retVal add (get (get ?t artikal)kategorijaArtikla))
                (if(<> (get(get (get ?t artikal)kategorijaArtikla)nadkategorija) nil) then
                	(call ?retVal add (get(get (get ?t artikal)kategorijaArtikla)nadkategorija))
                )
             )
		)
    )
    return (or (call ?retVal contains (get ?artikal kategorijaArtikla)) (call ?retVal contains (get (get ?artikal kategorijaArtikla)nadkategorija)))
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
    ?s <- (stavka (racun ?r &:(eq (get ?r stanjeRacuna) (StanjeRacuna.PORUCENO))) (artikal ?ar &:(call Utility getCatInSpan ?r 30 ?ar)))
    =>
    (call ?s.OBJECT addPrimenjeniPopust (new PopustZaPojedinacnuStavku "005" (get ?s racun) 0.01 (TipPopusta.DODATNI) ?s.OBJECT))
    ;(printout t crlf crlf "napravicu onaj 1% popust" crlf)
    
    )

;popust ako pripada nekom akcijskom dogadjaju
(defrule dodatni3
    (declare (no-loop TRUE) (salience 55))
    ?s <- (stavka (artikal ?a) (racun ?racSt1 &:(eq (get ?racSt1 stanjeRacuna) (StanjeRacuna.PORUCENO))))
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


;sortiraj listu po rastucem redosledu ako bog da
(deffunction uporediFunkcija(?name ?obj1 ?obj2)
    "Sortiranje studenata po proseku DESC a zatim po imenu ASC"
    
    (if (eq ?obj1 ?obj2) then 
        (return 0)    
     elif (and (neq ?obj1 nil) (neq ?obj2 nil) (instanceof ?obj1 Popust) (instanceof ?obj2 Popust)) then
        (bind ?retVal (- ?obj2.procenatUmanjenja ?obj1.procenatUmanjenja)) ;rezultat može biti realna vrednost 6.5-6.23
        (if (> ?retVal 0) then ;zato sto compare prima integer vrednosti 
            (bind ?retVal 1)
         elif (< ?retVal 0) then
            (bind ?retVal -1)
         else					;0.0-0.0 je 0.0
            (bind ?retVal 0))
        (return ?retVal))
    (return -1))

;saberi sve popuste
(defrule saberiPopusteZaStavke
    (declare (no-loop TRUE) (salience 0))
    ?s <-(stavka (racun ?r &:(eq (get ?r stanjeRacuna) (StanjeRacuna.PORUCENO))))
    =>
    (bind ?lst (get ?s primenjeniPopusti))
    (bind ?temp 0)
    
    (bind ?lstDod (new ArrayList))
	(bind ?lstOsn (new ArrayList))
    
    ;podeli popuste u dodatne i osnovne da bi primenio samo najpovoljniji osnovni
    (foreach ?x ?lst
		(if(eq (TipPopusta.OSNOVNI) (get ?x oznaka) ) then
            (call ?lstOsn add ?x)
        else
            (call ?lstDod add ?x)
		))
    
    (bind ?co (implement Comparator using uporediFunkcija))
	(call Collections sort ?lstOsn ?co)
    
    (if(> (call ?lstOsn size) 0) then 
    	(bind ?temp (+ ?temp (get (call ?lstOsn  get 0) procenatUmanjenja))))
   
    
    ; saberi sve dodatne popuste
    (foreach ?x ?lstDod
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
    (test(eq (get ?r stanjeRacuna) (StanjeRacuna.PORUCENO)))
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
    (test(eq (get ?r stanjeRacuna) (StanjeRacuna.PORUCENO)))
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
    (test(eq (get ?r stanjeRacuna) (StanjeRacuna.PORUCENO)))
    =>
    ;(printout t crlf "popust za srebrne/zlatne kupce od 1%" crlf)
    (call ?r.OBJECT addPrimenjeniPopust (new Popust "009" ?r.OBJECT 0.01 (TipPopusta.DODATNI)))
    )

;funkcija koja odredjuje da li prelazi prag za treci dodatni popust na racun
(deffunction overHalf(?ukupnaCena ?stLst ?prag)
    (bind ?count 0)
    (if(> (call ?stLst size) 10) then
       (foreach ?x ?lstDod
			(bind ?count (+ ?count (get(get ?x  artikal)cena)))
        )
        if((> (* ?prag ?ukupnaCena) ?count) then
            return 1
        else
            return 0
         )
    else
        return 0
     )
)

;popust 3%
(defrule dodatniR3
    (declare (no-loop TRUE) (salience 70))
    ;?r <- (racun (originalnaUkupnaCena ?ukupnaCena &:(and(> ?ukupnaCena 50000) (call Utility overHalf (get ?r originalnaUkupnaCena) (get ?r stavkeRacuna) 0.5))))
    ?r <- (racun (originalnaUkupnaCena ?ukupnaCena &:(and(> ?ukupnaCena 50000) (overHalf (get ?r originalnaUkupnaCena) (get ?r stavkeRacuna) 0.5))))
    (test(eq (get ?r stanjeRacuna) (StanjeRacuna.PORUCENO)))
    =>
    (call ?r.OBJECT addPrimenjeniPopust (new Popust "010" ?r.OBJECT 0.03 (TipPopusta.DODATNI)))
    )

;izracunavanje ukupnog popusta na racunu
(defrule saberiPopusteZaRacune
    (declare (no-loop TRUE) (salience 0))
    ?r<-(racun)
    (test(eq (get ?r stanjeRacuna) (StanjeRacuna.PORUCENO)))
    =>
    (bind ?lst (get ?r primenjeniPopusti))
    (bind ?temp 0)
    (foreach ?x ?lst
		(bind ?temp (+ ?temp (get ?x  procenatUmanjenja))))
	(call ?r.OBJECT setProcenatUmanjenja ?temp)
    (bind ?temp (* (get ?s jedinicnaCena) (get ?s kolicinaKupnjeljihArtikala) (- 1 (call ?s.OBJECT getProcenatUmanjenja))))
    (call ?r.OBJECT setKonacnaCena ?temp)
    (bind ?profilKupca (get ?r kupac))
    (bind ?pragPotrosnje (get (get (get ?r kupac) kategorijaKupca) pragPotrosnje))
    (call ?profilKupca addNagradniBodovi (round(call ?pragPotrosnje dodelaNagradnihBodova ?temp)))
    )

;proveri da li je potrebno da se poruci jos artikala
(defrule proveriLagerArtikala
    ?a <- (artikal (minimalnoStanjeNaLageru ?ms) (brojnoStanje ?br&:(< ?br ?ms)))
    =>
    ; uradi nesto
    )

;(run)