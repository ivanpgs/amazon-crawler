package com.ivanpgs.crawler.common;

import com.ivanpgs.crawler.common.interfaces.GenericCategoriesType;

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
 * Class to enumerate the diferent Amazon Category Type patterns in their web
 * site
 * 
 * @author Ivan Poza
 * 
 */

public enum AmazonCategoriesType implements GenericCategoriesType {

	AMAZON_CATEGORY_TYPE_NODE {
		@Override
		public String toString() {
			return "node=";
		}

		@Override
		public String getCategoryByDefinedTokens(String fullCategoryURL,
				GenericCategoriesType categoryType) {
			String category = null;
			// If the category is specified by the node attribute
			int index = fullCategoryURL.indexOf(AMAZON_CATEGORY_TYPE_NODE
					.toString());
			if (index != -1) {
				category = fullCategoryURL.substring(index
						+ AMAZON_CATEGORY_TYPE_NODE.toString().length(),
						fullCategoryURL.length() - 1);
			}
			return category;
		}
	},
	AMAZON_CATEGORY_TYPE_AMB_LINK {
		@Override
		public String toString() {
			return "amb_link_";
		}

		@Override
		public String getCategoryByDefinedTokens(String fullCategoryURL,
				GenericCategoriesType categoryType) {
			String category = null;
			// If the category is specified by the node attribute amb_link_
			int index = fullCategoryURL.indexOf(AMAZON_CATEGORY_TYPE_AMB_LINK
					.toString());
			if (index != -1) {
				int indexQuestionMark = fullCategoryURL.indexOf('?');
				category = fullCategoryURL.substring(index
						+ AMAZON_CATEGORY_TYPE_AMB_LINK.toString().length(),
						indexQuestionMark);
			}
			return category;
		}
	},
	AMAZON_CATEGORY_TYPE_S_REF_LP {
		@Override
		public String toString() {
			return "s/ref/lp_";
		}

		@Override
		public String getCategoryByDefinedTokens(String fullCategoryURL,
				GenericCategoriesType categoryType) {
			String category = null;
			// If the category is specified by the page indexer (1 2 3 4...)
			// attribute s/ref/lp_<number>_pg_<page_number>?
			// eg /s/ref=lp_7505231011_pg_2?
			int index = fullCategoryURL.indexOf(AMAZON_CATEGORY_TYPE_S_REF_LP
					.toString());
			if (index != -1) {
				int indexQuestionMark = fullCategoryURL.indexOf('?');
				category = fullCategoryURL.substring(index
						+ AMAZON_CATEGORY_TYPE_S_REF_LP.toString().length(),
						indexQuestionMark);
			}
			// log.info("<-<- Ending method Utils.getCategoryByDefinedTokens()");
			return category;
		}
	};

}