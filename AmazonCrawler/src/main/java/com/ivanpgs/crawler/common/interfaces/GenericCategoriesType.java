package com.ivanpgs.crawler.common.interfaces;

/**
 * 
 * Basic interface representing the existing BasicElement category types
 * 
 * @author Ivan Poza
 * 
 */

public interface GenericCategoriesType {

	String getCategoryByDefinedTokens(String fullCategoryURL,
			GenericCategoriesType categoryType);
}