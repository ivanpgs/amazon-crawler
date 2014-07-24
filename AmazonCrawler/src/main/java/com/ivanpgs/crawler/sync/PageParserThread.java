package com.ivanpgs.crawler.sync;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ivanpgs.crawler.common.interfaces.PageAnalizer;
import com.ivanpgs.crawler.model.bean.PageLinkProductElement;
import com.ivanpgs.crawler.model.service.impl.SrvProductServiceImpl;
import com.ivanpgs.crawler.model.service.interfaces.SrvProductService;

/**
 * PageParserThread is the thread that launch the parsers/spiders/consumers.<BR/>
 * <BR/>
 * It takes an element from the abpProductQueue (an url) and then make use of
 * the object PageParser<BR/>
 * to read/parse the page and convert it into a Product<BR/>
 * <BR/>
 */
public class PageParserThread implements Runnable {

	private final Log log = LogFactory.getLog(PageParserThread.class);
	private BlockingQueue<PageLinkProductElement> abpProductQueue = null;
	private SrvProductService productService = null;
	private PageAnalizer pageAnalizer = null;

	public PageAnalizer getPageAnalizer() {
		return pageAnalizer;
	}

	public void setPageAnalizer(PageAnalizer pageAnalizer) {
		this.pageAnalizer = pageAnalizer;
	}

	public void setProductService(SrvProductService productService) {
		this.productService = productService;
	}

	public SrvProductService getProductService() {
		return productService;
	}

	private Integer idThread = null;
	private Integer maxProduct = null;

	public Integer getMaxProduct() {
		return maxProduct;
	}

	public void setMaxProduct(Integer maxProduct) {
		this.maxProduct = maxProduct;
	}

	public Integer getIdThread() {
		return idThread;
	}

	public void setIdThread(Integer idThread) {
		this.idThread = idThread;
	}

	public BlockingQueue<PageLinkProductElement> getAbpProductQueue() {
		return abpProductQueue;
	}

	public void setAbpProductQueue(
			BlockingQueue<PageLinkProductElement> abpProductQueue) {
		this.abpProductQueue = abpProductQueue;
	}

	public PageParserThread(SrvProductServiceImpl productService,
			BlockingQueue<PageLinkProductElement> abpProductQueue,
			PageAnalizer pageAnalizer, Integer threadId, Integer maxProduct)
			throws IOException {
		setProductService(productService);
		setAbpProductQueue(abpProductQueue);
		setPageAnalizer(pageAnalizer);
		setIdThread(threadId);
		setMaxProduct(maxProduct);
	}

	/*
	 * 1. Taking the next element (url) of the Product Queue. 2. Using the
	 * PageParser to connect to the given element (url) to parse it into a new
	 * Product 3. Adding the product to a list of Products
	 * 
	 * @see java.lang.Runnable#run()
	 */

	@Override
	public void run() {
		log.info("->-> Starting method PageParserThread.run()");
		/*
		 * IMPORTANT: This condition (maxProduct) is parametrized by a constant
		 * in the ApplicationContext and can be changed
		 */
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (!abpProductQueue.isEmpty()) {
			try {
				PageLinkProductElement nextPageLinkProductElement = abpProductQueue
						.take();
				if (!pageAnalizer.isExcludedProduct(nextPageLinkProductElement)) {
					// Parse the url pointed by the PageLinkProductElement taken
					// from the Product Queue and populates
					// the PageLinkProductElement with extra attributes
					PageLinkProductElement pe = pageAnalizer
							.populateProductElementAttributes(nextPageLinkProductElement);
					// log.info("Product Queue Size : "+abpProductQueue.size());
					getProductService().addProductByElement(pe);
				}
				log.info("abpProductQueue.size : " + abpProductQueue.size());
				// product.getTitle().trim()+" ]["+product.getPrice()+"]");
			} catch (Exception e) {
				log.error("Exception at method PageParserThread.run() : "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		log.info("Thread #" + getIdThread() + " FINISHED!!");

	}

}
