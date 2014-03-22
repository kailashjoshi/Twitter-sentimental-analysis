package com.tweet.analysis;

import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

public class RunMe {
	/**
	 * Remove url from the string using regular expression
	 * 
	 * @param text
	 * @return URL free text
	 */
	private static String removeUrl(String text) {
		String url = "((https?|ftp|telnet|file|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Pattern pattern = Pattern.compile(url, Pattern.CASE_INSENSITIVE);
		Matcher match = pattern.matcher(text);
		int i = 0;
		while (match.find()) {
			text = text.replaceAll(match.group(i), "").trim();
			i++;
		}

		return removeHTMLChar(text);
	}

	/**
	 * Public tweets contains certain reserve character of HTML such as &amp,
	 * &quote This method cleans such HTMl reserve characters from the text
	 * using Regular Expression.
	 * 
	 * @param text
	 * @return
	 */

	private static String removeHTMLChar(String text) {

		return text.replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
				.replaceAll("&apos;", "'").replaceAll("&lt;", "<")
				.replaceAll("&gt;", ">");
	}

	/**
	 * Entry point for this program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		@SuppressWarnings({ "resource" })
		BeanFactory cntx = new GenericXmlApplicationContext(
				"mongo-context.xml");
		MongoOperations operation = cntx.getBean("mongoTemplate",
				MongoOperations.class);// Connects to mongodb

		FileWriter writer;
		try {
			writer = new FileWriter("output.txt");

			for (Object tweet : operation.getCollection("dataset").distinct(
					"text")) { //"dataset" parameter is the name of the collection
				               // "text" parameter is text record inside the Json object that we want to extract
				try {
					writer.write(RunMe.removeUrl(tweet.toString()) + "\n");// Clean															// file
				} catch (Exception e) {
					continue;
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Model buildModel = (Model)cntx.getBean("training"); //dependency injection
		buildModel.train(); // creating model to classify whether tweet is
							// positive or negative
		RunMe.startMapReduce();
	}

	/**
	 * This method starts map reduce job
	 */
	private static void startMapReduce() {
		@SuppressWarnings({ "resource", "unused" })
		ApplicationContext cntx = new GenericXmlApplicationContext(
				"hadoop-context.xml"); // starting MapReduce job
	}

}
