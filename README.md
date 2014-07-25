============================
AmazonCrawler application
============================

What is it?
-----------

A Web crawler is a program that explores the Web by reading Web pages and 
following the links it finds on them to other pages, from which it extracts more 
links to follow, and so forth. A typical use of a Web crawler is to add pages to
a search service's database -- using a crawler to find pages automatically 
allows the search service to build a much larger database than would be possible
if people had to identify pages and add them manually.

I started developing this application as a part of a test for a japanese company
called Become.jp with the following rules:

- Write a Java program that will crawl and scrape product information from 
amazon.com . 
- Collected data should be stored in a relational database (mysql) or any choice 
of NoSQL DB, where each product 
will be represented with a title, description, price and URL.
- Collected data should also be available in a form of a product data feed as 
output. 
- The feed should be in TSV format with the following columns: product title, 
description, price, URL (Hint: use of seed urls, bread-first vs depth first, 
separation of crawler vs scraper, what sort of queuing mechanism used, etc.)

Application Structure
----------------------

[+] src/main/java/com/ivanpgs/crawler
 ---[+] common/		Directory containing different Enum classes defining the 
			existing kind of Products, Categories and Atrributes.
 ---[+] common/interfaces 	Different interfaces used in the application
				like Parser, PageAnalizer, Connection,...
 ---[+] common/impl 	Some of the implementation of the above interfaces
 ---[+] model/bean	Plain Objects used to store data (such as Product.java)
 ---[+] model/dao	Data Access Object (Like ProductDao.java)
 ---[+] model/dao/impl	Implementation of the Data Access Object
 ---[+] model/dao/rowmapper	Objects to map from the stored data to plain 
				objects like Product.java.
 ---[+] model/service/interface Service contracts / interfaces
 ---[+] model/service/impl	Implementations of the services used in the app
				like SrvProductService or SrvPageAnalizerService
 ---[+] model/service/produceconsumer	Several classes that helps build a
			Producer - Consumer Pattern. Producers are in charge of
			adding Products to a queue while the Consumers will
			parse this data building a list of Products.
			(This branch could be moved to the common.impl brand)
 ---[+] runnable	Directory where it is the executable class of the
			project and also the xml containing the app. context.
			(Maybe it's better to rename to 'executable' package)
 ---[+] sync	Contains the Producer and Consumer main threads,
		PageAnalizerThread (Producer) and PageParserThread (Consumer).
		The behaviour is implemented in the run method (Runnable tasks)
 ---[+] utils	Directory with some class utilities.


The Latest Version
------------------

It has no version or snapshot created.

Documentation
-------------

The documentation included is an in-class documentation and this README.

Known bugs
----------

There are several things to solve:

Bug.001 - The class HTMLParser.java throws ClientProtocolException caused by
org.apache.http.client.CircularRedirectException: Circular redirect to <webUrl>.
where <webURL> is the absolute URL of the website.

It is happening with several web URLs, so I did config the HMTLParser with a
redirect option to try at least 5 times, but it is still happening. If I try the
same URLs in the browser there will be loaded withouh any problem.

It could be a problem of the library org.apache.httpcomponents:
http://stackoverflow.com/questions/6698214/avoid-circular-redirect-using-httpclient-4-1-1

TODO Things
-----------

TODO.001 - 

When parsing a new Product (PageParserThread.run() method). When calling the
pageAnalizer.populateProductElementAttributes(...) method.

When parsing a new Product from an URL it might happen that within that URL
there are different versions of the Product itself.

E.g: A book title and description with five different prices (with different 
id though). A book could be sold as a CD Audio, as a book itself, in electronic
format, ... Should implement a parseSingleProduct() and parseComplexProduct()
or just add new Products to the Product Queue in order to parse them later,
because every product has a different Amazon Product ID.

An example URL is:

http://www.amazon.com/Business-Adventures-Twelve-Classic-Street/dp/1497644895

TODO.002 - Create a basic Exception object to handle any raising exception in
the application, that will wrap the Java Exception and will add more useful
information. Then, implement a throwing/catching policy of the exceptions.

A good way to starts could be handling the Bug.001 Connection exceptions.

TODO.003 - 

Tests for the different module of the application in order to properly test the
different modules.

Installation/Execution
----------------------

For the execution just create a Maven project in your favorite IDE, download
the maven libraries (there is a pom.xml) abd then Run the class
AmazonProductWebCrawler.java as an application.

Pre-requisites: You need Java installed in your computer.
(Note that I have compiled and executed the application with the Java 1.7 
version, so it might be needed this version to execute the application) 
Or just compile the files with another version and then execute the application.

Git Access
----------

https://github.com/ivanpgs

Contacts
--------

If there is any bug, improvement that you might thing is worth doing contact me
at:

ivanpgs@gmail.com
https://www.linkedin.com/profile/view?id=153419800

Off-Topic
---------

I have also a blog (written in Spanish) about Japan called http://japabanchel.com
