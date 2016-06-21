package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class AkcijskiDogadjaj implements Serializable {
	
	private static final long serialVersionUID = 3847844314539051353L;
	
	private String sifra;
	private String naziv;
	private Date vaziOd;
	private Date vaziDo;
	private double popustZaDogadjaj; // u procentima
	private ArrayList<KategorijaArtikla> kategorijaArtiklaSaPopustima = new ArrayList<KategorijaArtikla>();
	
	
	// KONSTRUKTORI
	public AkcijskiDogadjaj(String sifra, String naziv, Date vaziOd,
			Date vaziDo, double popustZaDogadjaj,
			ArrayList<KategorijaArtikla> kategorijaArtiklaSaPopustima) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.vaziOd = vaziOd;
		this.vaziDo = vaziDo;
		this.popustZaDogadjaj = popustZaDogadjaj;
		this.kategorijaArtiklaSaPopustima = kategorijaArtiklaSaPopustima;
	}
	
	public AkcijskiDogadjaj(String sifra, String naziv, Date vaziOd,
			Date vaziDo, double popustZaDogadjaj) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.vaziOd = vaziOd;
		this.vaziDo = vaziDo;
		this.popustZaDogadjaj = popustZaDogadjaj;
		this.kategorijaArtiklaSaPopustima = new ArrayList<KategorijaArtikla>();
	}
	
	
	public AkcijskiDogadjaj() {
		super();
	}


	// GET && SET
	public boolean addKategorijaArtiklaSaPopustima(KategorijaArtikla kategorijaArtikla){
		for (KategorijaArtikla ka : kategorijaArtiklaSaPopustima) {
			if (ka.equals(kategorijaArtikla)){
				return false;
			}
		}
		kategorijaArtiklaSaPopustima.add(kategorijaArtikla);
		return true;
	}
	
	public void removeKategorijaArtiklaSaPopustima(KategorijaArtikla kategorijaArtikla){
		kategorijaArtiklaSaPopustima.remove(kategorijaArtikla);
	}
	
	public boolean containsKategorija (KategorijaArtikla kategorijaArtikla){
		return kategorijaArtiklaSaPopustima.contains(kategorijaArtikla);
	}
	
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public Date getVaziOd() {
		return vaziOd;
	}
	public void setVaziOd(Date vaziOd) {
		this.vaziOd = vaziOd;
	}
	public Date getVaziDo() {
		return vaziDo;
	}
	public void setVaziDo(Date vaziDo) {
		this.vaziDo = vaziDo;
	}
	public double getPopustZaDogadjaj() {
		return popustZaDogadjaj;
	}
	public void setPopustZaDogadjaj(double popustZaDogadjaj) {
		this.popustZaDogadjaj = popustZaDogadjaj;
	}
	public ArrayList<KategorijaArtikla> getKategorijaArtiklaSaPopustima() {
		return kategorijaArtiklaSaPopustima;
	}
	public void setKategorijaArtiklaSaPopustima(
			ArrayList<KategorijaArtikla> kategorijaArtiklaSaPopustima) {
		this.kategorijaArtiklaSaPopustima = kategorijaArtiklaSaPopustima;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AkcijskiDogadjaj other = (AkcijskiDogadjaj) obj;
		if (kategorijaArtiklaSaPopustima == null) {
			if (other.kategorijaArtiklaSaPopustima != null)
				return false;
		} else if (!kategorijaArtiklaSaPopustima
				.equals(other.kategorijaArtiklaSaPopustima))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (Double.doubleToLongBits(popustZaDogadjaj) != Double
				.doubleToLongBits(other.popustZaDogadjaj))
			return false;
		if (sifra == null) {
			if (other.sifra != null)
				return false;
		} else if (!sifra.equals(other.sifra))
			return false;
		if (vaziDo == null) {
			if (other.vaziDo != null)
				return false;
		} else if (!vaziDo.equals(other.vaziDo))
			return false;
		if (vaziOd == null) {
			if (other.vaziOd != null)
				return false;
		} else if (!vaziOd.equals(other.vaziOd))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AkcijskiDogadjaj [sifra=" + sifra + ", naziv=" + naziv
				+ ", vaziOd=" + vaziOd + ", vaziDo=" + vaziDo
				+ ", popustZaDogadjaj=" + popustZaDogadjaj
				+ ", kategorijaArtiklaSaPopustima="
				+ kategorijaArtiklaSaPopustima + "]";
	}
	
	
	
	
}
