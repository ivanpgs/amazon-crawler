package com.ivanpgs.crawler.model.service.interfaces;

import java.io.IOException;

/**
 * 
 * Service interface for analyzing a page/site.
 * 
 * @author Ivan Poza
 * 
 */
public interface SrvPageAnalizerService {

	/**
	 * Main method to start the crawling process.<BR/>
	 * It receives the application context of Spring and creates two different
	 * queues:<BR/>
	 * <BR/>
	 * <OL>
	 * <LI>abpCategoryQueue - Queue in which each element contain an URL of an
	 * Amazon category</LI>
	 * <LI>abpProductQueue - Queue in which each element contain an URL of an
	 * Amazon product</LI>
	 * </OL>
	 * <BR/>
	 * After creating the queues and loading the abpCategoryQueue with at least
	 * one category page to<BR/>
	 * feed the Producers, create a thread for the crawler/producer and several
	 * Consumers to read the<BR/>
	 * information produced by the crawler. <BR/>
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void analizeSiteAndStored();
}
