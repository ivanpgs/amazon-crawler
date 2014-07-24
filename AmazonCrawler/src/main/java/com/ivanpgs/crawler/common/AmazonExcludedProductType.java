package com.ivanpgs.crawler.common;

/**
 * 
 * Class to enumerate the Amazon Product that wanted to be excluded. For
 * examples, not for sale products such as:
 * 
 * - Movie Trailers (<body id="dp" class="movie_trailer en_US">) - ...
 * 
 * @author Ivan Poza
 * 
 */

public enum AmazonExcludedProductType {

	AMAZON_PRODUCT_MOVIE_TRAILER {
		@Override
		public String toString() {
			return "body[class=movie_trailer en_US]";
		}
	},
	AMAZON_PRODUCT_GAME_TRAILER {
		@Override
		public String toString() {
			return "body[class=video_game_trailer en_US]";
		}
	},
	AMAZON_PRODUCT_MUSIC_VIDEO {
		@Override
		public String toString() {
			return "body[class=music_video en_US]";
		}
	},
	AMAZON_PRODUCT_HOW_TO_VIDEO {
		@Override
		public String toString() {
			return "body[class=how_to_video en_US]";
		}
	};

}