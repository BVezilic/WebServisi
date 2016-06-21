package model;

import java.io.Serializable;

public class KategorijaArtikla implements Serializable {

	private static final long serialVersionUID = -545398219412336491L;
	
	private String sifraKategorije;
	private KategorijaArtikla nadkategorija;
	private String naziv;
	private double maksimalniDozvoljeniPopust; // u procentima
	
	//KONSTRUKTORI
	
	public KategorijaArtikla(String sifraKategorije,
			KategorijaArtikla nadkategorija, String naziv,
			double maksimalniDozvoljeniPopust) {
		super();
		this.sifraKategorije = sifraKategorije;
		this.nadkategorija = nadkategorija;
		this.naziv = naziv;
		this.maksimalniDozvoljeniPopust = maksimalniDozvoljeniPopust;
	}

	public KategorijaArtikla() {
		super();
	}

	
	// GET && SET
	
	public String getSifraKategorije() {
		return sifraKategorije;
	}

	public void setSifraKategorije(String sifraKategorije) {
		this.sifraKategorije = sifraKategorije;
	}

	public KategorijaArtikla getNadkategorija() {
		return nadkategorija;
	}

	public void setNadkategorija(KategorijaArtikla nadkategorija) {
		this.nadkategorija = nadkategorija;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public double getMaksimalniDozvoljeniPopust() {
		return maksimalniDozvoljeniPopust;
	}

	public void setMaksimalniDozvoljeniPopust(double maksimalniDozvoljeniPopust) {
		this.maksimalniDozvoljeniPopust = maksimalniDozvoljeniPopust;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KategorijaArtikla other = (KategorijaArtikla) obj;
		if (Double.doubleToLongBits(maksimalniDozvoljeniPopust) != Double
				.doubleToLongBits(other.maksimalniDozvoljeniPopust))
			return false;
		if (nadkategorija == null) {
			if (other.nadkategorija != null)
				return false;
		} else if (!nadkategorija.equals(other.nadkategorija))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (sifraKategorije == null) {
			if (other.sifraKategorije != null)
				return false;
		} else if (!sifraKategorije.equals(other.sifraKategorije))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KategorijaArtikla [sifraKategorije=" + sifraKategorije
				+ ", nadkategorija=" + nadkategorija + ", naziv=" + naziv
				+ ", maksimalniDozvoljeniPopust=" + maksimalniDozvoljeniPopust
				+ "]";
	}

	
	
	
	
}
