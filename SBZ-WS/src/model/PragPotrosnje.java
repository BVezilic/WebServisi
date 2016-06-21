package model;

import java.io.Serializable;

public class PragPotrosnje implements Serializable {
	
	private static final long serialVersionUID = 70594258524453153L;
	
	private double opsegPotrosnjeOd;
	private double opsegPotrosnjeDo;
	
	//KONSTRUKTORI
	public PragPotrosnje() {
		super();
	}

	public PragPotrosnje(double opsegPotrosnjeOd, double opsegPotrosnjeDo) {
		super();
		this.opsegPotrosnjeOd = opsegPotrosnjeOd;
		this.opsegPotrosnjeDo = opsegPotrosnjeDo;
	}
	
	// METODE
	public double dodelaNagradnihBodova(){
		
		//TODO: implementiraj
		return 0;
	}
	
	// GET && SET
	public double getOpsegPotrosnjeOd() {
		return opsegPotrosnjeOd;
	}

	public void setOpsegPotrosnjeOd(double opsegPotrosnjeOd) {
		this.opsegPotrosnjeOd = opsegPotrosnjeOd;
	}

	public double getOpsegPotrosnjeDo() {
		return opsegPotrosnjeDo;
	}

	public void setOpsegPotrosnjeDo(double opsegPotrosnjeDo) {
		this.opsegPotrosnjeDo = opsegPotrosnjeDo;
	}

	@Override
	public String toString() {
		return "PragPotrosnje [opsegPotrosnjeOd=" + opsegPotrosnjeOd
				+ ", opsegPotrosnjeDo=" + opsegPotrosnjeDo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PragPotrosnje other = (PragPotrosnje) obj;
		if (Double.doubleToLongBits(opsegPotrosnjeDo) != Double
				.doubleToLongBits(other.opsegPotrosnjeDo))
			return false;
		if (Double.doubleToLongBits(opsegPotrosnjeOd) != Double
				.doubleToLongBits(other.opsegPotrosnjeOd))
			return false;
		return true;
	}
	
}
