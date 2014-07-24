package com.ivanpgs.crawler.model.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * Class in charge of mapping the products returned from the database to the
 * Product POJO
 * 
 * @author ivan
 * 
 */
public class ProductRowMapper implements RowMapper {

	// title, description, price and URL
	String title = null;
	String description = null;
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
	/**
	 * @param rs result-set with the products retrieved from the database
	 * @param rowNum Number of rows to be retrieved
	 * 
	 * @return Object POJO Products after being mapped
	 */
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductRowMapper product = new ProductRowMapper();
		product.setTitle(rs.getString("TITLE"));
		product.setDescription(rs.getString("DESCRIPTION"));
		product.setPrice(rs.getDouble("PRICE"));
		product.setURL(rs.getString("URL"));
		return product;
	}
}
