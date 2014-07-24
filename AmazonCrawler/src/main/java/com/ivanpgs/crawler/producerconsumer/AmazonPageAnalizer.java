package com.ivanpgs.crawler.producerconsumer;

import org.apache.commons.lang3.EnumUtils;
import org.jsoup.nodes.Document;

import com.ivanpgs.crawler.common.AmazonCategoriesType;
import com.ivanpgs.crawler.common.AmazonExcludedProductType;
import com.ivanpgs.crawler.common.AmazonProductDescriptionAttribute;
import com.ivanpgs.crawler.common.AmazonProductPriceAttribute;
import com.ivanpgs.crawler.common.AmazonProductTitleAttribute;
import com.ivanpgs.crawler.common.AmazonProductType;
import com.ivanpgs.crawler.common.NotFoundMessages;
import com.ivanpgs.crawler.common.interfaces.GenericCategoriesType;
import com.ivanpgs.crawler.common.interfaces.GenericProductsType;
import com.ivanpgs.crawler.util.ParseUtils;

/**
 * Helper class to help parse Amazon Products. The methods in this class helps
 * parse a document making use of a parsing library.
 * 
 * @author Ivan Poza
 * 
 */
public class AmazonPageAnalizer extends AbstractJSoupPageAnalizer {

	public AmazonPageAnalizer() {
	}

	ParseUtils parseUtils;

	/**
	 * Method that given a document get the Product price attribute.
	 * 
	 * @return - the current Amazon Product price attribute represented by a
	 *         Double
	 */
	@Override
	protected Double getProductPrice(Document doc, String productId) {

		String productPrice = getAttributeValueFromLinkHtml(doc, productId,
				AmazonProductPriceAttribute.class);
		return ParseUtils.parsePrice(productPrice);
	}

	/**
	 * Method that given a document get the Product title attribute.
	 * 
	 * @return - the current Amazon Product title attribute represented by a
	 *         String
	 */
	@Override
	protected String getProductTitle(Document doc, String productId) {
		String productTitle = getAttributeValueFromLinkText(doc, productId,
				AmazonProductTitleAttribute.class);
		return ((productTitle == null) ? NotFoundMessages.PRODUCT_TITLE_NOT_FOUND
				.toString() : productTitle);
	}

	/**
	 * Method that given a document get the Product description attribute.
	 * 
	 * @return - the current Amazon Product description attribute represented by
	 *         a String
	 */
	@Override
	protected String getProductDescription(Document doc, String productId) {
		String productDescription = getAttributeValueFromLinkText(doc,
				productId, AmazonProductDescriptionAttribute.class);
		if (null == productDescription) {
			// TODO Get rid of the hard-coded constants
			productDescription = getAttributeValueFromMetaContentByName(doc,
					"description", "content");
		}
		return ((productDescription == null) ? NotFoundMessages.PRODUCT_DESCRIPTION_NOT_FOUND
				.toString() : productDescription);
	}

	@Override
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
	public String getCategoryId(String url, GenericCategoriesType categoryType) {
		return categoryType.getCategoryByDefinedTokens(url, categoryType);
	}

	public String[] getRelativeURLAndProductId(String url,
			GenericProductsType productType) {
		return productType.getRelativeURLAndProductID(url, productType);
	}

	@Override
	/**
	 * 
	 * Method that receiving an URL and a returns the category type as a
	 * GenericCategoriesType.
	 * 
	 * @param url
	 *            URL representing the URL of the category
	 */
	public GenericCategoriesType getCategoryType(String url) {
		for (AmazonCategoriesType categoryType : EnumUtils
				.getEnumList(AmazonCategoriesType.class)) {
			if (url.contains(categoryType.toString())) {
				return categoryType;
			}
		}
		return null;
	}

	@Override
	/**
	 * 
	 * Method that receiving an URL and a returns the category type as a
	 * GenericProductsType.
	 * 
	 * @param url
	 *            URL representing the URL of the product
	 */
	public GenericProductsType getProductType(String url) {
		for (AmazonProductType productType : EnumUtils
				.getEnumList(AmazonProductType.class)) {
			if (url.contains(productType.toString())) {
				return productType;
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	/**
	 * Method that get the Class that enumerates the products to be excluded. Products that have
	 * no need to be analyzed.
	 */
	public Class getExcludedProductClass() {
		return AmazonExcludedProductType.class;
	}

	@Override
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
	public String getProductId(String url, GenericProductsType productType) {
		return productType.getRelativeURLAndProductID(url, productType)[0];
	}

}
