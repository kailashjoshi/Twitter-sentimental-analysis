package com.tweet.analysis;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;

public class Classify {
	String[] categories;
	@SuppressWarnings("rawtypes")
	LMClassifier lmc;

	/**
	 * Constructor loading serialized object created by Model class to local
	 * LMClassifer of this class
	 */
	@SuppressWarnings("rawtypes")
	public Classify() {
		try {

			lmc = (LMClassifier) AbstractExternalizable.readObject(new File(
					"/Path/to/you/classify.txt/file/classifier.txt"));
			categories = lmc.categories();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Classify whether the text is positive or negative based on Model object
	 * 
	 * @param text
	 * @return classified group i.e either positive or negative
	 */
	public String classify(String text) {
		ConditionalClassification classification = lmc.classify(text);
		return classification.bestCategory();
	}
}
