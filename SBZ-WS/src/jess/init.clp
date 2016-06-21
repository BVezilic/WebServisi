(import model.*)

(deftemplate korisnik
    (declare
        (slot-specific TRUE)
        (from-class Kosrisnik)
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

(bind ?ka1 (new KategorijaArtikla "001" nil "Sitna riba" 0.2))

(bind ?ka2 (new KategorijaArtikla "002" ?ka1 "Jos sitnija riba" 0.3))

(printout t (call ?ka1 toString) crlf)
(printout t (call ?ka2 toString) crlf)