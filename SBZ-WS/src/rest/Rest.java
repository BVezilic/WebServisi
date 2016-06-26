package rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import model.AkcijskiDogadjaj;
import model.Artikal;
import model.KategorijaArtikla;
import model.KategorijaKupca;
import model.Korisnik;
import model.PopustZaPojedinacnuStavku;
import model.ProfilKupca;
import model.Racun;
import model.StanjeRacuna;
import model.StavkaRacuna;
import rezoner.Rezoner;
import utils.Utility;
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
			
			System.out.println(st.getKolicinaKupnjeljihArtikala());
			st.setKolicinaKupnjeljihArtikala(st.getKolicinaKupnjeljihArtikala()+kolicina);
			System.out.println(st.getKolicinaKupnjeljihArtikala());
			
			st.setOriginalnaUkupnaCena(st.getJedinicnaCena() * st.getKolicinaKupnjeljihArtikala());
			data.getKorpa().put(artikal.getSifra() , st);
		}else
		{	
			StavkaRacuna st = new StavkaRacuna(null, 0, artikal, artikal.getCena(), kolicina, kolicina * artikal.getCena(), 0, kolicina * artikal.getCena());
			data.getKorpa().put(artikal.getSifra(), st);
		}
		System.out.println("DODAO JE U KORPU IDUCE" + data.getKorpa().toString());
	}
	
	@GET
	@Path("/artikal/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Artikal> sviArtikli(){
		rezoner.replenishArticles();
		rezoner.removeAllFacts();
		return data.getArtikli();
	}
	
	@POST
	@Path("/artikal/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean azurirajArtikal(Artikal artikal, @QueryParam("kolicina")int kolicina){
		for (Artikal a : data.getArtikli()) {
			if (a.getSifra().equals(artikal.getSifra())) {
				a.setBrojnoStanje(a.getBrojnoStanje() + kolicina);
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
		Racun temp = data.getRacuni().get(data.getRacuni().indexOf(racun));
		for(StavkaRacuna sr: temp.getStavkeRacuna())
		{
			if(sr.getKolicinaKupnjeljihArtikala() > data.getArtikalBySifra(sr.getArtikal().getSifra()).getBrojnoStanje())
			{
				return false;
			}
		}
		for(StavkaRacuna sr: temp.getStavkeRacuna())
		{
			temp.setStanjeRacuna(StanjeRacuna.USPESNO_REALIZOVANO);
			temp.getKupac().addRealizovanaKupovina(temp);
			//temp.getKupac().setNagradniBodovi((int)(temp.getKupac().getNagradniBodovi() - temp.getBrojPotrosenihBodova()));
			temp.getKupac().addNagradniBodovi(temp.getBrojOstvarenihBodova());
			data.getArtikalBySifra(sr.getArtikal().getSifra()).setBrojnoStanje((int)(data.getArtikalBySifra(sr.getArtikal().getSifra()).getBrojnoStanje() - sr.getKolicinaKupnjeljihArtikala()));
		}
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
		if(data.getKorpa().values().size() == 0){
			return null;
		}
		
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
			stavka.setRacun(racun);
			racun.addStavkaRacuna(stavka);
			//racun.setOriginalnaUkupnaCena(racun.getOriginalnaUkupnaCena() + stavka.getKonacnaCena());
			//racun.setKonacnaCena(racun.getOriginalnaUkupnaCena());
		}	
		racun = rezoner.pokreniRezonerZaRacun(racun);		
		data.setRacunUPirpremi(racun);
		rezoner.removeAllFacts();
		System.out.println(racun);
		
		racun.izaberiNajboljiOsnovniPopust(); // brise sve popuste osim najboljeg
		System.out.println(racun);
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
		//System.out.println(racun);
		Racun temp = data.getRacuni().get(data.getRacuni().indexOf(racun));
		if(temp.getStanjeRacuna() == StanjeRacuna.PORUCENO)
		{
			temp.setStanjeRacuna(StanjeRacuna.OTKAZANO);
			temp.getKupac().addNagradniBodovi((int)temp.getBrojPotrosenihBodova());
			return true;
		}else
			return false;
		
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
		ArrayList<AkcijskiDogadjaj> retVal = new ArrayList<AkcijskiDogadjaj>();
		for(AkcijskiDogadjaj ad : data.getAkcijskiDogadjaji())
		{
			if(Utility.isWithinDates(new Date(), ad.getVaziOd(), ad.getVaziDo()))
				retVal.add(ad);
		}
		return retVal;
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
	public Response login(@QueryParam("korisnickoIme")String korisnickoIme, @QueryParam("lozinka")String lozinka) {
		ArrayList<Korisnik> korisnici = data.getKorisnici();
		for (Korisnik k : korisnici) {
			if (k.getKorisnickoIme().equals(korisnickoIme) && k.getLozinka().equals(lozinka)) {
				return Response.ok(k, MediaType.APPLICATION_JSON).header("Authorization", createJWT(korisnickoIme, lozinka, k.getUlogaKorisnika().toString())).build();
				//return createJWT(korisnickoIme, lozinka, k.getUlogaKorisnika().toString());
			}
		}
		return null;
	}
	
	private static String createJWT(String korisnickoIme, String lozinka, String uloga) {
		 
		//The JWT signature algorithm we will be using to sign the token
		Key key = MacProvider.generateKey();
		 
		 Claims claims = Jwts.claims().setSubject(korisnickoIme);
	        claims.put("korisnickoIme", korisnickoIme);
	        claims.put("lozinka", lozinka);
	        claims.put("role", uloga);
	        
		  //Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setClaims(claims)
		                                .signWith(SignatureAlgorithm.HS256, key);
		  
		 //Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
		}

}
