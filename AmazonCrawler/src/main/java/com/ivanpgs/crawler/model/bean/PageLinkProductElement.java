package com.ivanpgs.crawler.model.bean;

/**
 * Class representing a Product element/component of a site/page. The extra
 * attributes of this element are:
 * 
 * <ul>
 * <li>title</li>
 * <li>description</li>
 * <li>price</li>
 * <li>...</li>
 * </ul>
 * 
 * @author Ivan Poza
 */
public class PageLinkProductElement extends BasicPageLinkElement {

	// title, description, price and URL
	String title = null;
	String description = null;
	Double price = null;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(
				"Class: PageLinkProductElement ID {").append(this.getId())
				.append("}\r\n").append("Title [").append(this.getTitle())
				.append("] Price : [").append(this.getPrice())
				.append("] Description : [").append(this.getDescription())
				.append("]\r\n").append("URL [")
				.append(this.getAbsoluteHrefValue()).append("]");
		return sb.toString();
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	String url = null;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}
}
