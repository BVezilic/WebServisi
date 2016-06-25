package model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Popust implements Serializable {
	
	private static final long serialVersionUID = 5113798612811616498L;
	protected static int brojPopusta = 0;
	
	protected String sifra;
	protected Racun racun;
	protected double procenatUmanjenja; //u procentima
	protected TipPopusta oznaka;
	
	//KONSTRUKTORI
	public Popust(String sifra, Racun racun, double procenatUmanjenja,
			TipPopusta oznaka) {
		super();
		this.sifra = sifra;
		this.racun = racun;
		this.procenatUmanjenja = procenatUmanjenja;
		this.oznaka = oznaka;
	}

	public Popust() {
		super();
	}
	
	//METODE
	public String postaviJednistvenuSifru(){
		
		String retVal = String.valueOf(brojPopusta++);
		setSifra(retVal);
		
		return retVal;
	}

	
	//GET && SET
	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	@JsonIgnore
	public Racun getRacun() {
		return racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}

	public double getProcenatUmanjenja() {
		return procenatUmanjenja;
	}

	public void setProcenatUmanjenja(double procenatUmanjenja) {
		this.procenatUmanjenja = procenatUmanjenja;
	}

	public TipPopusta getOznaka() {
		return oznaka;
	}

	public void setOznaka(TipPopusta oznaka) {
		this.oznaka = oznaka;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Popust other = (Popust) obj;
		if (oznaka != other.oznaka)
			return false;
		if (Double.doubleToLongBits(procenatUmanjenja) != Double
				.doubleToLongBits(other.procenatUmanjenja))
			return false;
		if (racun == null) {
			if (other.racun != null)
				return false;
		} else if (!racun.equals(other.racun))
			return false;
		if (sifra == null) {
			if (other.sifra != null)
				return false;
		} else if (!sifra.equals(other.sifra))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Popust [sifra=" + sifra + ", racun="
				+ ", procenatUmanjenja=" + procenatUmanjenja + ", oznaka="
				+ oznaka + "]";
	}
	
}
