(import model.*)
(import model.PragPotrosnje)

(deftemplate pragPotrosnje
    (declare 
        (slot-specific TRUE)
        (from-class PragPotrosnje)
        (include-variables TRUE)))