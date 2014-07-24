package com.ivanpgs.crawler.common;

import com.ivanpgs.crawler.common.interfaces.GenericProductsType;

/**
 * 
 * Very interesting articles on Java Enum types:
 * 
 * http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
 * http://javahowto.blogspot.jp/2006/10/custom-string-values-for-enum.html
 * http:/
 * /stackoverflow.com/questions/4936819/java-check-if-enum-contains-a-given
 * -string
 * http://stackoverflow.com/questions/2709593/why-would-an-enum-implement
 * -an-interface
 * 
 * Class to enumerate the different Amazon Product Type patterns in their web
 * site
 * 
 * @author Ivan Poza
 * 
 */

public enum AmazonProductType implements GenericProductsType {

	AMAZON_PRODUCT_TYPE_GP {
		@Override
		public String toString() {
			return "/gp/product/";
		}

		@Override
		public String[] getRelativeURLAndProductID(String fullProductURL,
				GenericProductsType productType) {
			String productID = null;
			String productTruncateRelativeURL = null;

			// TODO Need a Helper/Utils method or a regular expression to avoid
			// this multiple if
			int index = fullProductURL.indexOf(AMAZON_PRODUCT_TYPE_GP
					.toString());
			if (index != -1) {
				int indexProductIDEnd = fullProductURL.indexOf('?', index
						+ AMAZON_PRODUCT_TYPE_GP.toString().length());
				if (indexProductIDEnd == -1) {
					indexProductIDEnd = fullProductURL.indexOf('/', index
							+ AMAZON_PRODUCT_TYPE_GP.toString().length());
					if (indexProductIDEnd == -1) {
						indexProductIDEnd = fullProductURL.indexOf('&', index
								+ AMAZON_PRODUCT_TYPE_GP.toString().length());
						// If no token ? / & has been found
						if (indexProductIDEnd == -1) {
							indexProductIDEnd = fullProductURL.length();
						}

					}
				}

				productID = fullProductURL.substring(index
						+ AMAZON_PRODUCT_TYPE_GP.toString().length(),
						indexProductIDEnd);
				productTruncateRelativeURL = fullProductURL.substring(0,
						indexProductIDEnd);
			}
			;
			String[] productRelativeURLAndID = new String[2];
			productRelativeURLAndID[0] = productID;
			productRelativeURLAndID[1] = productTruncateRelativeURL;
			return productRelativeURLAndID;
		}
	},
	AMAZON_PRODUCT_TYPE_DP {
		@Override
		public String toString() {
			return "/dp/";
		}

		@Override
		public String[] getRelativeURLAndProductID(String fullProductURL,
				GenericProductsType productType) {
			String productID = null;
			String productTruncateRelativeURL = null;
			// TODO Need a Helper/Utils method or a regular expression to avoid
			// this multiple if
			int index = fullProductURL.indexOf(AMAZON_PRODUCT_TYPE_DP
					.toString());
			if (index != -1) {
				int indexProductIDEnd = fullProductURL.indexOf('?', index
						+ AMAZON_PRODUCT_TYPE_DP.toString().length());
				if (indexProductIDEnd == -1) {
					if (indexProductIDEnd == -1) {
						indexProductIDEnd = fullProductURL.indexOf('/', index
								+ AMAZON_PRODUCT_TYPE_DP.toString().length());
						if (indexProductIDEnd == -1) {
							indexProductIDEnd = fullProductURL.indexOf('&',
									index
											+ AMAZON_PRODUCT_TYPE_DP.toString()
													.length());
							// If no token ? / & has been found
							if (indexProductIDEnd == -1) {
								indexProductIDEnd = fullProductURL.length();
							}

						}
					}
				}
				productID = fullProductURL.substring(index
						+ AMAZON_PRODUCT_TYPE_DP.toString().length(),
						indexProductIDEnd);
				productTruncateRelativeURL = fullProductURL.substring(0,
						indexProductIDEnd);
			}
			String[] productRelativeURLAndID = new String[2];
			productRelativeURLAndID[0] = productID;
			productRelativeURLAndID[1] = productTruncateRelativeURL;
			return productRelativeURLAndID;
		}
	};

}