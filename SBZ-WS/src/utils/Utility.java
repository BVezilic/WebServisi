package utils;

import java.util.ArrayList;
import java.util.Date;

import model.Artikal;
import model.KategorijaArtikla;
import model.Racun;
import model.StavkaRacuna;

public class Utility {
	public static int dateDifference(Date date1, Date date2){
	
		return Math.abs((int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24)));
	
	}
	
	public static boolean isWithinDates(Date date, Date dateFrom, Date dateTo){
		return (date.after(dateFrom) && date.before(dateTo));
	}
	
	
	// funkcija za proveravanje da li je kupljen isti artikal u odredjenom opsegu
	public static boolean getArticlesInSpan(Racun stv, int opseg, Artikal a)
	{
		ArrayList<Artikal> retVal = new ArrayList<Artikal>();
		
		ArrayList<Racun> rcLst = stv.getKupac().getRealizovaneKupovine();
		
		for(Racun r: rcLst)
		{
			if(dateDifference(stv.getDatumIzdavanja(),r.getDatumIzdavanja()) < opseg)
			{
				for(StavkaRacuna s:r.getStavkeRacuna())
				{
					retVal.add(s.getArtikal());
				}
			}
		}
		
		return retVal.contains(a);
	}
	
	// funkcija za proveravanje da li je kupljen proizvod iz iste katerogije u odredjenom opsegu
	public static boolean getCatInSpan(Racun stv, int opseg, Artikal a)
	{
		ArrayList<KategorijaArtikla> retVal = new ArrayList<KategorijaArtikla>();
		
		ArrayList<Racun> rcLst = stv.getKupac().getRealizovaneKupovine();
		
		for(Racun r: rcLst)
		{
			if(dateDifference(stv.getDatumIzdavanja(),r.getDatumIzdavanja()) < opseg)
			{
				for(StavkaRacuna s:r.getStavkeRacuna())
				{
					retVal.add(s.getArtikal().getKategorijaArtikla());
					if(s.getArtikal().getKategorijaArtikla().getNadkategorija() != null)
					{
						retVal.add(s.getArtikal().getKategorijaArtikla().getNadkategorija());
					}
				}
			}
		}
		
		boolean ret = false;
		if(a.getKategorijaArtikla().getNadkategorija() != null)
		{
			ret = retVal.contains(a.getKategorijaArtikla()) || retVal.contains(a.getKategorijaArtikla().getNadkategorija());
		}else
		{
			ret = retVal.contains(a.getKategorijaArtikla());
		}
		return ret;
		
	}
	
}
