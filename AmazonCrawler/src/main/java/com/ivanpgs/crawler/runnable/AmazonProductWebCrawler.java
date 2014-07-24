package com.ivanpgs.crawler.runnable;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ivanpgs.crawler.model.bean.Product;
import com.ivanpgs.crawler.model.service.interfaces.SrvPageAnalizerService;
import com.ivanpgs.crawler.model.service.interfaces.SrvProductService;
import com.ivanpgs.crawler.sync.PageAnalizerThread;
import com.ivanpgs.crawler.sync.PageParserThread;

/**
 * 
 * Amazon Crawler application using Java and Spring Framework.
 * 
 * A web crawler is a program that explores the Web by reading Web pages and
 * following the links it finds on them to other pages, from which it extracts
 * more links to follow, and so forth. A typical use of a Web crawler is to add
 * pages to a search service's database -- using a crawler to find pages
 * automatically allows the search service to build a much larger database than
 * would be possible if people had to identify pages and add them manually.
 * 
 * This application crawl and scrape product information from amazon.com .
 * 
 * Collected data will be stored in a relational database (MySQL), where each
 * product will be represented with a title, description, price and URL.
 * 
 * Collected data is also available in a form of a product data feed as output.
 * The feed is in TSV format with the following columns: product title,
 * description, price, URL
 * 
 * <ul>
 * <li>Using seed urls</li>
 * <li>Bread-first vs depth first approaching</li>
 * <li>Separation of crawler vs scrape (Consumer / Producer pattern)</li>
 * <li>Queuing mechanism</li>
 * </ul>
 * 
 */
public class AmazonProductWebCrawler {

	private static final String CONFIG_PATH = "com/ivanpgs/crawler/runnable/applicationContext.xml";
	// Number of Retrievers/Producers
	private Integer numberOfRetrievers = null;
	// Number of Parsers/Consumers
	private Integer numberOfParsers = null;
	private PageAnalizerThread pageRetrieverThread = null;
	private PageParserThread pageParserThread = null;
	private List<Product> productList = null;
	// TODO : Could be retrieved with a ServiceLocator, but there is only two
	// services
	private SrvProductService productService = null;

	private static ApplicationContext context;

	public Integer getNumberOfRetrievers() {
		return numberOfRetrievers;
	}

	public void setNumberOfRetrievers(Integer numberOfRetrievers) {
		this.numberOfRetrievers = numberOfRetrievers;
	}

	public Integer getNumberOfParsers() {
		return numberOfParsers;
	}

	public void setNumberOfParsers(Integer numberOfParsers) {
		this.numberOfParsers = numberOfParsers;
	}

	public SrvProductService getProductService() {
		return productService;
	}

	public void setProductService(SrvProductService productService) {
		this.productService = productService;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public PageParserThread getPageParserThread() {
		return pageParserThread;
	}

	public void setPageParserThread(PageParserThread pageParserThread) {
		this.pageParserThread = pageParserThread;
	}

	public PageAnalizerThread getPageRetrieverThread() {
		return pageRetrieverThread;
	}

	public void setPageRetrieverThread(PageAnalizerThread pageRetrieverThread) {
		this.pageRetrieverThread = pageRetrieverThread;
	}

	/**
	 * Method that loads the context of the Spring application. From the
	 * contexts creates a service that will begin with the the Crawler/Spider
	 * (Producer/Consumer) application.
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException {
		context = new ClassPathXmlApplicationContext(CONFIG_PATH);
		SrvPageAnalizerService srvPageAnalizeService = (SrvPageAnalizerService) context
				.getBean("srvPageAnalizerService");
		srvPageAnalizeService.analizeSiteAndStored();
	}
}