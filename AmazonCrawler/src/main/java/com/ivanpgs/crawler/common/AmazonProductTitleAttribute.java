package com.ivanpgs.crawler.common;

// Amazon Category patterns
// http://javahowto.blogspot.jp/2006/10/custom-string-values-for-enum.html

public enum AmazonProductTitleAttribute {

	AMAZON_PRODUCT_ATTR_TITLE {
		@Override
		public String toString() {
			return "[id=btAsinTitle]";
		}
	},
	AMAZON_PRODUCT_ATTR_TITLE_INSTANT_VIDEO {
		@Override
		public String toString() {
			return "[class=hd]";
		}
	},
	AMAZON_PRODUCT_ATTR_TITLE_INSTANT_VIDEO_2 {
		@Override
		public String toString() {
			return "[id=aiv-content-title]";
		}
	},
	AMAZON_PRODUCT_ATTR_TITLE_MP3_STORE {
		@Override
		public String toString() {
			return "[class=a-size-large WebstoreAUISmoothFont a-text-bold]";
		}
	},
	AMAZON_PRODUCT_ATTR_TITLE_MP3_STORE_2 {
		@Override
		public String toString() {
			return "[class=a-size-large WebstoreAUISmoothFont]";
		}
	},
	// 'Books' section
	AMAZON_PRODUCT_ATTR_BOOK_TITLE {
		@Override
		public String toString() {
			return "[id=productTitle]";
		}
	};
}
