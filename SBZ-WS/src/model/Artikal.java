package model;

import java.io.Serializable;
import java.util.Date;

public class Artikal implements Serializable{
	
	private static final long serialVersionUID = -6812695755695031113L;
	
	private String sifra;
	private String naziv;
	private KategorijaArtikla kategorijaArtikla;
	private double cena;
	private int brojnoStanje;
	private int minimalnoStanjeNaLageru;
	private Date datumKreiranjaZapisa;
	private boolean potrebnoPopunitiZalihe;
	private boolean statusZapisa; // aktivan ili arhiviran
	
	
	// KONSTRUKTORI
	public Artikal(String sifra, String naziv,
			KategorijaArtikla kategorijaArtikla, double cena, int brojnoStanje,
			int minimalnoStanjeNaLageru, Date datumKreiranjaZapisa,
			boolean potrebnoPopunitiZalihe, boolean statusZapisa) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.kategorijaArtikla = kategorijaArtikla;
		this.cena = cena;
		this.brojnoStanje = brojnoStanje;
		this.minimalnoStanjeNaLageru = minimalnoStanjeNaLageru;
		this.datumKreiranjaZapisa = datumKreiranjaZapisa;
		this.potrebnoPopunitiZalihe = potrebnoPopunitiZalihe;
		this.statusZapisa = statusZapisa;
	}
	
	public Artikal() {
		super();
	}

	//GET && SET
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
	public KategorijaArtikla getKategorijaArtikla() {
		return kategorijaArtikla;
	}
	public void setKategorijaArtikla(KategorijaArtikla kategorijaArtikla) {
		this.kategorijaArtikla = kategorijaArtikla;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public int getBrojnoStanje() {
		return brojnoStanje;
	}
	public void setBrojnoStanje(int brojnoStanje) {
		this.brojnoStanje = brojnoStanje;
	}
	public int getMinimalnoStanjeNaLageru() {
		return minimalnoStanjeNaLageru;
	}
	public void setMinimalnoStanjeNaLageru(int minimalnoStanjeNaLageru) {
		this.minimalnoStanjeNaLageru = minimalnoStanjeNaLageru;
	}
	public Date getDatumKreiranjaZapisa() {
		return datumKreiranjaZapisa;
	}
	public void setDatumKreiranjaZapisa(Date datumKreiranjaZapisa) {
		this.datumKreiranjaZapisa = datumKreiranjaZapisa;
	}
	public boolean isPotrebnoPopunitiZalihe() {
		return potrebnoPopunitiZalihe;
	}
	public void setPotrebnoPopunitiZalihe(boolean potrebnoPopunitiZalihe) {
		this.potrebnoPopunitiZalihe = potrebnoPopunitiZalihe;
	}
	public boolean isStatusZapisa() {
		return statusZapisa;
	}
	public void setStatusZapisa(boolean statusZapisa) {
		this.statusZapisa = statusZapisa;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artikal other = (Artikal) obj;
		if (brojnoStanje != other.brojnoStanje)
			return false;
		if (Double.doubleToLongBits(cena) != Double
				.doubleToLongBits(other.cena))
			return false;
		if (datumKreiranjaZapisa == null) {
			if (other.datumKreiranjaZapisa != null)
				return false;
		} else if (!datumKreiranjaZapisa.equals(other.datumKreiranjaZapisa))
			return false;
		if (kategorijaArtikla == null) {
			if (other.kategorijaArtikla != null)
				return false;
		} else if (!kategorijaArtikla.equals(other.kategorijaArtikla))
			return false;
		if (minimalnoStanjeNaLageru != other.minimalnoStanjeNaLageru)
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (potrebnoPopunitiZalihe != other.potrebnoPopunitiZalihe)
			return false;
		if (sifra == null) {
			if (other.sifra != null)
				return false;
		} else if (!sifra.equals(other.sifra))
			return false;
		if (statusZapisa != other.statusZapisa)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Artikal [sifra=" + sifra + ", naziv=" + naziv
				+ ", kategorijaArtikla=" + kategorijaArtikla + ", cena=" + cena
				+ ", brojnoStanje=" + brojnoStanje
				+ ", minimalnoStanjeNaLageru=" + minimalnoStanjeNaLageru
				+ ", datumKreiranjaZapisa=" + datumKreiranjaZapisa
				+ ", potrebnoPopunitiZalihe=" + potrebnoPopunitiZalihe
				+ ", statusZapisa=" + statusZapisa + "]";
	}
	
	
	public String nazivKategorije()
	{
		return kategorijaArtikla.getNaziv();
	}
	
	public String nazivNadKategorije()
	{
		if(kategorijaArtikla.getNadkategorija() != null)
			return kategorijaArtikla.getNadkategorija().getNaziv();
		else
			return "Prazno";
	}
}
