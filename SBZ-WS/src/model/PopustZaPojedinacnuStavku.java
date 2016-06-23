package model;

import java.io.Serializable;

public class PopustZaPojedinacnuStavku extends Popust implements Serializable{

	private static final long serialVersionUID = 2379537612191306866L;
	
	private StavkaRacuna stavkaRacuna;

	public PopustZaPojedinacnuStavku() {
		super();
	}

	public PopustZaPojedinacnuStavku(String sifra, Racun racun,
			double procenatUmanjenja, TipPopusta oznaka, StavkaRacuna stavkaRacuna) {
		super(sifra, racun, procenatUmanjenja, oznaka);
		this.stavkaRacuna = stavkaRacuna;
	}

	@Override
	public String toString() {
		return "PopustZaPojedinacnuStavku [stavkaRacuna=" + stavkaRacuna.getRedniBrojStavke() + 
				", procenatUmanjenja="+ procenatUmanjenja +"]";
	}

	public StavkaRacuna getStavkaRacuna() {
		return stavkaRacuna;
	}

	public void setStavkaRacuna(StavkaRacuna stavkaRacuna) {
		this.stavkaRacuna = stavkaRacuna;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PopustZaPojedinacnuStavku other = (PopustZaPojedinacnuStavku) obj;
		if (stavkaRacuna == null) {
			if (other.stavkaRacuna != null)
				return false;
		} else if (!stavkaRacuna.equals(other.stavkaRacuna))
			return false;
		return true;
	}
	
	
	
}
