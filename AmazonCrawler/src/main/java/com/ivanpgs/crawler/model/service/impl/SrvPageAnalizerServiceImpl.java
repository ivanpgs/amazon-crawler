package com.ivanpgs.crawler.model.service.impl;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ivanpgs.crawler.common.interfaces.PageAnalizer;
import com.ivanpgs.crawler.model.service.interfaces.SrvPageAnalizerService;
import com.ivanpgs.crawler.model.service.interfaces.SrvProductService;
import com.ivanpgs.crawler.sync.PageAnalizerThread;
import com.ivanpgs.crawler.sync.PageParserThread;

/**
 * 
 * Service in charge of analyzing a page/site.
 * 
 * Could be retrieved with a ServiceLocator, but there is only two services
 * 
 * @author Ivan Poza
 * 
 */
public class SrvPageAnalizerServiceImpl implements SrvPageAnalizerService {

	private final Log log = LogFactory.getLog(SrvPageAnalizerServiceImpl.class);

	public void setAbpCategoryQueue(BlockingQueue<String> abpCategoryQueue) {
		this.abpCategoryQueue = abpCategoryQueue;
	}

	public void setAbpProductQueue(BlockingQueue<String> abpProductQueue) {
		this.abpProductQueue = abpProductQueue;
	}

	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		return threadPoolTaskExecutor;
	}

	public void setPageAnalizerThread(PageAnalizerThread pageAnalizerThread) {
		this.pageAnalizerThread = pageAnalizerThread;
	}

	public void setPageParserThread(PageParserThread pageParserThread) {
		this.pageParserThread = pageParserThread;
	}

	public void setThreadPoolTaskExecutor(
			ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

	public void setNumberOfPageAnalizers(Integer numberOfPageAnalizers) {
		this.numberOfPageAnalizers = numberOfPageAnalizers;
	}

	public void setNumberOfParsers(Integer numberOfParsers) {
		this.numberOfParsers = numberOfParsers;
	}

	private BlockingQueue<String> abpCategoryQueue = null;
	private BlockingQueue<String> abpProductQueue = null;
	private PageAnalizerThread pageAnalizerThread = null;
	private PageParserThread pageParserThread = null;
	private PageAnalizer pageAnalizer = null;
	private SrvProductService srvProductService = null;

	public SrvProductService getSrvProductService() {
		return srvProductService;
	}

	public void setSrvProductService(SrvProductService srvProductService) {
		this.srvProductService = srvProductService;
	}

	public PageAnalizer getPageAnalizer() {
		return pageAnalizer;
	}

	public void setPageAnalizer(PageAnalizer pageAnalizer) {
		this.pageAnalizer = pageAnalizer;
	}

	private ThreadPoolTaskExecutor threadPoolTaskExecutor = null;
	// Number of Retrievers/Producers
	private Integer numberOfPageAnalizers = null;
	// Number of Parsers/Consumers
	private Integer numberOfParsers = null;

	/**
	 * Method that starts a crawling process analyzing a site from a seed URL
	 * defined in the Spring ApplicationContext.xml context:
	 * 
	 * <OL>
	 * <LI>abpCategoryQueue - Queue in which each element contain an URL of an
	 * Amazon category</LI>
	 * <LI>abpProductQueue - Queue in which each element contain an URL of an
	 * Amazon product</LI>
	 * </OL>
	 * 
	 * Create a Thread Pool helped by the Thread Pool Task Executor. The setting
	 * up of the Thread Pool is defined in the Spring Application context, with
	 * the number of PageAnalizers (producer/crawlers) and PageParser
	 * (consumers). Both implements the Runnable interface and will be executes
	 * and controlled by the Thread Pool Executer until they finishes their
	 * tasks (method run).
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public void analizeSiteAndStored() {
		log.info("->-> Starting method SrvPageAnalizerService.analizeSiteAndStored()");

		// Starting the task executor to start the threads
		for (int i = 0; i < numberOfPageAnalizers; i++) {
			threadPoolTaskExecutor.execute(pageAnalizerThread);
		}
		log.info("Created " + numberOfPageAnalizers + " Retriever threads");

		// Starting the Spiders/Consumers
		for (int i = 0; i < numberOfParsers; i++) {
			pageParserThread.setIdThread(i);
			threadPoolTaskExecutor.execute(pageParserThread);
		}
		log.info("Created " + numberOfParsers + " Parser threads");

		threadPoolTaskExecutor.shutdown();
		// Waiting for all the threads to be finished
		while (threadPoolTaskExecutor.getActiveCount() != 0) {
		}
		;
		// Print the generated products
		getSrvProductService().printProductsToTSVFileByDefault();

		log.info("<-<- Ending SrvPageAnalizerService.analizeSiteAndStored()");
	}

}
