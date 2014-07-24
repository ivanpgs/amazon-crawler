package com.ivanpgs.crawler.common.interfaces;

import java.util.List;

import com.ivanpgs.crawler.model.bean.BasicPageLinkElement;
import com.ivanpgs.crawler.model.bean.PageLinkProductElement;

/**
 * 
 * Basic class which target is to read a page/site analyzing the
 * elements/components of the site and gathering together categories and
 * products in a unique list of BasicPageLinkElement.
 * 
 * The BasicPageLinkElement is the parent element. The children of the element
 * can be:
 * 
 * <ul>
 * <li>PageLinkProductElement</li>
 * <li>PageLinkCategoryElement</li>
 * </ul>
 * 
 * @author Ivan Poza
 * 
 */
public interface PageAnalizer {

	/**
	 * 
	 * Method that receiving a String representing an page/site's URL analyze
	 * the elements/components of the site and gather together the categories
	 * and products in a unique list of BasicPageLinkElement.
	 * 
	 * @param url
	 *            the URL of the page/site to be analyze/parse
	 * @return a list of BasicPageLinkElement of the analyzed page/site
	 * @throws Exception
	 */
	List<BasicPageLinkElement> readPage(String url) throws Exception;

	/**
	 * 
	 * Method that receiving an URL and a returns the category type as a
	 * GenericCategoriesType.
	 * 
	 * @param url
	 *            URL representing the URL of the category
	 */
	GenericCategoriesType getCategoryType(String url);

	/**
	 * 
	 * Method that receiving an URL and a GenericCategoriesType representing the
	 * category type (might be different depending the implementation) returns
	 * the category Id.
	 * 
	 * @param url
	 *            URL representing the URL of the category to be retrieved
	 * @param categoryType
	 *            String representing the id of the category to be retrieved
	 */
	String getCategoryId(String url, GenericCategoriesType categoryType);

	/**
	 * 
	 * Method that add a new category to the BasicPageLinkElement list.
	 * 
	 * @param elements
	 *            a list of BasicPageLinkElement to add the new category
	 *            (PageLinkCategoryElement)
	 * @param url
	 *            URL representing the URL of the category to be added
	 * @param categoryId
	 *            String representing the id of the category to be added
	 */
	void addNewCategory(List<BasicPageLinkElement> elements, String url,
			String categoryId);

	/**
	 * 
	 * Method that receiving an URL and a returns the category type as a
	 * GenericProductsType.
	 * 
	 * @param url
	 *            URL representing the URL of the product
	 */
	GenericProductsType getProductType(String url);

	/**
	 * 
	 * Method that receiving an URL and a GenericProductsType representing the
	 * product type (might be different depending the implementation) returns
	 * the product Id.
	 * 
	 * @param url
	 *            URL representing the URL of the product to be retrieved
	 * @param productType
	 *            String representing the id of the product to be retrieved
	 */
	String getProductId(String url, GenericProductsType productType);

	/**
	 * 
	 * Method that add a new product to the BasicPageLinkElement list.
	 * 
	 * @param elements
	 *            a list of BasicPageLinkElement to add the new category
	 *            (PageLinkProductElement)
	 * @param url
	 *            URL representing the URL of the product to be added
	 * @param productId
	 *            String representing the id of the product to be added
	 */
	void addNewProduct(List<BasicPageLinkElement> elements, String url,
			String productId);

	/**
	 * 
	 * Method that populate a PageLinkProductElement with extra fields.
	 * 
	 * @param pe
	 *            PageLinkProductElement to be populated
	 */
	PageLinkProductElement populateProductElementAttributes(
			PageLinkProductElement pe);

	/**
	 * 
	 * Method that receiving a PageLinkProductElement filter if the product has
	 * to be excluded or not. The excluded policy must be implemented.
	 * 
	 * @param pe
	 *            PageLinkProductElement
	 * @return true if the product should be excluded and false in any other
	 *         case
	 */
	boolean isExcludedProduct(PageLinkProductElement pe);

}
