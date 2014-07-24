package com.ivanpgs.crawler.producerconsumer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ivanpgs.crawler.common.impl.HTMLParser;
import com.ivanpgs.crawler.common.interfaces.GenericCategoriesType;
import com.ivanpgs.crawler.common.interfaces.GenericProductsType;
import com.ivanpgs.crawler.common.interfaces.PageAnalizer;
import com.ivanpgs.crawler.model.bean.BasicPageLinkElement;
import com.ivanpgs.crawler.model.bean.PageLinkCategoryElement;
import com.ivanpgs.crawler.model.bean.PageLinkProductElement;

/**
 * Abstract class which target is to read a page/site analyzing the
 * elements/components of the site and gathering together categories and
 * products in a unique list of BasicPageLinkElement (based on the interface
 * methods of PageAnalizer).
 * 
 * The BasicPageLinkElement is the parent element. The children of the element
 * can be:
 * 
 * <ul>
 * <li>PageLinkProductElement</li>
 * <li>PageLinkCategoryElement</li>
 * </ul>
 * 
 * The analyzing of the page/site is made using JSoup as the parser library.
 * 
 * USING LIBRARY JSOUP to parse the document The JSoup library is used to get
 * the DOM tree of the page. It has some features that makes it better for
 * navigation through the DOM tree than using the DOM-type getElementBy
 * 
 * @see http://jsoup.org/apidocs/org/jsoup/nodes/Element.html#select
 * 
 * @author Ivan Poza
 * 
 */
public abstract class AbstractJSoupPageAnalizer implements PageAnalizer {

	public AbstractJSoupPageAnalizer() {
	}

	private final Log log = LogFactory.getLog(AbstractJSoupPageAnalizer.class);
	private HTMLParser htmlParser = null;

	// Set of already processed Categories to prevent the application to
	// re-processing again the same page
	private Set<String> alreadyAddedCategorySet = null;
	// Set of already processed Products to prevent the application to
	// re-processing again the same page
	private Set<String> alreadyAddedProductSet = null;

	public HTMLParser getHtmlParser() {
		return htmlParser;
	}

	public void setHtmlParser(HTMLParser htmlParser) {
		this.htmlParser = htmlParser;
	}

	public Set<String> getAlreadyAddedCategorySet() {
		return alreadyAddedCategorySet;
	}

	public void setAlreadyAddedCategorySet(Set<String> alreadyAddedCategorySet) {
		this.alreadyAddedCategorySet = alreadyAddedCategorySet;
	}

	public Set<String> getAlreadyAddedProductSet() {
		return alreadyAddedProductSet;
	}

	public void setAlreadyAddedProductSet(Set<String> alreadyAddedProductSet) {
		this.alreadyAddedProductSet = alreadyAddedProductSet;
	}

	/**
	 * @param abpCategoryQueue
	 *            - Queue in which each element contain an URL of an Amazon
	 *            category
	 * @param abpProductQueue
	 *            - Queue in which each element contain an URL of an Amazon
	 *            product
	 * @throws IOException
	 */
	public AbstractJSoupPageAnalizer(Set<String> alreadyAddedCategorySet,
			Set<String> alreadyAddedProductSet, HTMLParser htmlParser)
			throws IOException {
		setAlreadyAddedCategorySet(alreadyAddedCategorySet);
		setAlreadyAddedProductSet(alreadyAddedProductSet);
		setHtmlParser(htmlParser);
	}

	/**
	 * 
	 * Method that receiving a String representing an page/site's URL analyze
	 * the elements/components of the site and gather together the categories
	 * and products in a unique list of BasicPageLinkElement.
	 * 
	 * @param externalUrlToAnalize
	 *            the URL of the page/site to be analyze/parse
	 * @return a list of BasicPageLinkElement of the analyzed page/site
	 * @throws Exception
	 */
	@Override
	public List<BasicPageLinkElement> readPage(String externalUrlToAnalize)
			throws Exception {
		// log.info("->-> Starting method AbstractJSoupPageAnalizer.readPage()");
		List<BasicPageLinkElement> pageElements = new LinkedList<BasicPageLinkElement>();

		// Get all the hyperlinks within the page
		Elements pagelinks = getPageHyperlinks(htmlParser
				.parseURL(externalUrlToAnalize));

		// Gathering CATEGORIES and PRODUCTS
		if (pagelinks.size() != 0) {
			for (Element link : pagelinks) {

				String url = getHREFAttribute(link);

				// In case it is a CATEGORY
				GenericCategoriesType categoryType = getCategoryType(url);
				if (null != categoryType) {
					String categoryId = getCategoryId(url, categoryType);
					addNewCategory(pageElements, url, categoryId);

				}
				// In case it is a PRODUCT
				GenericProductsType productType = getProductType(url);
				if (null != productType) {
					String productID = getProductId(url, productType);
					addNewProduct(pageElements, url, productID);
				}
			}
		}

		// log.info("<-<- Ending method AbstractJSoupPageAnalizer.readPage");
		return pageElements;
	}

	/**
	 * 
	 * Method that add a new category to the BasicPageLinkElement list and also
	 * add the brand new added category to the already added category set to
	 * avoid re-processing it again.
	 * 
	 * @param elements
	 *            a list of BasicPageLinkElement to add the new category
	 *            (PageLinkCategoryElement)
	 * @param url
	 *            URL representing the URL of the category to be added
	 * @param categoryId
	 *            String representing the id of the category to be added
	 */
	@Override
	public void addNewCategory(List<BasicPageLinkElement> pageElements,
			String url, String categoryId) {

		if (!alreadyAddedCategorySet.contains(categoryId)) {
			// Create a new Category Element
			PageLinkCategoryElement ce = new PageLinkCategoryElement();
			ce.setId(categoryId);
			alreadyAddedCategorySet.add(categoryId);

			// In case of an relative URL referenced in the HREF attribute it is
			// added the baseURI to make an absolute one
			if (!url.contains(htmlParser.getBaseURI())) {
				ce.setAbsoluteHrefValue(htmlParser.getBaseURI() + url);
			} else {
				// In case of an absolute URL referenced in the HREF attribute
				// it is added
				ce.setAbsoluteHrefValue(url);
			}
			// Adding to the basicElementList
			pageElements.add(ce);
		}
	}

	/**
	 * 
	 * Method that add a new product to the BasicPageLinkElement list and also
	 * add the brand new added product to the already added product set to avoid
	 * re-processing it again.
	 * 
	 * @param elements
	 *            a list of BasicPageLinkElement to add the new category
	 *            (PageLinkProductElement)
	 * @param url
	 *            URL representing the URL of the product to be added
	 * @param productId
	 *            String representing the id of the product to be added
	 */
	@Override
	public void addNewProduct(List<BasicPageLinkElement> pageElements,
			String url, String productID) {
		// Checking against the HashSet containing the product IDs. In case it
		// has been already added it will not be added to the Product Queue
		if (!alreadyAddedProductSet.contains(productID)) {
			// Create a new Category Element
			PageLinkProductElement pe = new PageLinkProductElement();
			pe.setId(productID);
			// In case of an relative URL referenced in the HREF attribute it is
			// added the baseURI to make an absolute one
			if (!url.contains(htmlParser.getBaseURI())) {
				pe.setAbsoluteHrefValue(htmlParser.getBaseURI() + url);
			} else {
				// In case of an absolute URL referenced in the HREF attribute
				// it is added
				pe.setAbsoluteHrefValue(url);
			}
			// The product is added to the hash-set in order to avoid future
			// duplicates
			alreadyAddedProductSet.add(productID);
			// Adding to the basicElementList
			pageElements.add(pe);
		}

	}

	/**
	 * 
	 * Given a link element returns the HREF attribute value
	 * 
	 * @param link
	 *            element representing a link
	 * @return the link HREF attribute value
	 */
	private String getHREFAttribute(Element link) {
		// @see http://jsoup.org/apidocs/org/jsoup/select/Selector.html
		return link.attr("href");
	}

	/**
	 * Get all the hyper-link within the page represented by the document doc
	 * 
	 * @param doc
	 *            Document representing a page
	 * @return hyper-link elements within the document passed as a parameter
	 */
	public Elements getPageHyperlinks(Document doc) {
		// @see http://jsoup.org/apidocs/org/jsoup/select/Selector.html
		return doc.select("a[href]");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * 
	 * Method that given a HTML represented by a Document, look up for the attributes
	 * that matches the Enum class <I>attributeClass</I> passed as a parameter and if it finds
	 * an attribute matching that class its text value will be returned.
	 * 
	 * For example:
	 * 
	 * attributeClass -> AmazonProductPriceAttribute.class
	 * This class is an Enum with the enumeration elements: 
	 * 
	 * AMAZON_PRODUCT_ATTR_PRICE ([class=priceLarge])
	 * AMAZON_PRODUCT_ATTR_PRICE_KIT ([class=priceLarge kitsunePrice])
	 * AMAZON_PRODUCT_ATTR_PRICE_MP3_STORE ([id=actualPriceValue])
	 * AMAZON_PRODUCT_ATTR_DESCRIPTION_INSTANT_VIDEO_2 ([value=PRODUCT_ID])
	 * 
	 * Each of this enumeration stands for an HTML tag and attribute to search within the document.
	 * The method will iterate through the Emum values to search if it is located in the document.
	 * 
	 * If it is found an HTML tag with the attribute value class="priceLarge" then it will be
	 * return the text inside this HTML tag:
	 * 
	 * <div class="priceLarge" ...> $129.99 </div>
	 * 
	 * This HTML tag above would match the AMAZON_PRODUCT_ATTR_PRICE Enum and it would be returned
	 * $129.99 represented as a String.
	 * 
	 * @param doc HTML parsed Document
	 * @param productId product id to look up within the attribute values (if needed)
	 * @param attributeClass class of the attribute to look up within the document
	 * @return
	 */
	protected String getAttributeValueFromLinkText(Document doc,
			String productId, Class attributeClass) {
		List<Enum> enumList = EnumUtils.getEnumList(attributeClass);
		for (Enum productTitleEnum : enumList) {
			Elements pagelinks = doc.select(productTitleEnum.toString());
			pagelinks = doc.select(productTitleEnum.toString());
			if ((null != pagelinks.text())
					&& (!"".equals(pagelinks.text().trim()))) {
				return pagelinks.text();
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * Method that given a HTML represented by a Document, look up for the attributes
	 * that matches the Enum class <I>attributeClass</I> passed as a parameter and if it finds
	 * an attribute matching that class its innerHtml value will be returned.
	 * 
	 * For a further explanation @see getAttributeValueFromLinkText
	 * 
	 * @param doc HTML parsed Document
	 * @param productId product id to look up within the attribute values (if needed)
	 * @param attributeClass class of the attribute to look up within the document
	 * @return
	 */
	protected String getAttributeValueFromLinkHtml(Document doc,
			String productId, Class attributeClass) {
		List<Enum> enumList = EnumUtils.getEnumList(attributeClass);
		for (Enum productTitleEnum : enumList) {
			String productTagSearch = productTitleEnum.toString();
			// Special attribute pattern that needs the productId to be retrieve
			if (productTagSearch.contains("PRODUCT_ID")) {
				productTagSearch = productTagSearch.replaceAll("PRODUCT_ID",
						productId);
			}
			Elements pagelinks = doc.select(productTagSearch);
			if ((null != pagelinks.html())
					&& (!"".equals(pagelinks.html().trim()))) {
				return pagelinks.html();
			}
		}
		return null;
	}

	/**
	 * TODO: Add a proper description
	 * 
	 * 
	 * E.g: <I>lookinUpAttribute</I>:"description" and
	 * <I>getValueFromAttribute</I>:"content" from the next tag:
	 * 
	 * <meta content=
	 * "Amazon.com: Nobody's Smiling (Deluxe) [Explicit]: Common: MP3 Downloads"
	 * name="description">
	 * 
	 * ...Would return the value
	 * "Amazon.com: Nobody's Smiling (Deluxe) [Explicit]: Common: MP3 Downloads"
	 * 
	 * @param doc
	 * @param lookinUpAttribute
	 * @param getValueFromAttribute
	 * @return
	 */
	protected String getAttributeValueFromMetaContentByName(Document doc,
			String lookinUpAttribute, String getValueFromAttribute) {
		String selectorParameter = "meta[name=" + lookinUpAttribute + "]";
		Elements pagelinks = doc.select(selectorParameter);
		if (!pagelinks.isEmpty()) {
			return pagelinks.attr(getValueFromAttribute);
		}
		return null;
	}

	@Override
	/**
	 * 
	 * Method that populate a PageLinkProductElement with extra fields.
	 * 
	 * @param pe
	 *            PageLinkProductElement to be populated
	 */
	public PageLinkProductElement populateProductElementAttributes(

	PageLinkProductElement pe) {
		Document doc = htmlParser.parseURL(pe.getAbsoluteHrefValue());
		pe.setPrice(getProductPrice(doc, pe.getId()));
		pe.setTitle(getProductTitle(doc, pe.getId()));
		pe.setDescription(getProductDescription(doc, pe.getId()));
		log.info(pe.toString());
		return pe;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	public boolean isExcludedProduct(PageLinkProductElement pe) {
		Document doc = htmlParser.parseURL(pe.getAbsoluteHrefValue());
		List<Enum> enumList = EnumUtils.getEnumList(getExcludedProductClass());
		for (Enum nonProductEnum : enumList) {
			Elements pagelinks = doc.select(nonProductEnum.toString());
			if ((null != pagelinks.html()) && (pagelinks.size() > 0)) {
				log.info("WATCH OUT! The product ID: [" + pe.getId()
						+ "] has been excluded. Reason: ["
						+ nonProductEnum.name() + "]");
				return true;
			}
		}
		return false;
	}

	protected abstract Double getProductPrice(Document doc, String productId);

	protected abstract String getProductTitle(Document doc, String productId);

	protected abstract String getProductDescription(Document doc,
			String productId);

	@SuppressWarnings("rawtypes")
	protected abstract Class getExcludedProductClass();

}
