package com.ivanpgs.crawler.common;

/**
 * 
 * Class to enumerate the different not found messages in the application. (As
 * it is not focused on the user side there is no need to have a ResourceBundle
 * yet)
 * 
 * @author Ivan Poza
 * 
 */

public enum NotFoundMessages {

	PRODUCT_DESCRIPTION_NOT_FOUND {
		@Override
		public String toString() {
			return "Description not found!";
		}
	},
	PRODUCT_TITLE_NOT_FOUND {
		@Override
		public String toString() {
			return "Title not found!";
		}
	};

}