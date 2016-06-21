package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfilKupca implements Serializable {

	private static final long serialVersionUID = -4118823700944757540L;

	private String adresaIsporuke;
	private double nagradniBodovi;
	private KategorijaKupca kategorijaKupca;
	private Korisnik korisnik; // dodato da bi moglo da bude unique
	private ArrayList<Racun> realizovaneKupovine = new ArrayList<Racun>();

	// KONSTRUKTORI
	public ProfilKupca(String adresaIsporuke, double nagradniBodovi,
			KategorijaKupca kategorijaKupca, Korisnik korisnik) {
		super();
		this.korisnik = korisnik;
		this.adresaIsporuke = adresaIsporuke;
		this.nagradniBodovi = nagradniBodovi;
		this.kategorijaKupca = kategorijaKupca;
	}

	public ProfilKupca(String adresaIsporuke, double nagradniBodovi,
			KategorijaKupca kategorijaKupca, Korisnik korisnik,
			ArrayList<Racun> realizovaneKupovine) {
		super();
		this.adresaIsporuke = adresaIsporuke;
		this.nagradniBodovi = nagradniBodovi;
		this.kategorijaKupca = kategorijaKupca;
		this.realizovaneKupovine = realizovaneKupovine;
	}

	public ProfilKupca() {
		super();
	}

	// GET && SET
	public boolean addRealizovanaKupovina(Racun racun) {
		for (Racun r : realizovaneKupovine) {
			if (r.getSifra().equals(racun.getSifra())) {
				return false;
			}
		}
		realizovaneKupovine.add(racun);
		return true;
	}

	public void removeRealizovanaKupovina(Racun racun) {
		realizovaneKupovine.remove(racun);
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public String getAdresaIsporuke() {
		return adresaIsporuke;
	}

	public void setAdresaIsporuke(String adresaIsporuke) {
		this.adresaIsporuke = adresaIsporuke;
	}

	public double getNagradniBodovi() {
		return nagradniBodovi;
	}

	public void setNagradniBodovi(double nagradniBodovi) {
		this.nagradniBodovi = nagradniBodovi;
	}

	public KategorijaKupca getKategorijaKupca() {
		return kategorijaKupca;
	}

	public void setKategorijaKupca(KategorijaKupca kategorijaKupca) {
		this.kategorijaKupca = kategorijaKupca;
	}

	public ArrayList<Racun> getRealizovaneKupovine() {
		return realizovaneKupovine;
	}

	public void setRealizovaneKupovine(ArrayList<Racun> realizovaneKupovine) {
		this.realizovaneKupovine = realizovaneKupovine;
	}

	@Override
	public String toString() {
		return "ProfilKupca [adresaIsporuke=" + adresaIsporuke
				+ ", nagradniBodovi=" + nagradniBodovi + ", kategorijaKupca="
				+ kategorijaKupca + ", realizovaneKupovine="
				+ realizovaneKupovine + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfilKupca other = (ProfilKupca) obj;
		if (adresaIsporuke == null) {
			if (other.adresaIsporuke != null)
				return false;
		} else if (!adresaIsporuke.equals(other.adresaIsporuke))
			return false;
		if (kategorijaKupca == null) {
			if (other.kategorijaKupca != null)
				return false;
		} else if (!kategorijaKupca.equals(other.kategorijaKupca))
			return false;
		if (korisnik == null) {
			if (other.korisnik != null)
				return false;
		} else if (!korisnik.equals(other.korisnik))
			return false;
		if (Double.doubleToLongBits(nagradniBodovi) != Double
				.doubleToLongBits(other.nagradniBodovi))
			return false;
		if (realizovaneKupovine == null) {
			if (other.realizovaneKupovine != null)
				return false;
		} else if (!realizovaneKupovine.equals(other.realizovaneKupovine))
			return false;
		return true;
	}

}
