package org.alkalus.scraper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.alkalus.FlightScraper;
import org.alkalus.browser.ScraperClient;
import org.alkalus.browser.ScraperClient.WebPage;
import org.alkalus.gui.MainFrame;
import org.alkalus.objects.AutoMap;
import org.alkalus.objects.Date;
import org.alkalus.objects.Date.Month;
import org.alkalus.objects.Logger;

import com.google.common.util.concurrent.MoreExecutors;

public class ScrapeManager {

	private static boolean mMomondo = false;
	private static boolean mSkyScanner = false;
	private static boolean mWebJet = false;

	public final String KEY_DEPARTURE_CODE;
	public final String KEY_ARRIVAL_CODE;
	public final Date DATE_SCAN_START;
	public final Date DATE_SCAN_END;
	
	private final boolean mExtendedScanning;
	
	private final ThreadPoolExecutor mMainThreadExecutor;
	private final ExecutorService mMainThreadSinglePool;
	private final ScrapeMainLogicTask mMainThreadTask;
	
	private ScraperClient mScraperWebClient;
	
	//Worker Pool and Executor
	public final ThreadPoolExecutor mWorkerThreadExecutor;
	public final ExecutorService mWorkerThreadPool;
	
	public ScrapeManager(String AIRPORT_CODE_DEPARTURE, String AIRPORT_CODE_ARRIVAL, Date aDepartureDate) {
		this(AIRPORT_CODE_DEPARTURE, AIRPORT_CODE_ARRIVAL, aDepartureDate, null);
	}
	
	public ScrapeManager(String AIRPORT_CODE_DEPARTURE, String AIRPORT_CODE_ARRIVAL, Date aDepartureDate, Date aFinalDate) {
		KEY_DEPARTURE_CODE = AIRPORT_CODE_DEPARTURE;
		KEY_ARRIVAL_CODE = AIRPORT_CODE_ARRIVAL;
		DATE_SCAN_START = aDepartureDate;
		DATE_SCAN_END = aFinalDate;
		
		//Generate the main thread and wait.		
		mMainThreadExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
		mMainThreadSinglePool = MoreExecutors.getExitingExecutorService(mMainThreadExecutor,  30, TimeUnit.SECONDS);
		mMainThreadTask = new ScrapeMainLogicTask(this);
		mMainThreadSinglePool.submit(mMainThreadTask);
		
		//Generate a Worker Pool and Executor
		mWorkerThreadExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		mWorkerThreadPool = MoreExecutors.getExitingExecutorService(mWorkerThreadExecutor,  1, TimeUnit.SECONDS);		
		
		Logger.INFO("Created a new Scraper.");
		Logger.INFO("Departing: "+AIRPORT_CODE_DEPARTURE);
		Logger.INFO("Arrival: "+AIRPORT_CODE_ARRIVAL);
		Logger.INFO("Departure Date: "+aDepartureDate.toString());
		if (aFinalDate != null && aFinalDate.day() != -1) {
			Logger.INFO("Scanning until: "+aFinalDate.toString());			
		}
		else {
			Logger.INFO("Single day search initiated.");
		}
		
		int aValidDates = areDatesValid(aDepartureDate, aFinalDate);
		if (aValidDates <= 0 || aValidDates == 3 || aValidDates == 5) {
			mExtendedScanning = false;
			close();
			return;
		}
		else if (aValidDates == 1) { // Only Valid Departure Date
			mExtendedScanning = false;			
		}
		else if (aValidDates == 4) { // Valid Dates but Number two is either invalid or in the past, let's disregard it
			mExtendedScanning = false;			
		}
		else if (aValidDates == 9) { // Both Dates are valid and the secondary is in the future
			mExtendedScanning = true;			
		}
		else {
			mExtendedScanning = false;
			close();
			return;			
		}
		

		/*
		 * Do things here~
		 */
		Logger.INFO("Initialising data to handle within Scraper Web Client. Mode: "+aValidDates);
		AutoMap<Date> g;	
		
		if (aValidDates == 1 || aValidDates == 4) {
			g = Date.getDatesBetweenXAndYInclusive(DATE_SCAN_START, DATE_SCAN_START);			
		}
		else if (aValidDates == 9) {
			g = Date.getDatesBetweenXAndYInclusive(DATE_SCAN_START, DATE_SCAN_END);			
		}
		else {
			close();
			return;				
		}	
		if (g.size() > 0) {			
			String mTempURL = "http://www.momondo.com.au";		
			
			WebPage temp = new WebPage(mTempURL);
			temp = null;
			if (temp != null) {
				MainFrame.getScrapeManager().mWorkerThreadPool.submit(temp);
			}
		}
		else {
			Logger.INFO("Did not find any Dates to scan.");
		}
		
		
	}
	
	public void close() {
		if (FlightScraper.getGUI().getWindow().isGuiLocked()) {
			FlightScraper.getGUI().getWindow().toggleLockGUI();
		}
		if (mMainThreadTask != null) {
			mMainThreadTask.setWaiting(false);
		}
		MainFrame.setScrapeManager(null);
	}
	
	public int areDatesValid(Date a0, Date a1) {
		boolean z0 = false, z1 = false, z2 = false;
		if (a0 != null && a0.isValid()) {
			z0 = true;
		}
		if (a1 == null) {
			a1 = a0;
		}
		if (a1 != null && a1.isValid()) {
			z1 = true;
		}
		if (z0 && z1) {
			if (
					a1.year() > a0.year() 
					|| (a1.year() == a0.year() && a1.month().ID() > a0.month().ID()) 
					|| (a1.year() == a0.year() && a1.month().ID() == a0.month().ID() && a1.day() > a0.day())) {
				z2 = true; // Means Date 2 is in the future
			}
		}
		
		int returner = 0;
		if (z0) {
			returner+=1;
		}
		if (z1) {
			returner+=3;
		}
		if (z2) {
			returner+=5;
		}
		return returner;
	}

	public synchronized final ScraperClient getWebClient() {
		return mScraperWebClient;
	}

	public synchronized final void setWebClient(ScraperClient mScraperWebClient) {
		this.mScraperWebClient = mScraperWebClient;
	}

}
