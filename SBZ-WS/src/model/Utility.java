package model;

import java.util.Date;

public class Utility {
	public static int dateDifference(Date date1, Date date2){
	
		return (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24));
	
	}
	
	public static boolean isWithinDates(Date date, Date dateFrom, Date dateTo){
		return (date.after(dateFrom) && date.before(dateTo));
	}
}
