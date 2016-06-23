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

import model.Artikal;
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
	@Path("/racun/cancel")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean otkaziRacun(Racun racun){
		System.out.println(racun);
		return data.getRacuni().remove(racun);
	}
	
}
