package com.ivanpgs.crawler.common.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ivanpgs.crawler.common.interfaces.Connection;
import com.ivanpgs.crawler.common.interfaces.Parser;

/**
 * 
 * Class used to establish a HTML connection using the org.apache.httpcomponent
 * library and getting a URL Response returning the String comprising an URL
 * site.
 * 
 * Also implements Parser interface to get a Document structure from the String
 * representation of a site/page. (The parser used is JSoup)
 * 
 * @author Ivan Poza
 * 
 */
public class HTMLParser implements Parser, Connection {

	private final Log log = LogFactory.getLog(HTMLParser.class);
	// Atrribute to save the baseURI of the page to be parsed
	private String baseURI = null;

	public String getBaseURI() {
		return baseURI;
	}

	/**
	 * Method that receiving a document within a Sting return a DOM Document
	 * object
	 * 
	 * @param documentToParse
	 *            - A string containing a parseable document
	 * @return
	 */
	@Override
	public Document parse(String documentToParse) {
		// log.info("->-> Starting method HTMLParser.parse()");
		Document doc = Jsoup.parse(documentToParse);
		// log.info("<-<- Ending method HTMLParser.parse()");
		return doc;
	}

	/**
	 * Method that receiving a URL within connects to the URL, gets the content
	 * and parse it as DOM Document object
	 * 
	 * @param urlToParse
	 *            - The URL of the site to be parsed
	 * @return
	 */
	@Override
	public Document parseURL(String urlToParse) {
		// log.info("->-> Starting method HTMLParser.parseURL()");
		String documentToParse = connect(urlToParse);
		Document doc = Jsoup.parse(documentToParse);
		// log.info("<-<- Ending method HTMLParser.parseURL()");
		return doc;
	}

	/**
	 * Method that given a String containing an URL connects to the URL and
	 * retrieve the HTML
	 * 
	 * (1) Using Jsoup for the HTTP connection [ Document doc =
	 * Jsoup.connect(getUrl()).get(); ] leads to error
	 * "Too many redirects occurred trying to load URL" while connecting to some
	 * https pages. That's the reason to use Apache HTTPComponents library for
	 * the HTTP Connection
	 * 
	 * (1) Another problem occurred getting the error
	 * org.apache.http.client.CircularRedirectException: Circular redirect to
	 * 'https://www.amazon.com/...', so I set the circular redirect parameter to
	 * true to avoid skipping pages.
	 * 
	 * @see http://qiita.com/mychaelstyle/items/e02b3011d1e71bfa26c5
	 * 
	 * @param the
	 *            content of the url
	 * @return a String containing the page contents
	 */
	@Override
	public String connect(String ulrToConnect) {
		// log.info("->-> Starting method HTMLParser.connect()");
		String responseBody = "";

		// Allowing redirects to avoid CircularRedirectException
		RequestConfig config = RequestConfig.custom()
				.setCircularRedirectsAllowed(true).setMaxRedirects(5).build();
		CloseableHttpClient httpclient = HttpClientBuilder.create()
				.setDefaultRequestConfig(config).build(); // Custom HttpClient
		// CloseableHttpClient httpclient = HttpClients.createDefault(); //
		// Default HttpClient
		try {
			HttpGet httpget = new HttpGet(ulrToConnect);

			log.debug("Executing HTTP GET request : " + httpget.getURI());
			setBaseURI(httpget.getURI().toString());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			};
			try {
				responseBody = httpclient.execute(httpget, responseHandler);
			} catch (ClientProtocolException e) {
				log.error("ClientProtocolException at method HTMLParser.connect() : "
						+ e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				log.error("IOException at method HTMLParser.connect() : "
						+ e.getMessage());
				e.printStackTrace();
			}

		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				log.error("IOException at method HTMLParser.connect() : "
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		// log.info("<-<- Ending method HTMLParser.connect()");
		return responseBody;
	}

	/**
	 * Method that given an absolute URL gets the baseURI and set it into an
	 * atribute
	 * 
	 * @param uri
	 */
	private void setBaseURI(String uri) {
		// log.info("->-> Ending method HTMLParser.setBaseURI()");
		if (null != uri) {
			int initialIndex = uri.indexOf("//");
			if (initialIndex != -1) {
				int endIndex = uri.indexOf("/", initialIndex + "//".length());
				if (endIndex != -1) {
					baseURI = uri.substring(0, endIndex);
				}
			}
		}
		// log.info("<-<- Ending method HTMLParser.setBaseURI()");
	}

}
