package org.alkalus.util;

import static org.alkalus.objects.Date.Month.*;

import java.awt.Color;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import javax.xml.bind.DatatypeConverter;

import org.alkalus.objects.Date.Month;
import org.alkalus.objects.Logger;

public class Utils {

	/**
	 *
	 * @param colourStr
	 *            e.g. "#FFFFFF"
	 * @return String - formatted "rgb(0,0,0)"
	 */
	public static String hex2RgbFormatted(final String hexString) {
		final Color c = new Color(Integer.valueOf(hexString.substring(1, 3), 16),
				Integer.valueOf(hexString.substring(3, 5), 16), Integer.valueOf(hexString.substring(5, 7), 16));

		final StringBuffer sb = new StringBuffer();
		sb.append("rgb(");
		sb.append(c.getRed());
		sb.append(",");
		sb.append(c.getGreen());
		sb.append(",");
		sb.append(c.getBlue());
		sb.append(")");
		return sb.toString();
	}

	/**
	 *
	 * @param colourStr
	 *            e.g. "#FFFFFF"
	 * @return
	 */
	public static Color hex2Rgb(final String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	/**
	 *
	 * @param colourInt
	 *            e.g. 0XFFFFFF
	 * @return Colour
	 */
	public static Color hex2Rgb(final int colourInt) {
		return Color.decode(String.valueOf(colourInt));
	}

	/**
	 *
	 * @param colourInt
	 *            e.g. 0XFFFFFF
	 * @return short[]
	 */
	public static short[] hex2RgbShort(final int colourInt) {
		final Color rgb = Color.decode(String.valueOf(colourInt));
		final short[] rgba = { (short) rgb.getRed(), (short) rgb.getGreen(), (short) rgb.getBlue(),
				(short) rgb.getAlpha() };
		return rgba;
	}

	public static String byteToHex(final byte b) {
		final int i = b & 0xFF;
		return Integer.toHexString(i);
	}

	public static int rgbtoHexValue(final int r, final int g, final int b) {
		if ((r > 255) || (g > 255) || (b > 255) || (r < 0) || (g < 0) || (b < 0)) {
			return 0;
		}
		final Color c = new Color(r, g, b);
		String temp = Integer.toHexString(c.getRGB() & 0xFFFFFF).toUpperCase();

		// System.out.println( "hex: " + Integer.toHexString( c.getRGB() &
		// 0xFFFFFF ) + " hex value:"+temp);
		temp = Utils.appenedHexNotationToString(String.valueOf(temp));
		Logger.WARNING("Made " + temp + " - Hopefully it's not a mess.");
		Logger.WARNING("It will decode into " + Integer.decode(temp) + ".");
		return Integer.decode(temp);
	}

	/*
	 * http://javadevnotes.com/java-left-pad-string-with-zeros-examples
	 */
	public static String leftPadWithZeroes(final String originalString, final int length) {
		final StringBuilder sb = new StringBuilder();
		while ((sb.length() + originalString.length()) < length) {
			sb.append('0');
		}
		sb.append(originalString);
		final String paddedString = sb.toString();
		return paddedString;
	}

	/*
	 * Original Code by Chandana Napagoda -
	 * https://cnapagoda.blogspot.com.au/2011/03/java-hex-color-code-generator.
	 * html
	 */
	public static Map<Integer, String> hexColourGenerator(final int colorCount) {
		final int maxColorValue = 16777215;
		// this is decimal value of the "FFFFFF"
		final int devidedvalue = maxColorValue / colorCount;
		int countValue = 0;
		final HashMap<Integer, String> hexColorMap = new HashMap<>();
		for (int a = 0; (a < colorCount) && (maxColorValue >= countValue); a++) {
			if (a != 0) {
				countValue += devidedvalue;
				hexColorMap.put(a, Integer.toHexString(0x10000 | countValue).substring(1).toUpperCase());
			} else {
				hexColorMap.put(a, Integer.toHexString(0x10000 | countValue).substring(1).toUpperCase());
			}
		}
		return hexColorMap;
	}

	/*
	 * Original Code by Chandana Napagoda -
	 * https://cnapagoda.blogspot.com.au/2011/03/java-hex-color-code-generator.
	 * html
	 */
	public static Map<Integer, String> hexColourGeneratorRandom(final int colorCount) {
		final HashMap<Integer, String> hexColorMap = new HashMap<>();
		for (int a = 0; a < colorCount; a++) {
			String code = "" + (int) (Math.random() * 256);
			code = code + code + code;
			final int i = Integer.parseInt(code);
			hexColorMap.put(a, Integer.toHexString(0x1000000 | i).substring(1).toUpperCase());
			Logger.WARNING("" + Integer.toHexString(0x1000000 | i).substring(1).toUpperCase());
		}
		return hexColorMap;
	}

	public static String appenedHexNotationToString(final Object hexAsStringOrInt) {
		final String hexChar = "0x";
		String result;
		if (hexAsStringOrInt.getClass() == String.class) {

			if (((String) hexAsStringOrInt).length() != 6) {
				final String temp = leftPadWithZeroes((String) hexAsStringOrInt, 6);
				result = temp;
			}
			result = hexChar + hexAsStringOrInt;
			return result;
		} else if (hexAsStringOrInt.getClass() == Integer.class) {
			if (((String) hexAsStringOrInt).length() != 6) {
				final String temp = leftPadWithZeroes((String) hexAsStringOrInt, 6);
				result = temp;
			}
			result = hexChar + String.valueOf(hexAsStringOrInt);
			return result;
		} else {
			return null;
		}
	}

	public static Integer appenedHexNotationToInteger(final int hexAsStringOrInt) {
		final String hexChar = "0x";
		String result;
		Logger.WARNING(String.valueOf(hexAsStringOrInt));
		result = hexChar + String.valueOf(hexAsStringOrInt);
		return Integer.getInteger(result);
	}

	public static String sanitizeString(final String input) {
		String temp;
		String output;

		temp = input.replace(" ", "");
		temp = temp.replace("-", "");
		temp = temp.replace("_", "");
		temp = temp.replace("?", "");
		temp = temp.replace("!", "");
		temp = temp.replace("@", "");
		temp = temp.replace("#", "");
		temp = temp.replace("(", "");
		temp = temp.replace(")", "");
		temp = temp.replace("{", "");
		temp = temp.replace("}", "");
		temp = temp.replace("[", "");
		temp = temp.replace("]", "");
		temp = temp.replace(" ", "");
		output = temp;
		return output;

	}

	public static String sanitizeStringKeepBrackets(final String input) {
		String temp;
		String output;

		temp = input.replace(" ", "");
		temp = temp.replace("-", "");
		temp = temp.replace("_", "");
		temp = temp.replace("?", "");
		temp = temp.replace("!", "");
		temp = temp.replace("@", "");
		temp = temp.replace("#", "");
		temp = temp.replace(" ", "");
		output = temp;
		return output;
	}
	
	public static String removeNumeralsFromString(final String input) {
		String temp;
		String output;
		temp = input.replace("0", "");
		temp = temp.replace("1", "");
		temp = temp.replace("2", "");
		temp = temp.replace("3", "");
		temp = temp.replace("4", "");
		temp = temp.replace("5", "");
		temp = temp.replace("6", "");
		temp = temp.replace("7", "");
		temp = temp.replace("8", "");
		temp = temp.replace("9", "");
		output = temp;
		return output;
	}
	
	public static SecureRandom generateSecureRandom(){
		SecureRandom secRan;
		String secRanType;		
		
		if (SystemUtils.isWindows()){
			secRanType = "Windows-PRNG";
		}
		else {
			secRanType = "NativePRNG";
		}		
		try {
			secRan = SecureRandom.getInstance(secRanType);
			// Default constructor would have returned insecure SHA1PRNG algorithm, so make an explicit call.
			byte[] b = new byte[64] ;
			secRan.nextBytes(b);
			return secRan;
		}
		catch (NoSuchAlgorithmException e) {
			return null;
		} 
	}	
	

	public static String calculateChecksumMD5(Object bytes) {  
		byte[] result = new byte[] {};
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(bytes);
		  out.flush();
		  result = bos.toByteArray();
		}
		catch (IOException e) {
		} finally {
		    try {
				bos.close();
			}
			catch (IOException e) {}
		}		
		return calculateChecksumMD5(result);
	}
	
	public static String calculateChecksumMD5(byte[] bytes) {        
	    MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		    md.update(bytes);
		    byte[] digest = md.digest();
		    String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		    return myHash;
		}
		catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	public static Month getMonthFromString(String month) {
		if (month == null || month.length() <= 1) {
			return BAD;
		}
		if (month.toLowerCase().contains("jan")) {
			return JANUARY;
		}
		else if (month.toLowerCase().contains("feb")) {
			return FEBRUARY;
		}
		else if (month.toLowerCase().contains("mar")) {
			return MARCH;
		}
		else if (month.toLowerCase().contains("apr")) {
			return APRIL;
		}
		else if (month.toLowerCase().contains("may")) {
			return MAY;
		}
		else if (month.toLowerCase().contains("jun")) {
			return JUNE;
		}
		else if (month.toLowerCase().contains("jul")) {
			return JULY;
		}
		else if (month.toLowerCase().contains("aug")) {
			return AUGUST;
		}
		else if (month.toLowerCase().contains("sep")) {
			return SEPTEMBER;
		}
		else if (month.toLowerCase().contains("oct")) {
			return OCTOBER;
		}
		else if (month.toLowerCase().contains("nov")) {
			return NOVEMBER;
		}
		else if (month.toLowerCase().contains("dec")) {
			return DECEMBER;
		}
		else {
			return BAD;
		}	
	}
	
	public static boolean isDayValidForMonth(int day, Month month) {
		//Logger.INFO("Trying to check if a day is within a month. "+day+" / "+month.ID());
		if (day <= 0 || day > 31 || month == null) {
			return false;
		}
		if (month == FEBRUARY) {
			if (day > 0 && day <= 29) {
				//Logger.INFO("Found Feb.");
				return true;
			}
		} else if (month == SEPTEMBER || month == APRIL || month == JUNE || month == NOVEMBER) {
			if (day > 0 && day <= 30) {
				//Logger.INFO("Found 30 day month.");
				return true;
			}
		} else if (month == JANUARY || month == MARCH || month == MAY || month == JULY || month == AUGUST
				|| month == OCTOBER || month == DECEMBER) {
			if (day > 0 && day <= 31) {
				//Logger.INFO("Found 31 day month.");
				return true;
			}
		}
		//Logger.INFO("Bad day for month.");
		return false;
	}
	

}
