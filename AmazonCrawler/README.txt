============================
AmazonCrawler application
============================

What is it?
-----------

It is an Java test received from the company Become Japan (http://www.become.co.jp/) for a Senior Java position.

'A Web crawler is a program that explores the Web by reading Web pages and following the links it finds on 
them to other pages, from which it extracts more links to follow, and so forth. A typical use of a Web crawler
is to add pages to a search service's database -- using a crawler to find pages automatically allows the search 
service to build a much larger database than would be possible if people had to identify pages and add them manually.'

Test:

- Write a Java program that will crawl and scrape product information from amazon.com . 
- Collected data should be stored in a relational database (mysql) or any choice of NoSQL DB, where each product 
will be represented with a title, description, price and URL.
- Collected data should also be available in a form of a product data feed as output. 

The feed should be in TSV format with the following columns: product title, description, price, URL
(Hint: use of seed urls, bread-first vs depth first, separation of crawler vs scraper, what sort of queuing 
mechanism used, etc.)

Application Structure
----------------------


The Latest Version
------------------


Documentation
-------------

The documentation included is an in-class documentation and this README.

Known bugs
----------

A book title and description with five different prices (with different id though).
Should implement a parseSingleProduct() and parseComplexProduct()

http://www.amazon.com/Business-Adventures-Twelve-Classic-Street/dp/1497644895

TODO Things
-----------


Installation/Execution
----------------------

For the execution just download the jar file called ProgrammingTestUzabase.jar to a directory 
and then execute the next command in the command line:

java -jar ....jar

Pre-requisites: You need Java installed in your computer.
(Note that I have compiled and executed the application with the Java 1.7 version, so
it might be needed this version to execute the application) Or just compile the files with
another version and then execute the application.

Git Access
----------

https://github.com/ivanpgs

Contacts
--------

If there is any bug of just want to contact me:

You can contact me at: ivanpgs@gmail.com
I am also available at Linked-in: https://www.linkedin.com/profile/view?id=153419800

I have also a blog (written in Spanish) about Japan called http://japabanchel.com
