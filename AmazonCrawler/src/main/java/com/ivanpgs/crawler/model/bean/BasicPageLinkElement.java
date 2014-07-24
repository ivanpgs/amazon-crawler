package com.ivanpgs.crawler.model.bean;

/**
 * Class representing the basic element/component of a site/page.
 * 
 * The BasicPageLinkElement is the parent element. The children of the element
 * can be:
 * 
 * <ul>
 * <li>PageLinkProductElement</li>
 * <li>PageLinkCategoryElement</li>
 * <li>...</li>
 * </ul>
 * 
 * The basic attributes of this basic/generic element are
 * 
 * <ul>
 * <li>id</li>
 * <li>description</li>
 * <li>originalHrefValue</li>
 * <li>absoluteHrefValue</li>
 * </ul>
 * 
 * @author Ivan Poza
 */
public class BasicPageLinkElement {

	private String id;
	private String description;
	private String originalHrefValue;
	private String absoluteHrefValue;

	public String getOriginalHrefValue() {
		return originalHrefValue;
	}

	public void setOriginalHrefValue(String originalHrefValue) {
		this.originalHrefValue = originalHrefValue;
	}

	public String getAbsoluteHrefValue() {
		return absoluteHrefValue;
	}

	public void setAbsoluteHrefValue(String absoluteHrefValue) {
		this.absoluteHrefValue = absoluteHrefValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
