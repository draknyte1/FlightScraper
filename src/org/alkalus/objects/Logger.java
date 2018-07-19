package org.alkalus.objects;

import java.time.LocalTime;

import org.alkalus.util.Utils;

public class Logger {

	private static void log(final String z) {
		LocalTime localTime = LocalTime.now();
		String h, m, s;
		h = ""+localTime.getHour();
		m = ""+localTime.getMinute();
		s = ""+localTime.getSecond();

		h = Utils.leftPadWithZeroes(h, 2);
		m = Utils.leftPadWithZeroes(m, 2);
		s = Utils.leftPadWithZeroes(s, 2);
		
		
		String time = "["+h+":"+m+":"+s+"]";
		System.out.println(time+"[Flight Scraper]"+z);
	}
	
	// Non-Dev Comments
	public static void INFO(final String s) {
		log("[Info]"+s);
	}

	// Developer Comments
	public static void WARNING(final String s) {
		log("[Warning]"+s);
	}

	// Errors
	public static void ERROR(final String s) {
		log("[Error]"+s);
	}
}
