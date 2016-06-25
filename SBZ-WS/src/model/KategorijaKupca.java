package model;

import java.io.Serializable;

public class KategorijaKupca implements Serializable {
	
	private static final long serialVersionUID = -233306176288927403L;
	
	private String sifraKategorije;
	private String nazivKategorije;
	private PragPotrosnje pragPotrosnje;
	
	//KONSTRUKTORI
	public KategorijaKupca(String sifraKategorije, String nazivKategorije,
			PragPotrosnje pragPotrosnje) {
		super();
		this.sifraKategorije = sifraKategorije;
		this.nazivKategorije = nazivKategorije;
		this.pragPotrosnje = pragPotrosnje;
	}

	public KategorijaKupca() {
		super();
	}

	
	//GET && SET
	public String getSifraKategorije() {
		return sifraKategorije;
	}

	public void setSifraKategorije(String sifraKategorije) {
		this.sifraKategorije = sifraKategorije;
	}

	public String getNazivKategorije() {
		return nazivKategorije;
	}

	public void setNazivKategorije(String nazivKategorije) {
		this.nazivKategorije = nazivKategorije;
	}

	public PragPotrosnje getPragPotrosnje() {
		return pragPotrosnje;
	}

	public void setPragPotrosnje(PragPotrosnje pragPotrosnje) {
		this.pragPotrosnje = pragPotrosnje;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nazivKategorije == null) ? 0 : nazivKategorije.hashCode());
		result = prime * result
				+ ((pragPotrosnje == null) ? 0 : pragPotrosnje.hashCode());
		result = prime * result
				+ ((sifraKategorije == null) ? 0 : sifraKategorije.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KategorijaKupca other = (KategorijaKupca) obj;
		if (nazivKategorije == null) {
			if (other.nazivKategorije != null)
				return false;
		} else if (!nazivKategorije.equals(other.nazivKategorije))
			return false;
		if (pragPotrosnje == null) {
			if (other.pragPotrosnje != null)
				return false;
		} else if (!pragPotrosnje.equals(other.pragPotrosnje))
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
		return "KategorijaKupca [sifraKategorije=" + sifraKategorije
				+ ", nazivKategorije=" + nazivKategorije + ", pragPotrosnje="
				+ pragPotrosnje + "]";
	}

	
	
}
