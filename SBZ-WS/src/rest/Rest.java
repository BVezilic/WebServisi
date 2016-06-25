package rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.AkcijskiDogadjaj;
import model.Artikal;
import model.KategorijaArtikla;
import model.KategorijaKupca;
import model.Korisnik;
import model.ProfilKupca;
import model.Racun;
import model.StanjeRacuna;
import model.StavkaRacuna;
import rezoner.Rezoner;
import database.Database;

@Stateless
@Path("/services")
public class Rest {

	@EJB
	Database data;
	
	@EJB
	Rezoner rezoner;
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test(){	
		rezoner.serializeToFile();		
		rezoner.loadFromFile();
		return "Radi";
	}
	
	@GET
	@Path("/korpa/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<StavkaRacuna> getKorpa(){
		return data.getKorpa().values();
	}
	
	@POST
	@Path("/korpa/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<StavkaRacuna> removeFromKorpa (Artikal artikal){
		data.getKorpa().remove(artikal.getSifra());
		return data.getKorpa().values();
	}
	
	@POST
	@Path("/korpa/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addToKorpa (Artikal artikal, @QueryParam("kolicina")int kolicina){
		if(data.getKorpa().containsKey(artikal.getSifra()))
		{
			StavkaRacuna st = data.getKorpa().get(artikal.getSifra());
			st.setKolicinaKupnjeljihArtikala(st.getKolicinaKupnjeljihArtikala()+kolicina);
			st.setKonacnaCena(st.getJedinicnaCena() * st.getKolicinaKupnjeljihArtikala());
			data.getKorpa().put(artikal.getSifra() , st);
		}else
		{	
			StavkaRacuna st = new StavkaRacuna(null, 0, artikal, artikal.getCena(), kolicina, kolicina * artikal.getCena(), 0, kolicina * artikal.getCena());
			data.getKorpa().put(artikal.getSifra(), st);
		}
		System.out.println(data.getKorpa().toString());
	}
	
	@GET
	@Path("/artikal/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Artikal> sviArtikli(){
		rezoner.replenishArticles();
		return data.getArtikli();
	}
	
	@POST
	@Path("/artikal/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean azurirajArtikal(Artikal artikal, @QueryParam("kolicina")int kolicina){
		for (Artikal a : data.getArtikli()) {
			if (a.getSifra().equals(artikal.getSifra())) {
				a.setBrojnoStanje(a.getBrojnoStanje() + kolicina);
				if(a.getBrojnoStanje() >= a.getMinimalnoStanjeNaLageru())
					a.setPotrebnoPopunitiZalihe(false);
				return true;
			}
		}
		return false;
	}
	
	@GET
	@Path("/racun/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Racun> sviRacuni(){
		System.out.println(data.getRacuni());
		return data.getRacuni();
	}
	
	@POST
	@Path("/racun/apply")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean obradiRacun(Racun racun){
		System.out.println(racun); 
		return true;
	}
	
	@POST
	@Path("/racun/getForKupac")
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Racun> getForKupac(Korisnik kupac){
		ArrayList<Racun> racuniKupca = new ArrayList<Racun>();
		System.out.println("Primljen ovaj parametar: "+kupac);
		
		for (Racun racun : data.getRacuni()) {
			if(racun.getKupac().getKorisnik() != null){
				System.out.println("korisnik u racunu nije null");
				if(racun.getKupac().getKorisnik().getKorisnickoIme().equals(kupac.getKorisnickoIme())){
					racuniKupca.add(racun);
				}
			}else{
				System.out.println("Dobio null za racun");
			}
		}

		return racuniKupca;
	}
	
	@POST
	@Path("/racun/potvrda")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean obradiRacun(Racun racun, @QueryParam("bodovi")int bodovi){
		System.out.println(racun);
		if(data.getRacunUPirpremi().getKupac().getNagradniBodovi() - bodovi < 0){
			return false;
		}
		
		data.getRacunUPirpremi().setStanjeRacuna(StanjeRacuna.PORUCENO);
		data.getRacunUPirpremi().setBrojPotrosenihBodova(bodovi);
		data.addRacun(data.getRacunUPirpremi());
		data.getRacunUPirpremi().getKupac().setNagradniBodovi(data.getRacunUPirpremi().getKupac().getNagradniBodovi() - bodovi);
		data.getKorpa().clear();
		return true;
	}
	
	@POST
	@Path("/racun/pregled")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Racun createRacun(Korisnik kupac){
		ProfilKupca kupacFromDB = new ProfilKupca();
		
		for (Korisnik korisnik : data.getKorisnici()) {
			if(korisnik.getKorisnickoIme().equals(kupac.getKorisnickoIme())){
				kupacFromDB = korisnik.getProfilKupca();
				break;
			}
		}
		
		Racun racun = new Racun(""+data.getRacuni().size(), new Date(), kupacFromDB, 0, 0, 0, 0, 0);
		
		int i = 1;
		for (StavkaRacuna stavka : data.getKorpa().values()) {
			if(stavka.getArtikal().getBrojnoStanje() <= stavka.getKolicinaKupnjeljihArtikala())
			{
				return null;
			}
			stavka.setRedniBrojStavke(i++);
			racun.addStavkaRacuna(stavka);
			racun.setOriginalnaUkupnaCena(racun.getOriginalnaUkupnaCena() + stavka.getKonacnaCena());
			racun.setKonacnaCena(racun.getOriginalnaUkupnaCena());
		}
		
		data.setRacunUPirpremi(racun);
		
		return racun;
	}
	
	@GET
	@Path("/kategorijeArtikala/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<KategorijaArtikla> getKategorijeArtikala(){
		return data.getKategorijeArtikla();
	}
	
	@POST
	@Path("/racun/cancel")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean otkaziRacun(Racun racun){
		System.out.println(racun);
		return data.removeRacun(racun);
	}
	
	@GET
	@Path("/kategorija/kupac/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<KategorijaKupca> sveKategorijeKupaca(){
		return data.getKategorijeKupca();
	}
	
	@POST
	@Path("/kategorija/kupac/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean azurirajKategorijuKupca(KategorijaKupca kategorija){
		for (KategorijaKupca kk : data.getKategorijeKupca()) {
			if (kk.getSifraKategorije().equals(kategorija.getSifraKategorije())) {
				kk.setPragPotrosnje(kategorija.getPragPotrosnje());;
				return true;
			}
		}
		return false;
	}
	
	@GET
	@Path("/kategorija/artikal/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<KategorijaArtikla> sveKategorijeArtikala(){
		return data.getKategorijeArtikla();
	}
	
	@POST
	@Path("/kategorija/artikal/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean dodajKategorijuArtikla(KategorijaArtikla kategorija){
		return data.addKategorijaArtikla(kategorija);
	}
	
	@POST
	@Path("/kategorija/artikal/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean azurirajKategorijuArtikla(KategorijaArtikla kategorija){
		for (KategorijaArtikla ka : data.getKategorijeArtikla()) {
			if (ka.getSifraKategorije().equals(kategorija.getSifraKategorije())) {
				ka.setMaksimalniDozvoljeniPopust(kategorija.getMaksimalniDozvoljeniPopust());
				ka.setNadkategorija(kategorija.getNadkategorija());
				ka.setNaziv(kategorija.getNaziv());
				return true;
			}
		}
		return false;
	}
	
	@GET
	@Path("/akcija/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<AkcijskiDogadjaj> sveAkcije(){
		return data.getAkcijskiDogadjaji();
	}
	
	@POST
	@Path("/akcija/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean dodajAkciju(AkcijskiDogadjaj akcijskiDogadjaj){
		System.out.println(akcijskiDogadjaj);
		return data.addAkcijskiDogadjaj(akcijskiDogadjaj);
	}
	
	@POST
	@Path("/akcija/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean azurirajAkciju(AkcijskiDogadjaj akcijskiDogadjaj){
		for (AkcijskiDogadjaj ad : data.getAkcijskiDogadjaji()) {
			if (ad.getSifra().equals(akcijskiDogadjaj.getSifra())) {
				ad.setNaziv(akcijskiDogadjaj.getNaziv());
				ad.setPopustZaDogadjaj(akcijskiDogadjaj.getPopustZaDogadjaj());
				ad.setVaziDo(akcijskiDogadjaj.getVaziDo());
				ad.setVaziOd(akcijskiDogadjaj.getVaziOd());
				ad.setKategorijaArtiklaSaPopustima(akcijskiDogadjaj.getKategorijaArtiklaSaPopustima());
				return true;
			}
		}
		return false;
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik login(@QueryParam("korisnickoIme")String korisnickoIme, @QueryParam("lozinka")String lozinka) {
		ArrayList<Korisnik> korisnici = data.getKorisnici();
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme) && k.getLozinka().equals(lozinka)) {
				return k;
			}
		}
		return null;
	}
}
