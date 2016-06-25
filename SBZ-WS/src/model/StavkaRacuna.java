package model;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oracle.webservices.internal.api.databinding.DatabindingMode;

import utils.Utility;

public class StavkaRacuna implements Serializable{

	private static final long serialVersionUID = 829924934788310063L;
	
	private Racun racun;
	private int redniBrojStavke;
	private Artikal artikal;
	private double jedinicnaCena;
	private int kolicinaKupnjeljihArtikala;
	private double originalnaUkupnaCena;
	private double procenatUmanjenja;
	private double konacnaCena;
	private ArrayList<PopustZaPojedinacnuStavku> primenjeniPopusti = new ArrayList<PopustZaPojedinacnuStavku>();
	
	// KONSTRUKTORI
	public StavkaRacuna(Racun racun, int redniBrojStavke, Artikal artikal,
			double jedinicnaCena, int kolicinaKupnjeljihArtikala,
			double originalnaUkupnaCena, double procenatUmanjenja,
			double konacnaCena, ArrayList<PopustZaPojedinacnuStavku> primenjeniPopusti) {
		super();
		this.racun = racun;
		this.redniBrojStavke = redniBrojStavke;
		this.artikal = artikal;
		this.jedinicnaCena = jedinicnaCena;
		this.kolicinaKupnjeljihArtikala = kolicinaKupnjeljihArtikala;
		this.originalnaUkupnaCena = originalnaUkupnaCena;
		this.procenatUmanjenja = procenatUmanjenja;
		this.konacnaCena = konacnaCena;
		this.primenjeniPopusti = primenjeniPopusti;
	}
	
	public StavkaRacuna(Racun racun, int redniBrojStavke, Artikal artikal,
			double jedinicnaCena, int kolicinaKupnjeljihArtikala,
			double originalnaUkupnaCena, double procenatUmanjenja,
			double konacnaCena) {
		super();
		this.racun = racun;
		this.redniBrojStavke = redniBrojStavke;
		this.artikal = artikal;
		this.jedinicnaCena = jedinicnaCena;
		this.kolicinaKupnjeljihArtikala = kolicinaKupnjeljihArtikala;
		this.originalnaUkupnaCena = originalnaUkupnaCena;
		this.procenatUmanjenja = procenatUmanjenja;
		this.konacnaCena = konacnaCena;
	}

	public StavkaRacuna() {
		super();
	}
	
	// GET && SET
	public boolean addPrimenjeniPopust(PopustZaPojedinacnuStavku popust){
		for (Popust p : primenjeniPopusti) {
			if (p.getSifra().equals(popust.getSifra())){
				return false;
			}
		}
		primenjeniPopusti.add(popust);
		return true;
	}
	
	public void removePrimenjeniPopust(Popust popust){
		primenjeniPopusti.remove(popust);
	}
	
	@JsonIgnore
	public Racun getRacun() {
		return racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}

	public int getRedniBrojStavke() {
		return redniBrojStavke;
	}

	public void setRedniBrojStavke(int redniBrojStavke) {
		this.redniBrojStavke = redniBrojStavke;
	}

	public Artikal getArtikal() {
		return artikal;
	}

	public void setArtikal(Artikal artikal) {
		this.artikal = artikal;
	}

	public double getJedinicnaCena() {
		return jedinicnaCena;
	}

	public void setJedinicnaCena(double jedinicnaCena) {
		this.jedinicnaCena = jedinicnaCena;
	}

	public int getKolicinaKupnjeljihArtikala() {
		return kolicinaKupnjeljihArtikala;
	}

	public void setKolicinaKupnjeljihArtikala(int kolicinaKupnjeljihArtikala) {
		this.kolicinaKupnjeljihArtikala = kolicinaKupnjeljihArtikala;
	}

	public double getOriginalnaUkupnaCena() {
		return originalnaUkupnaCena;
	}

	public void setOriginalnaUkupnaCena(double originalnaUkupnaCena) {
		this.originalnaUkupnaCena = originalnaUkupnaCena;
	}

	public double getProcenatUmanjenja() {
		return procenatUmanjenja;
	}

	public void setProcenatUmanjenja(double procenatUmanjenja) {
		this.procenatUmanjenja = procenatUmanjenja;
	}

	public double getKonacnaCena() {
		return konacnaCena;
	}

	public void setKonacnaCena(double konacnaCena) {
		this.konacnaCena = konacnaCena;
	}

	public ArrayList<PopustZaPojedinacnuStavku> getPrimenjeniPopusti() {
		return primenjeniPopusti;
	}

	public void setPrimenjeniPopusti(ArrayList<PopustZaPojedinacnuStavku> primenjeniPopusti) {
		this.primenjeniPopusti = primenjeniPopusti;
	}

	@Override
	public String toString() {
		return "StavkaRacuna [redniBrojStavke="
				+ redniBrojStavke + ", artikal=" + artikal + ", jedinicnaCena="
				+ jedinicnaCena + ", kolicinaKupnjeljihArtikala="
				+ kolicinaKupnjeljihArtikala + ", originalnaUkupnaCena="
				+ originalnaUkupnaCena + ", procenatUmanjenja="
				+ procenatUmanjenja + ", konacnaCena=" + konacnaCena
				+ ", primenjeniPopusti=" + primenjeniPopusti + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StavkaRacuna other = (StavkaRacuna) obj;
		if (artikal == null) {
			if (other.artikal != null)
				return false;
		} else if (!artikal.equals(other.artikal))
			return false;
		if (Double.doubleToLongBits(jedinicnaCena) != Double
				.doubleToLongBits(other.jedinicnaCena))
			return false;
		if (kolicinaKupnjeljihArtikala != other.kolicinaKupnjeljihArtikala)
			return false;
		if (Double.doubleToLongBits(konacnaCena) != Double
				.doubleToLongBits(other.konacnaCena))
			return false;
		if (Double.doubleToLongBits(originalnaUkupnaCena) != Double
				.doubleToLongBits(other.originalnaUkupnaCena))
			return false;
		if (primenjeniPopusti == null) {
			if (other.primenjeniPopusti != null)
				return false;
		} else if (!primenjeniPopusti.equals(other.primenjeniPopusti))
			return false;
		if (Double.doubleToLongBits(procenatUmanjenja) != Double
				.doubleToLongBits(other.procenatUmanjenja))
			return false;
		if (racun == null) {
			if (other.racun != null)
				return false;
		} else if (!racun.equals(other.racun))
			return false;
		if (redniBrojStavke != other.redniBrojStavke)
			return false;
		return true;
	}
	
	public boolean articlesInSpan(int opseg, Artikal ar)
	{
		ArrayList<Artikal> retVal = new ArrayList<Artikal>();
		
		ArrayList<Racun> rcLst = racun.getKupac().getRealizovaneKupovine();
		
		for(Racun r: rcLst)
		{
			if(Utility.dateDifference(racun.getDatumIzdavanja(),r.getDatumIzdavanja()) < opseg)
			{
				for(StavkaRacuna s:r.getStavkeRacuna())
				{
					retVal.add(s.getArtikal());
				}
			}
		}
		
		return retVal.contains(ar);
	}

	
}
