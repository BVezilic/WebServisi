package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy");
		
		Date d1 = new Date();
		Date d2 = new Date();
		Date date = new Date();
		try {
			 d1 = formatter.parse("28.12.2015");
			 d2 = formatter.parse("05.01.2016");
			 date = formatter.parse("03.01.2016");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(Utility.isWithinDates(date, d1, d2));
	}

}
