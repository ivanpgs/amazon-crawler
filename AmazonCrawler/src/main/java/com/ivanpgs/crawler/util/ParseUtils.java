package com.ivanpgs.crawler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParseUtils {

	private static Log log = LogFactory.getLog(ParseUtils.class);

	/**
	 * Auxiliary method to parse a String and get the first decimal number
	 * within the String, that will be taken as the price of the item
	 * 
	 * @param priceToBeParsed
	 * @return the first decimal number within the String
	 */
	@SuppressWarnings("finally")
	public static Double parsePrice(String priceToBeParsed) {
		String price = null;
		Double dPrice = new Double(0.00);
		try {
			if ((null != priceToBeParsed)
					&& (!"".equals(priceToBeParsed.trim()))) {
				// Regular expression to get the
				Pattern p = Pattern.compile("[\\d\\.]+");
				Matcher m = p.matcher(priceToBeParsed);
				if (m.find()) {
					price = m.group();
				}
				dPrice = new Double(price);
			}
		} catch (Exception e) {
			log.error(e.getCause());
		} finally {
			return dPrice;
		}
	}

}
