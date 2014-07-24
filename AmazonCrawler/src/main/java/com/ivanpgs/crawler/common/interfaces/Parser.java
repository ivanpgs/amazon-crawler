package com.ivanpgs.crawler.common.interfaces;

public interface Parser {

	/**
	 * Method that receiving a document within a Sting return a DOM Document
	 * object
	 * 
	 * @param documentToParse
	 *            - A string containing a parseable document
	 * @return
	 */
	Object parse(String documentToParse);

	/**
	 * Method that receiving a URL within connects to the URL, gets the content
	 * and parse it as DOM Document object
	 * 
	 * @param urlToParse
	 *            - The URL of the site to be parsed
	 * @return
	 */
	Object parseURL(String urlToParse);

}
