package rest;

import java.util.ArrayList;

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
import model.Racun;
import database.Database;

@Stateless
@Path("/services")
public class Rest {

	@EJB
	Database data;
	
	@GET
	@Path("/artikal/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Artikal> sviArtikli(){
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
	@Path("/racun/cancel")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean otkaziRacun(Racun racun){
		System.out.println(racun);
		return data.getRacuni().remove(racun);
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
