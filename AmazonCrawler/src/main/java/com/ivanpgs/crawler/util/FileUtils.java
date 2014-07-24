package com.ivanpgs.crawler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ivanpgs.crawler.model.bean.Product;
import com.ivanpgs.crawler.sync.PageParserThread;

/**
 * Class of utils create to help other classes to do different tasks.
 * 
 */
public class FileUtils {

	private static Log log = LogFactory.getLog(PageParserThread.class);

	/**
	 * Creates a file with tsv format
	 * 
	 * @param <T>
	 * @throws IOException
	 */
	public static void createTsv(List<Product> listToPrint) {

		BufferedWriter bw = null;
		try {
			log.info("->-> Starting method Utils.createTsv()");

			// title, description, price and URL
			File tsv = new File("Product.tsv");
			bw = new BufferedWriter(new FileWriter(tsv));
			bw.write("TITLE" + String.valueOf('\t') + "PRICE"
					+ String.valueOf('\t') + "URL" + String.valueOf('\t')
					+ "DESCRIPTION");
			bw.newLine();
			for (Product element : listToPrint) {
				bw.write(element.toTabulatedString());
				bw.newLine();
			}

			log.debug("TSV file written in the file referenced by path : "
					+ tsv.getAbsolutePath());

		} catch (IOException ioe) {

		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("->-> Ending method Utils.createTsv()");
		}

	}

}
