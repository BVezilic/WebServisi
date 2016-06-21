package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Racun implements Serializable{
	
	private static final long serialVersionUID = -2165051061762547489L;
	
	private String sifra;
	private Date datumIzdavanja;
	private ProfilKupca kupac;
	private int brojOstvarenihBodova;
	private ArrayList<Popust> primenjeniPopusti = new ArrayList<Popust>();
	private ArrayList<StavkaRacuna> stavkeRacuna = new ArrayList<StavkaRacuna>();
	
	// KONSTRUKTORI
	public Racun(String sifra, Date datumIzdavanja, ProfilKupca kupac,
			int brojOstvarenihBodova, ArrayList<Popust> primenjeniPopusti,
			ArrayList<StavkaRacuna> stavkeRacuna) {
		super();
		this.sifra = sifra;
		this.datumIzdavanja = datumIzdavanja;
		this.kupac = kupac;
		this.brojOstvarenihBodova = brojOstvarenihBodova;
		this.primenjeniPopusti = primenjeniPopusti;
		this.stavkeRacuna = stavkeRacuna;
	}
	
	public Racun(String sifra, Date datumIzdavanja, ProfilKupca kupac,
			int brojOstvarenihBodova) {
		super();
		this.sifra = sifra;
		this.datumIzdavanja = datumIzdavanja;
		this.kupac = kupac;
		this.brojOstvarenihBodova = brojOstvarenihBodova;
	}
	
	public Racun() {
		super();
	}


	//GET && SET
	public boolean addPrimenjeniPopust(Popust popust){
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
	
	public boolean addStavkaRacuna(StavkaRacuna stavkaRacuna){
		for (StavkaRacuna sr : stavkeRacuna){
			if (sr.getRacun().getSifra().equals(stavkaRacuna.getRacun().getSifra()) && sr.getRedniBrojStavke() == stavkaRacuna.getRedniBrojStavke()){
				return false;
			}
		}
		stavkeRacuna.add(stavkaRacuna);
		return true;
	}
	
	public void removeStavkaRacuna(StavkaRacuna stavkaRacuna){
		stavkeRacuna.remove(stavkaRacuna);
	}
	
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public Date getDatumIzdavanja() {
		return datumIzdavanja;
	}
	public void setDatumIzdavanja(Date datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}
	public ProfilKupca getKupac() {
		return kupac;
	}
	public void setKupac(ProfilKupca kupac) {
		this.kupac = kupac;
	}
	public int getBrojOstvarenihBodova() {
		return brojOstvarenihBodova;
	}
	public void setBrojOstvarenihBodova(int brojOstvarenihBodova) {
		this.brojOstvarenihBodova = brojOstvarenihBodova;
	}
	public ArrayList<Popust> getPrimenjeniPopusti() {
		return primenjeniPopusti;
	}
	public void setPrimenjeniPopusti(ArrayList<Popust> primenjeniPopusti) {
		this.primenjeniPopusti = primenjeniPopusti;
	}
	public ArrayList<StavkaRacuna> getStavkeRacuna() {
		return stavkeRacuna;
	}
	public void setStavkeRacuna(ArrayList<StavkaRacuna> stavkeRacuna) {
		this.stavkeRacuna = stavkeRacuna;
	}

	@Override
	public String toString() {
		return "Racun [sifra=" + sifra + ", datumIzdavanja=" + datumIzdavanja
				+ ", kupac=" + kupac + ", brojOstvarenihBodova="
				+ brojOstvarenihBodova + ", primenjeniPopusti="
				+ primenjeniPopusti + ", stavkeRacuna=" + stavkeRacuna + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Racun other = (Racun) obj;
		if (brojOstvarenihBodova != other.brojOstvarenihBodova)
			return false;
		if (datumIzdavanja == null) {
			if (other.datumIzdavanja != null)
				return false;
		} else if (!datumIzdavanja.equals(other.datumIzdavanja))
			return false;
		if (kupac == null) {
			if (other.kupac != null)
				return false;
		} else if (!kupac.equals(other.kupac))
			return false;
		if (primenjeniPopusti == null) {
			if (other.primenjeniPopusti != null)
				return false;
		} else if (!primenjeniPopusti.equals(other.primenjeniPopusti))
			return false;
		if (sifra == null) {
			if (other.sifra != null)
				return false;
		} else if (!sifra.equals(other.sifra))
			return false;
		if (stavkeRacuna == null) {
			if (other.stavkeRacuna != null)
				return false;
		} else if (!stavkeRacuna.equals(other.stavkeRacuna))
			return false;
		return true;
	}
	
	
}
