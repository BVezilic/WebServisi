package rezoner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import model.AkcijskiDogadjaj;
import model.Artikal;
import model.KategorijaArtikla;
import model.KategorijaKupca;
import model.Korisnik;
import model.Popust;
import model.PopustZaPojedinacnuStavku;
import model.PragPotrosnje;
import model.ProfilKupca;
import model.Racun;
import model.StanjeRacuna;
import model.StavkaRacuna;
import model.TipPopusta;
import model.UlogaKorisnika;
import jess.Filter;
import jess.JessException;
import jess.Rete;
import jess.WorkingMemoryMarker;
import database.Database;

@Singleton
public class Rezoner {

	@EJB
	Database database;

	public Rete engine = new Rete();
	private HashMap<String, WorkingMemoryMarker> markeri = new HashMap<String, WorkingMemoryMarker>();

	// Funkcije za rest

	/**
	 * Vraca null ako korisnik ne postoji, u suprotnom vraca korisnika iz baze
	 */
	public Korisnik userExists(Korisnik korisnik) {
		return database.korisnikExists(korisnik);
	}

	/**
	 * Vraca sve artikle iz baze
	 * 
	 * @return
	 */
	public ArrayList<Artikal> getAllArtikli() {
		return database.getArtikli();
	}

	/**
	 * Vraca sve artikle cija kategorija ili nadkategorija pripada prosledjenoj
	 * kategoriji
	 * 
	 * @param ka
	 * @return
	 */
	public ArrayList<Artikal> getAllArtikliByKategorija(KategorijaArtikla ka) {
		return database.getAllArtikliByKategorija(ka);
	}

	/**
	 * Postavlja polje profil u korisniku, i polje korisnik u profilu
	 * 
	 * @param ko
	 * @param pk
	 * @return
	 */
	public void setProfilKupca(Korisnik ko, ProfilKupca pk) {

		Korisnik k = database.getKorisnikByKorisnickoIme(ko.getKorisnickoIme());

		if (database.getProfilKupcaByKorisnickoIme(ko.getKorisnickoIme()) == null) {
			database.addProfilKupca(pk);
		}

		pk.setKorisnik(k);
		k.setProfilKupca(pk);

	}
	
	/**
	 * Sacuva bazu u fajl database/database.bin
	 */
	public void serializeToFile(){
		database.serializeToFile();
	}
	
	/**
	 * Ucitava bazu iz fajla database/database.bin
	 */
	public void loadFromFile(){
		database.readFromFile();
	}

	// Funkcije za Engine

	// DODAVANJE FAKATA
	/**
	 * Dodaje sve fakte koji su potrebni za korisnika
	 * 
	 * @param ko
	 */
	public void dodajSveFakteZaKorisnika(Korisnik ko) {

		//Korisnik ko = database.getKorisnikByKorisnickoIme(korisnickoIme);
		dodajFact(ko); // korisnik
		/*
		dodajFact(ko.getProfilKupca()); // profil kupca
		dodajFact(ko.getProfilKupca().getKategorijaKupca()); // kategorija kupca
		dodajFact(ko.getProfilKupca().getKategorijaKupca().getPragPotrosnje()); // prag potrosnje za kategoriju kupca

		for (Racun ra : ko.getProfilKupca().getRealizovaneKupovine()) {

			dodajFact(ra); // racun
			for (Popust pp : ra.getPrimenjeniPopusti()) {
				dodajFact(pp); // popust na racun
				dodajFact(pp.getOznaka()); // tip popusta
			}

			for (StavkaRacuna sr : ra.getStavkeRacuna()) {
				dodajFact(sr); // stavka racuna
				
				for (PopustZaPojedinacnuStavku ps : sr.getPrimenjeniPopusti()){
					dodajFact(ps); // popust za stavku
					dodajFact(ps.getOznaka()); //tip popusta
				}
				
				Artikal ar = sr.getArtikal();
				dodajFact(ar); // artikal
				dodajFact(ar.getKategorijaArtikla()); // kategorija artikla
				dodajFact(ar.getKategorijaArtikla().getNadkategorija()); //nadkategorija artikla
				
			}
			
			dodajFact(ra.getStanjeRacuna()); // stanje racuna

		}

		dodajFact(ko.getUlogaKorisnika());

		for(AkcijskiDogadjaj ad : database.getAkcijskiDogadjaji()){
			dodajFact(ad);
		}
		*/
		
		
	}

	public void dodajFact(AkcijskiDogadjaj ad) {
		try {
			engine.definstance("akcijskiDogadjaj", ad, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(Artikal ar) {
		try {
			engine.definstance("artikal", ar, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(KategorijaArtikla ka) {
		try {
			engine.definstance("kategorijaArtikla", ka, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(KategorijaKupca kk) {
		try {
			engine.definstance("kategorijaKupca", kk, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(Korisnik ko) {
		try {
			engine.definstance("korisnik", ko, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(Popust po) {
		try {
			engine.definstance("popust", po, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(PopustZaPojedinacnuStavku ps) {
		try {
			engine.definstance("popustZaPojedinacnuStavku", ps, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(PragPotrosnje pp) {
		try {
			engine.definstance("pragPotrosnje", pp, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(ProfilKupca pk) {
		try {
			engine.definstance("profilKupca", pk, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(Racun ra) {
		try {
			engine.definstance("racun", ra, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(StanjeRacuna sr) {
		try {
			engine.definstance("stanjeRacuna", sr, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(StavkaRacuna st) {
		try {
			engine.definstance("stavkaRacuna", st, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(TipPopusta tp) {
		try {
			engine.definstance("tipPopusta", tp, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void dodajFact(UlogaKorisnika uk) {
		try {
			engine.definstance("ulogaKorisnika", uk, false);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	// DODAVANJE FAKATA

	// OSTALO
	public void postaviMarker(String key) {
		// Mark end of catalog data for later
		if (markeri.containsKey(key)) {
			System.out.println("Vec postoji marker za" + key);
		} else {
			markeri.put(key, engine.mark());
		}
	}

	public void resetujNaMarker(String key) {
		// Remove any previous order data, leaving only catalog data
		if (!markeri.containsKey(key)) {
			System.out.println("Ne postoji marker " + key);
			return;
		} else {
			try {
				engine.resetToMark(markeri.get(key));
			} catch (JessException e) {
				e.printStackTrace();
			}
		}
	}

	public void pokreniRezonovanje() {
		try {
			engine.run();
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void postaviTemplejte(String putanja) {
		try {
			engine.batch(putanja);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void postaviPravila(String putanja) {
		try {
			engine.batch(putanja);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public void izvrsiNaredbuKaoTekst(String naredba) {
		try {
			engine.batch(naredba);
		} catch (JessException e) {
			e.printStackTrace();
		}
	}

	public List<Object> vratiRezultate(String className) {
		List<Object> lista = new ArrayList<Object>();
		Iterator<?> it;
		try {
			it = engine
					.getObjects(new Filter.ByClass(Class.forName(className)));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		while (it.hasNext()) {
			lista.add(it.next());
		}
		return lista;
	}

	/**
	 * Clears working memory and agenda
	 */
	public void removeAllFacts() {
		try {
			engine.clear();
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	
	public void setupEngine(){
		
		try {
			
			engine.reset();
			engine.eval("(watch all)");
			engine.batch("jess/rules.clp");
			
		} catch (JessException e) {
			e.printStackTrace();
		}
		
	}

	// TEST MAIN
	public static void main(String[] args) {
		
		
		try {
			Database data = new Database();
			
			Rete engine2 = new Rete();
			
			engine2.reset();
			engine2.eval("(watch all)");
			engine2.batch("jess/rules.clp");
			engine2.definstance("korisnik", data.getKorisnikByKorisnickoIme("kupac1"), false);
			engine2.run();
			

		} catch (JessException e) {
			e.printStackTrace();
		}
		
	}
}
