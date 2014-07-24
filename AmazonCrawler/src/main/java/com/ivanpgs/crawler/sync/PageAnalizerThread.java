package com.ivanpgs.crawler.sync;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ivanpgs.crawler.common.interfaces.PageAnalizer;
import com.ivanpgs.crawler.model.bean.BasicPageLinkElement;
import com.ivanpgs.crawler.model.bean.PageLinkCategoryElement;
import com.ivanpgs.crawler.model.bean.PageLinkProductElement;
import com.ivanpgs.crawler.producerconsumer.QueueElementProvider;

/**
 * PageRetrieverThread is the thread that launch the retriever/crawler/producer.<BR/>
 * <BR/>
 * It takes an element from the abpCategoryQueue (an url) and then make use of
 * the object PageRetriever<BR/>
 * to read/parse the page and convert it into several categories and products.<BR/>
 * <B>It can add categories or products while parsing the page.</B> <BR/>
 */
public class PageAnalizerThread implements Runnable {

	private final Log log = LogFactory.getLog(PageAnalizerThread.class);
	private BlockingQueue<PageLinkProductElement> abpProductQueue = null;
	private BlockingQueue<PageLinkCategoryElement> abpCategoryQueue = null;
	private Integer maxProduct = null;
	private PageAnalizer pageAnalizer = null;
	private String initialURL = null;

	public String getInitialURL() {
		return initialURL;
	}

	public void setInitialURL(String initialURL) {
		this.initialURL = initialURL;
	}

	private QueueElementProvider queueElementProvider = null;

	public PageAnalizer getPageAnalizer() {
		return pageAnalizer;
	}

	public void setPageAnalizer(PageAnalizer pageAnalizer) {
		this.pageAnalizer = pageAnalizer;
	}

	public Integer getMaxProduct() {
		return maxProduct;
	}

	public void setMaxProduct(Integer maxProduct) {
		this.maxProduct = maxProduct;
	}

	public Set<String> getAlreadyAddedProductSet() {
		return alreadyAddedProductSet;
	}

	public QueueElementProvider getQueueElementProvider() {
		return queueElementProvider;
	}

	public void setQueueElementProvider(
			QueueElementProvider queueElementProvider) {
		this.queueElementProvider = queueElementProvider;
	}

	public void setAlreadyAddedProductSet(Set<String> alreadyAddedProductSet) {
		this.alreadyAddedProductSet = alreadyAddedProductSet;
	}

	private Set<String> alreadyAddedProductSet = null;

	public BlockingQueue<PageLinkProductElement> getAbpProductQueue() {
		return abpProductQueue;
	}

	public void setAbpProductQueue(
			BlockingQueue<PageLinkProductElement> abpProductQueue) {
		this.abpProductQueue = abpProductQueue;
	}

	public BlockingQueue<PageLinkCategoryElement> getAbpCategoryQueue() {
		return abpCategoryQueue;
	}

	public void setAbpCategoryQueue(
			BlockingQueue<PageLinkCategoryElement> abpCategoryQueue) {
		this.abpCategoryQueue = abpCategoryQueue;
	}

	public PageAnalizerThread(
			BlockingQueue<PageLinkCategoryElement> abpCategoryQueue,
			BlockingQueue<PageLinkProductElement> abpProductQueue,
			PageAnalizer pageAnalizer,
			QueueElementProvider queueElementProvider, Integer maxProduct,
			String initialURL) throws IOException, InterruptedException {
		setAbpCategoryQueue(abpCategoryQueue);
		setAbpProductQueue(abpProductQueue);
		setPageAnalizer(pageAnalizer);
		setQueueElementProvider(queueElementProvider);
		setMaxProduct(maxProduct);
		setInitialURL(initialURL);
	}

	/*
	 * While there is a category in the queue it will call the PageAnalyzer to
	 * get new categories/products
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		log.info("->-> Starting method PageRetrieverThread.run()");
		try {
			/*
			 * The 'maxProduct' condition is parametrized by a constant in the
			 * ApplicationContext and can be changed
			 */
			queueElementProvider.provideCategoryQueue(getInitialURL());
			while ((abpCategoryQueue.size() > 0)
					&& (abpProductQueue.size() < maxProduct)) {
				PageLinkCategoryElement ce = abpCategoryQueue.take();
				List<BasicPageLinkElement> elements = pageAnalizer.readPage(ce
						.getAbsoluteHrefValue());
				queueElementProvider.provideQueue(elements);
				// log.info("Category Queue Size : "+abpCategoryQueue.size());
				// log.info("Product Queue Size : "+abpProductQueue.size());
			}
		} catch (IOException e) {
			log.error("IOException at method PageRetrieverThread.run() : "
					+ e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("InterruptedException at method PageRetrieverThread.run() : "
					+ e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO Close all the objects
		}
		log.info("<-<- Ending method PageRetrieverThread.run()");
	}

}
