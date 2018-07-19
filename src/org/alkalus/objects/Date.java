package org.alkalus.objects;

public class Date {

	public static enum Month {
	    JANUARY(1),
	    FEBRUARY(2),
	    MARCH(3),
	    APRIL(4),
	    MAY(5),
	    JUNE(6),
	    JULY(7),
	    AUGUST(8),
	    SEPTEMBER(9),
	    OCTOBER(10),
	    NOVEMBER(11),
	    DECEMBER(12),
	    BAD(-1);
	    private int id;
	    Month(int url) {
	        this.id = url;
	    }
	    public int ID() {
	        return id;
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

	
}
