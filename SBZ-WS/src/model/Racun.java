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
	private double originalnaUkupnaCena;
	private double procenatUmanjenja;
	private double konacnaCena;
	private double brojPotrosenihBodova;
	private ArrayList<Popust> primenjeniPopusti = new ArrayList<Popust>();
	private ArrayList<StavkaRacuna> stavkeRacuna = new ArrayList<StavkaRacuna>();
	private StanjeRacuna stanjeRacuna;
	
	// KONSTRUKTORI
//	public Racun(String sifra, Date datumIzdavanja, ProfilKupca kupac,
//			int brojOstvarenihBodova, ArrayList<Popust> primenjeniPopusti,
//			ArrayList<StavkaRacuna> stavkeRacuna) {
//		super();
//		this.sifra = sifra;
//		this.datumIzdavanja = datumIzdavanja;
//		this.kupac = kupac;
//		this.brojOstvarenihBodova = brojOstvarenihBodova;
//		this.primenjeniPopusti = primenjeniPopusti;
//		this.stavkeRacuna = stavkeRacuna;
//	}
//	
//	public Racun(String sifra, Date datumIzdavanja, ProfilKupca kupac,
//			int brojOstvarenihBodova) {
//		super();
//		this.sifra = sifra;
//		this.datumIzdavanja = datumIzdavanja;
//		this.kupac = kupac;
//		this.brojOstvarenihBodova = brojOstvarenihBodova;
//	}
	
	
	public Racun() {
		super();
	}


	public Racun(String sifra, Date datumIzdavanja, ProfilKupca kupac,
			int brojOstvarenihBodova, double originalnaUkupnaCena,
			double procenatUmanjenja, double konacnaCena,
			double brojPotrosenihBodova) {
		super();
		this.sifra = sifra;
		this.datumIzdavanja = datumIzdavanja;
		this.kupac = kupac;
		this.brojOstvarenihBodova = brojOstvarenihBodova;
		this.originalnaUkupnaCena = originalnaUkupnaCena;
		this.procenatUmanjenja = procenatUmanjenja;
		this.konacnaCena = konacnaCena;
		this.brojPotrosenihBodova = brojPotrosenihBodova;
		this.stanjeRacuna = StanjeRacuna.PORUCENO;
	}


	public Racun(String sifra, Date datumIzdavanja, ProfilKupca kupac,
			int brojOstvarenihBodova, double originalnaUkupnaCena,
			double procenatUmanjenja, double konacnaCena,
			double brojPotrosenihBodova, ArrayList<Popust> primenjeniPopusti,
			ArrayList<StavkaRacuna> stavkeRacuna) {
		super();
		this.sifra = sifra;
		this.datumIzdavanja = datumIzdavanja;
		this.kupac = kupac;
		this.brojOstvarenihBodova = brojOstvarenihBodova;
		this.originalnaUkupnaCena = originalnaUkupnaCena;
		this.procenatUmanjenja = procenatUmanjenja;
		this.konacnaCena = konacnaCena;
		this.brojPotrosenihBodova = brojPotrosenihBodova;
		this.primenjeniPopusti = primenjeniPopusti;
		this.stavkeRacuna = stavkeRacuna;
		this.stanjeRacuna = StanjeRacuna.PORUCENO;
	}




	//GET && SET
	public boolean addPrimenjeniPopust(Popust popust){
		for (Popust p : primenjeniPopusti) {
			if (p.equals(popust)){
				return false;
			}
		}
		primenjeniPopusti.add(popust);
		return true;
	}
	
	public void addCena(double dodaj)
	{
		this.originalnaUkupnaCena += dodaj;
	}
	
	public void removePrimenjeniPopust(Popust popust){
		primenjeniPopusti.remove(popust);
	}
	
	public boolean addStavkaRacuna(StavkaRacuna stavkaRacuna){
		for (StavkaRacuna sr : stavkeRacuna){
			if (sr.equals(stavkaRacuna)){
				return false;
			}
		}
		stavkeRacuna.add(stavkaRacuna);
		return true;
	}
	
	public void removeStavkaRacuna(StavkaRacuna stavkaRacuna){
		stavkeRacuna.remove(stavkaRacuna);
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

	public double getBrojPotrosenihBodova() {
		return brojPotrosenihBodova;
	}

	public void setBrojPotrosenihBodova(double brojPotrosenihBodova) {
		this.brojPotrosenihBodova = brojPotrosenihBodova;
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
	public StanjeRacuna getStanjeRacuna() {
		return stanjeRacuna;
	}
	public void setStanjeRacuna(StanjeRacuna stanjeRacuna) {
		this.stanjeRacuna = stanjeRacuna;
	}


	@Override
	public String toString() {
		return "Racun [sifra=" + sifra + ", datumIzdavanja=" + datumIzdavanja
				+ ", brojOstvarenihBodova="
				+ brojOstvarenihBodova + ", primenjeniPopusti="
				+ primenjeniPopusti + ", originalnaUkupnaCena=" + originalnaUkupnaCena + "]";
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
		if (sifra == null) {
			if (other.sifra != null)
				return false;
		} else if (!sifra.equals(other.sifra))
			return false;
		return true;
	}
	
	
}
