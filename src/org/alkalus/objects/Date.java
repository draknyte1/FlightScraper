package org.alkalus.objects;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.alkalus.util.Utils;

public class Date {

	public static final Map<Integer, Month> mInternalMonthMap = new HashMap<Integer, Month>();
	
	public static enum Month {
	    JANUARY(1, 12, 2),
	    FEBRUARY(2, 1, 3),
	    MARCH(3, 2, 4),
	    APRIL(4, 3, 5),
	    MAY(5, 4, 6),
	    JUNE(6, 5, 7),
	    JULY(7, 6, 8),
	    AUGUST(8, 7, 9),
	    SEPTEMBER(9, 8, 10),
	    OCTOBER(10, 9, 11),
	    NOVEMBER(11, 11, 12),
	    DECEMBER(12, 11, 1),
	    BAD(-1, -1, -1);
	    private int id;
	    private int prev;
	    private int post;
	    Month(int month, int prev, int post) {
	        this.id = month;
	        this.prev = prev;
	        this.post= post;
	        Date.mInternalMonthMap.put(month, this);
	    }
	    public int ID() {
	        return id;
	    }
	    
	    public Month getNextMonth() {
	    	return getMonth(post);
	    }
	    
	    public Month getLastMonth() {
	    	return getMonth(prev);
	    }
	    
	    public static Month getMonth(int month) {
	    	if (month <= 0 || month > 12) {
	    		month = -1;
	    	}
	    	return Date.mInternalMonthMap.get(month);
	    }
	}
	
	/*
	 * Static Vars
	 */	
	private final int DAY;
	private final Month MONTH;
	private final int YEAR;	
	
	public Date(int aDay, Month aMonth) {
		this(aDay, aMonth, 2018);
	}
	
	public Date(int aDay, Month aMonth, int aYear) {
		DAY = aDay;
		MONTH = aMonth;
		YEAR = aYear;
	}

	public synchronized final int day() {
		return DAY;
	}

	public synchronized final Month month() {
		return MONTH;
	}

	public synchronized final int year() {
		return YEAR;
	}
	
	public boolean isValid() {
		if (day() == -1 || month() == Month.BAD || year() == -1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Date) {
			if (((Date) arg0).YEAR == year()) {
				if (((Date) arg0).MONTH == month()) {
					if (((Date) arg0).DAY == day()) {
						return true;
					}	
				}	
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return ""+day()+"/"+month().ID()+"/"+year();
	}
	
	public static AutoMap<Date> getDatesBetweenXAndYInclusive(Date a1, Date a2){
		AutoMap<Date> mDates = new AutoMap<Date>();
		if (a1 != null && a2 != null) {
			if (a1.isValid() && a2.isValid()) {	
				Logger.INFO("Comparing Dates. ["+a1.toString()+"]["+a2.toString()+"]");			
				Calendar b1, b2;
				b1 = new GregorianCalendar(a1.year(),a1.month().ID()-1,a1.day());	
				b2 = new GregorianCalendar(a2.year(),a2.month().ID()-1,a2.day());

				Logger.INFO("Date 1: "+b1.getTime());
				Logger.INFO("Date 2: "+b2.getTime());
				long mEpochDiff = ChronoUnit.DAYS.between(b1.toInstant(), b2.toInstant());
				Logger.INFO("Looking at a range of "+mEpochDiff+" days. ["+mEpochDiff+"]");	
				
				//Iterate Days
				while (b1.before(b2)) {
			        java.util.Date result = b1.getTime();
			        int t0, t2;
					Month t1;
			        t0 = result.getDate();
			        t1 = Month.getMonth(result.getMonth()+1);
			        t2 = result.getYear()+1900;
			        
			        Date temp = new Date(t0, t1, t2);
			        mDates.put(temp);
			        Logger.INFO("Caching "+temp.toString());
			        b1.add(Calendar.DATE, 1);
			    }
				
			}
			else {
				Logger.INFO("One Date was not valid.");
			}
		}
		else {
			Logger.INFO("One Date was null.");
		}		
		return mDates;
	}

	
}
