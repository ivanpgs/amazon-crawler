package com.ivanpgs.crawler.common;

// Amazon Price Attribute patterns
// http://javahowto.blogspot.jp/2006/10/custom-string-values-for-enum.html

public enum AmazonProductPriceAttribute {

	AMAZON_PRODUCT_ATTR_PRICE {
		@Override
		public String toString() {
			return "[class=priceLarge]";
		}
	},
	AMAZON_PRODUCT_ATTR_PRICE_KIT {
		@Override
		public String toString() {
			return "[class=priceLarge kitsunePrice]";
		}
	},
	AMAZON_PRODUCT_ATTR_PRICE_MP3_STORE {
		@Override
		public String toString() {
			return "[id=actualPriceValue]";
		}
	},
	AMAZON_PRODUCT_ATTR_DESCRIPTION_INSTANT_VIDEO_2 {
		@Override
		public String toString() {
			return "[value=PRODUCT_ID]";
		}
	};
}