package com.ivanpgs.crawler.model.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ivanpgs.crawler.model.bean.Product;
import com.ivanpgs.crawler.model.dao.interfaces.ProductDAO;
import com.ivanpgs.crawler.model.dao.rowmapper.ProductRowMapper;

/**
 * Implementation of the Product DAO and its basic operations:
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
public class ProductDAOImpl implements ProductDAO {

	// JDBC connection to operate with the database
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Method that inserts a batch of Product into the table PRODUCT
	 * 
	 * @param products
	 *            list of products to be inserted
	 * @return
	 */
	@Override
	public int[] batchUpdate(final List<Product> products) {
		log.debug("->-> Starting method ProductDAOImpl.insertBatch()");

		String bathUpdateSql = "INSERT INTO PRODUCT (URL, TITLE, PRICE, DESCRIPTION) VALUES(?,?,?,?);";

		int[] updateCounts = getJdbcTemplate().batchUpdate(bathUpdateSql,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setString(1, products.get(i).getURL());
						ps.setString(2, products.get(i).getTitle());
						ps.setDouble(3, products.get(i).getPrice());
						ps.setString(4, products.get(i).getDescription());
					}

					@Override
					public int getBatchSize() {
						return products.size();
					}

				});

		log.info("->-> Ending method ProductDAOImpl.insertBatch()");
		return updateCounts;
	}

	@Override
	/**
	 * Method that inserts a single product into the table PRODUCT
	 */
	public void insert(Product product) {
		log.debug("->-> Starting method ProductDAOImpl.insert()");
		String insertSql = "INSERT INTO PRODUCT (URL, TITLE, PRICE, DESCRIPTION) VALUES(?,?,?,?);";
		getJdbcTemplate().update(
				insertSql,
				new Object[] { product.getURL(), product.getTitle(),
						product.getPrice(), product.getDescription() });
		log.info("->-> Ending method ProductDAOImpl.insert()");
	}

	@Override
	@SuppressWarnings("unchecked")
	/**
	 * Method that select all the products from the table PRODUCT and returns
	 * them as a Product list.
	 */
	public List<Product> selectAll() {
		log.debug("->-> Starting method selectAll()");
		String selectAllSql = "SELECT * FROM PRODUCT;";
		List<Product> productList = getJdbcTemplate().query(selectAllSql,
				new ProductRowMapper());
		log.info("->-> Ending method ProductDAOImpl.insert()");
		return productList;
	}

	/**
	 * Method that find the Product that match the id passed as a parameter and
	 * returns it.
	 * 
	 * @param id
	 *            id of the returning Product
	 * @return Product whose id is the same id received as a parameter
	 */
	public Product findById(String id) {
		String sql = "SELECT * FROM PRODUCT WHERE TITLE = ?";
		Product product = (Product) jdbcTemplate.queryForObject(sql,
				new Object[] { id }, new ProductRowMapper());
		return product;
	}

}
