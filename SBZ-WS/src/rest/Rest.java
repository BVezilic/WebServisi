package rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import rezoner.Rezoner;
import model.Korisnik;
import model.PragPotrosnje;

@Stateless
@Path("/engine")
public class Rest {

	
	@EJB
	Rezoner rezoner;
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test(){
		
		Korisnik ko1 = new Korisnik("kupac1", "Pera", "Peric", "p", null,null);
		
		ko1 = rezoner.userExists(ko1);
		System.out.println(ko1);
		
		rezoner.setupEngine();
		rezoner.dodajFact(ko1);
		rezoner.pokreniRezonovanje();
		
		return "Radi";
	}
}
