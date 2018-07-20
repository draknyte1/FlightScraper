package org.alkalus.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.alkalus.FlightScraper;
import org.alkalus.gui.MainFrame;
import org.alkalus.gui.MainFrameGUI;
import org.alkalus.objects.Date;
import org.alkalus.objects.Date.Month;
import org.alkalus.objects.Logger;
import org.alkalus.scraper.ScrapeManager;
import org.alkalus.util.Utils;

public class RunButtonListener implements ActionListener {
	private int numClicks = 0;

	public synchronized void actionPerformed(ActionEvent event){
		numClicks++;
		boolean r = verifyMainIsReady();
		if (r) {
			String a0, a1; // Airport Codes
			Date b0, b1 = new Date(-1, Month.BAD, -1); // Depart date and End date
			int c0, c1; // Day of leave and day of return
			int d0, d1; // year of leave and year of return
			Month e0, e1; // month of leave and month of return

			//Set Airport Codes
			a0 = FlightScraper.getGUI().getWindow().getField_Text_Departure_Code().getText();
			a1 = FlightScraper.getGUI().getWindow().getField_Text_Arrival_Code().getText();

			//Set departure Info
			c0 = Integer.valueOf(window().getField_Integer_Day_Start_Scan().getText());
			e0 = Utils.getMonthFromString((String) window().getCombo_Month_Start().getSelectedItem());
			d0 = Integer.valueOf((String) window().getCombo_Year_Start().getSelectedItem());
			b0 = new Date(c0, e0, d0);			

			//If using extended dates, set the final date to search up until.
			if (window().isUsingExtendedDates()) {
				Logger.INFO("Enabling Extended Search parameters.");
				c1 = Integer.valueOf(window().getField_Integer_Day_Finish_Scan().getText());
				e1 = Utils.getMonthFromString((String) window().getCombo_Month_Finish().getSelectedItem());
				d1 = Integer.valueOf((String) window().getCombo_Year_Finish().getSelectedItem());		
				b1 = new Date(c1, e1, d1);		
			}

			if (b0 != null) {
				if (!MainFrame.mReady) {
					MainFrame.setScrapeManager(new ScrapeManager(a0, a1, b0, b1));	
					MainFrame.mReady = true;				
					window().toggleLockGUI();
				}
			}			
		}
	}

	private MainFrameGUI window() {
		return FlightScraper.getGUI().getWindow();
	}


	private boolean verifyMainIsReady() {
		boolean a0 = false, a1 = false;

		Integer b0, b1;
		b0 = Integer.valueOf(window().getField_Integer_Day_Start_Scan().getText());
		b1 = Integer.valueOf(window().getField_Integer_Day_Finish_Scan().getText());
		Month c0, c1;
		c0 = Utils.getMonthFromString((String) window().getCombo_Month_Start().getSelectedItem());
		c1 = Utils.getMonthFromString((String) window().getCombo_Month_Finish().getSelectedItem());

		if (b0 != null && c0 != null) {
			if (Utils.isDayValidForMonth(b0, c0)) {
				a0 = true;
			}
		}
		else {
			if (b0 == null) {
				Logger.INFO("Day was not valid. 0");
			}
			if (c0 == null) {
				Logger.INFO("Month was not valid. 0");				
			}
		}
		if (b1 != null && c1 != null) {
			if (Utils.isDayValidForMonth(b1, c1)) {
				a1 = true;
			}
		}
		else {
			if (b1 == null) {
				Logger.INFO("Day was not valid. 1");			
			}
			if (c1 == null) {
				Logger.INFO("Month was not valid. 1");				
			}
		}

		Logger.INFO("Validating. Departure Valid? "+a0+" | Extended Valid? "+a1);


		return a0 && a1;
	}
}
