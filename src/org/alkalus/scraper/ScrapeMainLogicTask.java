package org.alkalus.scraper;

import javax.swing.JProgressBar;

import org.alkalus.FlightScraper;
import org.alkalus.browser.ScraperClient;
import org.alkalus.gui.MainFrame;
import org.alkalus.objects.Logger;

public class ScrapeMainLogicTask implements Runnable {


	private boolean mIsWaiting = true;
	private final long mStartWaitTime;
	private final ScrapeManager parent;
	
	public ScrapeMainLogicTask(ScrapeManager scrapeManager) {
		mStartWaitTime = System.currentTimeMillis()/1000;
		parent = scrapeManager;
	}
	
	@Override
	public void run() {
		Logger.INFO("Created the main logic thread. Waiting to init.");
		while (mIsWaiting) {
			
			if (!mIsWaiting || ((System.currentTimeMillis()/1000)-mStartWaitTime >= 60)) {
				Logger.INFO("Waited long enough, let's continue");
				break;
			}
			
			if (FlightScraper.mInternalTickTimer % 20 == 0) {
				long progress = (System.currentTimeMillis()/1000)-mStartWaitTime;
				double perc = progress/60d;
				if (perc <= 1d) {
					JProgressBar d = FlightScraper.getGUI().getWindow().getProgressBar();
					d.setValue((int) perc*100);
					d.repaint();
					FlightScraper.getGUI().getWindow().setProgressBar(d);
				}
				if ((perc*100) >= 10) {
					if (parent.getWebClient() == null) {
						parent.setWebClient(new ScraperClient());
					}
					else {
						Logger.INFO("Created Web Client.");
						setWaiting(false);
					}
				}
			}
		}
		
		
		FlightScraper.getGUI().getWindow().getProgressBar().setValue((int) 0);
		FlightScraper.getGUI().getWindow().getProgressBar().repaint();
		
		if (!mIsWaiting) {
			Logger.INFO("Setting up web client.");
		}
		
		while (!mIsWaiting) {			
			
			//Main Thread Loop Starts here
			
			//Check for exit first
			if (!MainFrame.mReady || !FlightScraper.mIsMainFrameVisible) {
				Logger.INFO("mReady: "+MainFrame.mReady+" | mIsMainFrameVisible: "+FlightScraper.mIsMainFrameVisible);
				close();
				break;
			}
			
			//Update the Progress bar
			FlightScraper.getGUI().getWindow().getProgressBar().setIndeterminate(true);
			FlightScraper.getGUI().getWindow().getProgressBar().repaint();
			
			/*
			 * Do things here~
			 */
			
		}		
	}

	public synchronized final boolean isWaiting() {
		return mIsWaiting;
	}

	public synchronized final void setWaiting(boolean mIsWaiting) {
		this.mIsWaiting = mIsWaiting;
	}	
	
	private void close() {
		if (FlightScraper.getGUI().getWindow().isGuiLocked()) {
			FlightScraper.getGUI().getWindow().toggleLockGUI();
			MainFrame.mReady = false;
			try {
				this.finalize();
			} catch (Throwable e) {}
		}
	}
	
}
