package rezoner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import jess.Fact;
import jess.Filter;
import jess.FilteringIterator;
import jess.JessException;
import jess.Rete;
import jess.WorkingMemoryMarker;
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
import utils.Utility;
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
	
	@SuppressWarnings("unchecked")
	public ArrayList<Artikal> replenishArticles()
	{
		ArrayList<Artikal> retVal = new ArrayList<Artikal>();
		Iterator<Fact> it = new FilteringIterator(engine.listFacts(), new Filter() {
			public boolean accept(Object arg0) {
				Fact temp = (Fact) arg0; 
				if(temp.getName().equals("MAIN::artikalAlarm"))
					return true;
				return false;
			}
		});
		
		while (it.hasNext()) {
        	Fact fa = (Fact) it.next();
        	try {
				String sifra = fa.getSlotValue("ID").toString();
				sifra = sifra.replace("\"", "");
				retVal.add(database.getArtikalBySifra(sifra));
			} catch (JessException e) {
				e.printStackTrace();
			}
		}
		return retVal;
	}
	
	public void setProfilKupca(Korisnik ko, ProfilKupca pk) {

		Korisnik k = database.getKorisnikByKorisnickoIme(ko.getKorisnickoIme());

		if (database.getProfilKupcaByKorisnickoIme(ko.getKorisnickoIme()) == null) {
			database.addProfilKupca(pk);
		}

		pk.setKorisnik(k);
		k.setProfilKupca(pk);

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
			//engine2.definstance("korisnik", data.getKorisnikByKorisnickoIme("kupac1"), false);
			UlogaKorisnika uk1 = UlogaKorisnika.KUPAC;
			PragPotrosnje pp1 = new PragPotrosnje(0, 10000);
			Korisnik ko1 = new Korisnik("kupac1", "Pera", "Peric", "p", uk1,
					parseDate("1/1/2015"));
			KategorijaKupca kk1 = new KategorijaKupca("kk1", "SIL",
					pp1);
			KategorijaArtikla ka1 = new KategorijaArtikla("ka1", null,
					"Laptopovi", 0.15);
			KategorijaArtikla ka3 = new KategorijaArtikla("ka3", null,
					"Skolski pribor", 0.15);
			KategorijaArtikla ka2 = new KategorijaArtikla("ka2", ka1,
					"Random", 0.31);
			ProfilKupca pk1 = new ProfilKupca(ko1, "Zeleznicka 1", 0, kk1);
			Artikal ar1 = new Artikal("ar1", "Laptop", ka2, 15000, 100, 50, parseDate("1/1/2014"), false, false);
			Artikal ar3 = new Artikal("ar1", "Laptop", ka2, 25, 100, 50, parseDate("1/1/2014"), false, false);
			Artikal ar2 = new Artikal("ar2", "AR2", ka2, 25, 100, 50, parseDate("1/1/2014"), false, false);
			
			StavkaRacuna str1 = new StavkaRacuna(null, 1, ar1, ar1.getCena(), 22, 5001, 0, 0);
			Racun r1 = new Racun("111", parseDate("21/08/2016"), pk1, 0, 0.0, 0.0, 0.0, 0.0);
			r1.setStanjeRacuna(StanjeRacuna.PORUCENO);
			str1.setRacun(r1);
			ArrayList<StavkaRacuna> temp = new ArrayList<StavkaRacuna>();
			
			StavkaRacuna str2 = new StavkaRacuna(null, 1, ar3, ar2.getCena(), 22, 0, 0, 0);
			
			Racun r2 = new Racun("123", parseDate("20/11/1999"), pk1, 0, 0.0, 0.0, 0.0, 0.0);
			
			str2.setRacun(r2);
			temp.add(str2);
			r2.setStavkeRacuna(temp);
			pk1.addRealizovanaKupovina(r2);
			r1.addStavkaRacuna(str1);
			
			Racun r3 = new Racun("123", parseDate("20/11/1999"), pk1, 0, 0.0, 0.0, 0.0, 0.0);
			r3.addStavkaRacuna(str1);
			str1.setRacun(r3);
			/*
			 * akcijski dogadjaji
			 */
			AkcijskiDogadjaj ad1 = new AkcijskiDogadjaj("ad1", "Leto",
					parseDate("21/06/2016"), parseDate("21/09/2016"), 0.20);
			ad1.addKategorijaArtiklaSaPopustima(ka1);
			System.out.println(Utility.isWithinDates(r1.getDatumIzdavanja(), ad1.getVaziOd(), ad1.getVaziDo()));
			/*
			 * akcijski dogadjaji
			 */
			//engine2.definstance("akcijskiDogadjaj", ad1, false);
			//engine2.definstance("stavka", str1, false);
			//engine2.definstance("racun", r3, false);
			Artikal ar4 = new Artikal("ar1", "Laptop", ka2, 25, 49, 50, parseDate("1/1/2014"), false, false);
			Artikal ar5 = new Artikal("ar2", "Laptop", ka2, 25, 42, 50, parseDate("1/1/2014"), false, false);
			Artikal ar6 = new Artikal("ar3", "Laptop", ka2, 25, 39, 50, parseDate("1/1/2014"), false, false);
			Artikal ar7 = new Artikal("ar4", "Laptop", ka2, 25, 12, 50, parseDate("1/1/2014"), false, false);
			Artikal ar8 = new Artikal("ar5", "Laptop", ka2, 25, 58, 50, parseDate("1/1/2014"), false, false);
			Artikal ar9 = new Artikal("ar6", "Laptop", ka2, 25, 100, 50, parseDate("1/1/2014"), false, false);
			Database database1 = new Database();
			System.out.println(database1.getArtikli().size());
			database1.addArtikal(ar4);
			database1.addArtikal(ar5);
			database1.addArtikal(ar6);
			database1.addArtikal(ar7);
			database1.addArtikal(ar8);
			database1.addArtikal(ar9);
			engine2.definstance("artikal", ar4, false);
			engine2.definstance("artikal", ar5, false);
			engine2.definstance("artikal", ar6, false);
			engine2.definstance("artikal", ar7, false);
			engine2.definstance("artikal", ar8, false);
			engine2.definstance("artikal", ar9, false);
			engine2.run();
			//Iterator<Fact> it = engine2.listFacts();
			
			Iterator<Fact> it = new FilteringIterator(engine2.listFacts(), new Filter() {
				
				public boolean accept(Object arg0) {
					Fact temp = (Fact) arg0; 
					//						kreiranje filtera za studente koji imaju ocene
					if(temp.getName().equals("MAIN::artikalAlarm"))
						return true;
					return false;
				}
			});
			ArrayList<Artikal> retVal = new ArrayList<Artikal>();
			while (it.hasNext()) {
//	        	Jess.Fact je java reprezentacija Jess fakta
	        	Fact fa = (Fact) it.next();
	        	//if(fa.getName().equals("MAIN::artikalAlarm"))
	        	//{
	        		//System.out.println(fa.getSlotValue("ID"));
	        	//System.out.println("Za fakt sa rednim brojem " + fa.getFactId() + " tipa " + fa.getName());
	        	String sifra = fa.getSlotValue("ID").toString();
	        	sifra = sifra.replace("\"", "");
	        	System.out.println(sifra);
				retVal.add(database1.getArtikalBySifra(sifra));
	        	//}
	        	//filtriraj fakte samo da se preuzimaju fakti iz templejta student
			}
			System.out.println(retVal.toString());
			//engine2.definstance("stavka", str1, false);
			//engine2.run();
			//System.out.println(r3.getOriginalnaUkupnaCena());
//			System.out.println("POPUST: " + r3.getProcenatUmanjenja());
//			System.out.println("KONACNA CENA: " + r3.getKonacnaCena());
//			System.out.println("DODAT POPUST ");
//			for(StavkaRacuna t: r1.getStavkeRacuna())
//			{
//			for(Popust pp : r3.getPrimenjeniPopusti())
//			{
//				System.out.println(pp);
//			}

		} catch (JessException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Date parseDate(String s) {
		try {
			return (new SimpleDateFormat("dd/MM/yyyy")).parse(s);
		} catch (ParseException e) {
			return null;
		}
	}
}
