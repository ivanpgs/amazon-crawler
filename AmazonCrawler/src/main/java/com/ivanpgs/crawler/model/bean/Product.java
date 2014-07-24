package com.ivanpgs.crawler.model.bean;

/**
 * Class representing a Product POJO. The attributes of the POJO are:
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
public class Product {

	// title, description, price and URL
	String id = null;
	String absoluteUrl = null;
	String title = null;

	public String getAbsoluteUrl() {
		return absoluteUrl;
	}

	public void setAbsoluteUrl(String absoluteUrl) {
		this.absoluteUrl = absoluteUrl;
	}

	String description = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	Double price = null;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Class: Product ID {")
				.append(this.getId()).append("}\r\n").append("Title [")
				.append(this.getTitle()).append("] Price : [")
				.append(this.getPrice()).append("] Description : [")
				.append(this.getDescription()).append("]\r\n").append("URL [")
				.append(this.getAbsoluteUrl()).append("]");
		return sb.toString();
	}

	public String toTabulatedString() {
		StringBuilder sb = new StringBuilder(this.getId()).append("\t")
				.append(this.getTitle()).append("\t").append(this.getPrice())
				.append("\t").append(this.getDescription()).append("\t")
				.append(this.getAbsoluteUrl());
		return sb.toString();
	}
}
