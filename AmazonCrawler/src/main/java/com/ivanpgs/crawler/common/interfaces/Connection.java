package com.ivanpgs.crawler.common.interfaces;

public interface Connection {

	/**
	 * Method that receiving a url connect to the giving URL using an HTTP
	 * Connection
	 * 
	 * @param the
	 *            content of the url
	 * @return
	 */
	Object connect(String ulrToConnect);

}
