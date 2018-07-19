package org.alkalus.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.alkalus.FlightScraper;
import org.alkalus.objects.Logger;

public class CloseButtonListener implements ActionListener {

	public void actionPerformed(ActionEvent event){
		Logger.INFO("Closing Program.");
		FlightScraper.mIsMainFrameVisible = false;
		FlightScraper.getGUI().getFrame().setVisible(false);
		FlightScraper.getGUI().getFrame().dispose();
	}
}
