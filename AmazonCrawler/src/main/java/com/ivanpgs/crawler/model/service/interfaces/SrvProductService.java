package com.ivanpgs.crawler.model.service.interfaces;

import java.io.IOException;
import java.util.List;

import com.ivanpgs.crawler.model.bean.PageLinkProductElement;
import com.ivanpgs.crawler.model.bean.Product;

/**
 * Service interface to store / fetch information from a Product/ several
 * Products
 * 
 * @author Ivan Poza
 * 
 */
public interface SrvProductService {

	public List<Product> getAllProducts();

	void insertAll(List<Product> products);

	void addProduct(Product product);

	Product getProductById(String id);

	public void addProductByElement(PageLinkProductElement productElement);

	/**
	 * Method that print the alreaady not inserted product (in the DDBB) to the
	 * default TSV file
	 * 
	 * @throws IOException
	 */
	public void printProductsToTSVFileByDefault();

}