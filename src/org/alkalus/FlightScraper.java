package org.alkalus;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.alkalus.gui.MainFrame;
import org.alkalus.objects.Logger;
import org.alkalus.objects.XSTR;

public class FlightScraper  {

	/**
	 * The Local Instance of this program.
	 */
	private static final FlightScraper mInstance;	
	
	/**
	 * GUI Instance
	 */
	private final MainFrame mMainFrame;
	public static boolean mIsMainFrameVisible = false;

	/*
	 * Static Variables
	 */
	
	//Math Related
	public static final float PI = (float) Math.PI;
	public static volatile Random RANDOM = new XSTR();	
	private static final long KILL_TIME_IN_TICKS = 60 * 20; //Always multiply by 20 to account for ticks
	public static long mInternalTickTimer = 0;
	private static long mInternalTimer = 0;
	private static final long mStartTime;
	
	/*
	 * Instance Handling
	 */

	public static synchronized final FlightScraper getInstance() {
		return mInstance;
	}
	
	public static synchronized final MainFrame getGUI() {
		return getInstance().mMainFrame;
	}
	
	static {
		mInstance = new FlightScraper();
		mStartTime = System.currentTimeMillis();
	}
	
	public FlightScraper() {
		mMainFrame = new MainFrame();		
	}
	
	
	/**
	 * Main Program Loop
	 * @param args
	 */
	public static void main(String [ ] args){
		Logger.INFO("Starting Flight Scraper.");
		logic_Main();
	}
	
	/**
	 * Controls the flow of the program.
	 */
	public static void logic_Main() {
		//While GUI is open, lets sit in a loop.
		while (mIsMainFrameVisible && ((!getGUI().getWindow().getStopRunningIfTimeOut().isSelected()) || (getGUI().getWindow().getStopRunningIfTimeOut().isSelected() && mInternalTickTimer <= KILL_TIME_IN_TICKS))) {
			//If the GUI is not visible, lets just close.
			if (!mIsMainFrameVisible) {
				break;
			}			
			getInstance().tickTime();			
		}
		if (!mIsMainFrameVisible) {
			Logger.INFO("Main GUI has been closed, terminating. [0] "+getRunTime());
			System.exit(0);
		}
	}
	
	protected long seconds;
	protected int weeks;
	protected int days;
	protected long hours;
	protected long minutes;
	protected long second;
	private long currentMilli = 0, previousMilli = 0;
	
	/**
	 * Keeps track of time in Minecraft ticks, because it's something I am familiar with.
	 */
	private final void tickTime() {
		previousMilli = currentMilli;
		currentMilli = System.currentTimeMillis();		
		long mDiff = currentMilli - mStartTime;
		if (mDiff <= 0) {
			return;
		}
		else if (mDiff == 50) {
			mInternalTickTimer++;
		}		

		seconds = (mInternalTickTimer/20);
		weeks = (int) (TimeUnit.SECONDS.toDays(seconds) / 7);
		days = (int) (TimeUnit.SECONDS.toDays(seconds) - 7 * weeks);
		hours = TimeUnit.SECONDS.toHours(seconds) - TimeUnit.DAYS.toHours(days) - TimeUnit.DAYS.toHours(7*weeks);
		minutes = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
		second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);	
	}
	
	public static long getRunTimeAsSeconds() {
		return getInstance().seconds;
	}
	
	public static String getRunTime() {
		return ""+(Long.toString(getInstance().hours) + " Hours, " + Long.toString(getInstance().minutes) + " Minutes, " + Long.toString(getInstance().second) + " Seconds.");
	}

}
