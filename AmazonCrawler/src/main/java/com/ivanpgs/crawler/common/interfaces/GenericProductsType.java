package com.ivanpgs.crawler.common.interfaces;

/**
 * 
 * Basic interface representing the existing BasicElement product types
 * 
 * @author Ivan Poza
 * 
 */

public interface GenericProductsType {

	/**
	 * Method that given a relative URL referring to a product gets a truncated
	 * URL getting rid of the not necessary information<BR/>
	 * and also the unique identifier of the product.<BR/>
	 * This helps avoid different URLs belonging to the same product not to be
	 * put into/ added to the Product Queue.
	 * 
	 * @param fullProductURL
	 *            - The URL of the product
	 * 
	 * @return a String containing an array with a truncated product relative
	 *         URL (0) and an unique identifier of the product (1)
	 */
	String[] getRelativeURLAndProductID(String fullProductURL,
			GenericProductsType productType);
	// String getProductID (String fullProductURL);
	// String getRelativeURL (String fullProductURL);
}