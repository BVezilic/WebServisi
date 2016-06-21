package model;

import java.io.Serializable;
import java.util.Date;

public class Korisnik implements Serializable {

	private static final long serialVersionUID = 2548135526625129131L;
	
	private String korisnickoIme;
	private String ime;
	private String prezime;
	private String lozinka;
	private UlogaKorisnika ulogaKorisnika;
	private Date datumRegistrovanja;
	
	//KONSTRUKTORI
	public Korisnik(String korisnickoIme, String ime, String prezime,
			String lozinka, UlogaKorisnika ulogaKorisnika,
			Date datumRegistrovanja) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.ime = ime;
		this.prezime = prezime;
		this.lozinka = lozinka;
		this.ulogaKorisnika = ulogaKorisnika;
		this.datumRegistrovanja = datumRegistrovanja;
	}
	
	public Korisnik() {
		super();
	}

	//GET && SET
	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public UlogaKorisnika getUlogaKorisnika() {
		return ulogaKorisnika;
	}

	public void setUlogaKorisnika(UlogaKorisnika ulogaKorisnika) {
		this.ulogaKorisnika = ulogaKorisnika;
	}

	public Date getDatumRegistrovanja() {
		return datumRegistrovanja;
	}

	public void setDatumRegistrovanja(Date datumRegistrovanja) {
		this.datumRegistrovanja = datumRegistrovanja;
	}

	@Override
	public String toString() {
		return "Korisnik [korisnickoIme=" + korisnickoIme + ", ime=" + ime
				+ ", prezime=" + prezime + ", lozinka=" + lozinka
				+ ", ulogaKorisnika=" + ulogaKorisnika
				+ ", datumRegistrovanja=" + datumRegistrovanja + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Korisnik other = (Korisnik) obj;
		if (datumRegistrovanja == null) {
			if (other.datumRegistrovanja != null)
				return false;
		} else if (!datumRegistrovanja.equals(other.datumRegistrovanja))
			return false;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (korisnickoIme == null) {
			if (other.korisnickoIme != null)
				return false;
		} else if (!korisnickoIme.equals(other.korisnickoIme))
			return false;
		if (lozinka == null) {
			if (other.lozinka != null)
				return false;
		} else if (!lozinka.equals(other.lozinka))
			return false;
		if (prezime == null) {
			if (other.prezime != null)
				return false;
		} else if (!prezime.equals(other.prezime))
			return false;
		if (ulogaKorisnika != other.ulogaKorisnika)
			return false;
		return true;
	}
	
}
