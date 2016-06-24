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

import model.Artikal;
import model.ArtikalUKorpi;
import model.Korisnik;
import model.Racun;
import model.StanjeRacuna;
import model.StavkaRacuna;
import database.Database;

@Stateless
@Path("/services")
public class Rest {

	@EJB
	Database data;
	
	
	
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
		return data.getArtikli();
	}
	
	@POST
	@Path("/artikal/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public void azurirajArtikal(Artikal artikal, @QueryParam("kolicina")int kolicina){
		System.out.println(kolicina);
		System.out.println(artikal);
	}
	
	@GET
	@Path("/racun/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Racun> sviRacuni(){
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
	@Path("/racun/potvrda")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean obradiRacun(Racun racun, @QueryParam("bodovi")int bodovi){
		System.out.println(racun);
		racun.setStanjeRacuna(StanjeRacuna.USPESNO_REALIZOVANO);
		racun.setBrojPotrosenihBodova(bodovi);
		data.addRacun(racun);
		return true;
	}
	
	@GET
	@Path("/racun/pregled")
	@Produces(MediaType.APPLICATION_JSON)
	public Racun createRacun(){
		Racun racun = new Racun(""+data.getRacuni().size(), new Date(), null, 0, 0, 0, 0, 0);
		
		int i = 1;
		for (StavkaRacuna stavka : data.getKorpa().values()) {
			stavka.setRedniBrojStavke(i++);
			racun.addStavkaRacuna(stavka);
			racun.setOriginalnaUkupnaCena(racun.getOriginalnaUkupnaCena() + stavka.getKonacnaCena());
			racun.setKonacnaCena(racun.getOriginalnaUkupnaCena());
		}
		return racun;
	}
	
	@POST
	@Path("/racun/cancel")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean otkaziRacun(Racun racun){
		System.out.println(racun);
		return data.getRacuni().remove(racun);
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
