package com.ivanpgs.crawler.common;

// Amazon Product patterns
// http://javahowto.blogspot.jp/2006/10/custom-string-values-for-enum.html

public enum AmazonProductDescriptionAttribute {

	AMAZON_PRODUCT_ATTR_DESCRIPTION {
		@Override
		public String toString() {
			return "[id=postBodyPS]";
		}
	},
	AMAZON_PRODUCT_ATTR_DESCRIPTION_2 {
		@Override
		public String toString() {
			return "[class=productDescriptionWrapper]";
		}
	},
	AMAZON_PRODUCT_ATTR_DESCRIPTION_INSTANT_VIDEO {
		@Override
		public String toString() {
			return "[class=prod-synopsis]";
		}
	},
	AMAZON_PRODUCT_ATTR_DESCRIPTION_INSTANT_VIDEO_2 {
		@Override
		public String toString() {
			return "[class=dv-simple-synopsis dv-extender]";
		}
	};

}