package com.ivanpgs.crawler.model.service.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.ivanpgs.crawler.model.bean.PageLinkProductElement;
import com.ivanpgs.crawler.model.bean.Product;
import com.ivanpgs.crawler.model.dao.interfaces.ProductDAO;
import com.ivanpgs.crawler.model.service.interfaces.SrvProductService;
import com.ivanpgs.crawler.util.FileUtils;

/**
 * 
 * Service to store / fetch information from a Product/ several Products
 * 
 * @author Ivan Poza
 * 
 */
public class SrvProductServiceImpl implements SrvProductService {

	private ProductDAO productDAO;
	private LinkedList<Product> products;

	private LinkedList<Product> getNotInsertedProducts() {
		return products;
	}

	public void setProducts(LinkedList<Product> products) {
		this.products = products;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public void insertAll(List<Product> products) {
		getProductDAO().batchUpdate(products);
	}

	@Override
	public void addProduct(Product product) {
		getProductDAO().insert(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return getProductDAO().selectAll();
	}

	@Override
	public Product getProductById(String id) {
		return getProductDAO().findById(id);
	}

	@Override
	/**
	 * 
	 * Method that maps a PageLinkProductElement into a POJO Product
	 * 
	 * @param productElement PageLinkProductElement to be mapped as a POJO Product
	 * 
	 */
	public synchronized void addProductByElement(
			PageLinkProductElement productElement) {
		Product product = new Product();
		product.setId(productElement.getId());
		product.setAbsoluteUrl(product.getAbsoluteUrl());
		product.setDescription(productElement.getDescription());
		product.setPrice(productElement.getPrice());
		product.setTitle(productElement.getTitle());
		getNotInsertedProducts().add(product);
	}

	/**
	 * Method that print the alreaady not inserted product (in the DDBB) to the
	 * default TSV file
	 * 
	 * @throws IOException
	 */
	@Override
	public void printProductsToTSVFileByDefault() {
		FileUtils.createTsv(getNotInsertedProducts());
	}

}