package org.alkalus.gui;

import javax.swing.JFrame;

import org.alkalus.FlightScraper;
import org.alkalus.scraper.ScrapeManager;

public class MainFrame {
   
   public static boolean mReady = false;
   
   private static ScrapeManager mScrapeManager;
   
   private final static MainFrameGUI window;
   
   static {
		window = new MainFrameGUI();
   }

   public MainFrame(){
		getFrame().setVisible(true);
		FlightScraper.mIsMainFrameVisible = true;   
   }
   
   public MainFrameGUI getWindow() {
	   return window;
   }
   
   public JFrame getFrame() {
	   return getWindow().frame_Grand_Sky_Scraper;
   }

public static synchronized final ScrapeManager getScrapeManager() {
	return mScrapeManager;
}

public static synchronized final void setScrapeManager(ScrapeManager aScrapeManager) {
	if (mScrapeManager == null) {
		MainFrame.mScrapeManager = aScrapeManager;
	}
}

}