package org.alkalus.scraper;

import org.alkalus.objects.Date;
import org.alkalus.objects.Date.Month;
import org.alkalus.objects.Logger;

public class Job implements Runnable {

	public final int DAY;
	public final Month MONTH;
	public final int YEAR;

	public final String DEPARTURE_AIRPORT_CODE;
	public final String ARRIVAL_AIRPORT_CODE;
	
	public Job(String aCode0, String aCode1, Date aDate) {
		DAY = aDate.day();
		MONTH = aDate.month();
		YEAR = aDate.year();
		DEPARTURE_AIRPORT_CODE = aCode0;
		ARRIVAL_AIRPORT_CODE = aCode1;
		Logger.INFO("Created Job ["+DEPARTURE_AIRPORT_CODE+"] -> ["+ARRIVAL_AIRPORT_CODE+"] @ "+aDate.toString());
	}
	
	@Override
	public void run() {
		
	}

}
