package com.ivanpgs.crawler.model.dao.interfaces;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ivanpgs.crawler.model.bean.Product;

/**
 * 
 * DAO interface for the Product data:
 * 
 * <ul>
 * <li>int[] batchUpdate(final List<Product> products)</li>
 * <li>void insert(Product product)</li>
 * <li>List<Product> selectAll()</li>
 * <li>Product findById(int id)</li>
 * </ul>
 * 
 * @author Ivan Poza
 */

public interface ProductDAO {

	Log log = LogFactory.getLog(ProductDAO.class);

	/**
	 * Method that inserts a batch of Product into the table PRODUCT
	 * 
	 * @param products
	 *            list of products to be inserted
	 * @return
	 */
	int[] batchUpdate(final List<Product> products);

	/**
	 * Method that inserts a single product into the table PRODUCT
	 */
	void insert(Product product);

	/**
	 * Method that select all the products from the table PRODUCT and returns
	 * them as a Product list.
	 */
	List<Product> selectAll();

	/**
	 * Method that find the Product that match the id passed as a parameter and
	 * returns it.
	 * 
	 * @param id
	 *            id of the returning Product
	 * @return Product whose id is the same id received as a parameter
	 */
	Product findById(String id);
}