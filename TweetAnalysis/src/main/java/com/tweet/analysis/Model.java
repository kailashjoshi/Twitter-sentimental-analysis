package com.tweet.analysis;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.corpus.ObjectHandler;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Compilable;
import com.aliasi.util.Files;

public class Model {
	/**
	 * Train the model based on training dataset
	 *  Serialize the model and save it as classifier.txt file
	 */
	@SuppressWarnings("unchecked")
	public void train() {
		File trainDir;
		String[] categories;
		@SuppressWarnings("rawtypes")
		LMClassifier lmc; //Language model classifier
		trainDir = new File("trainDirectory"); //Directory of training dataset
		categories = trainDir.list();
		int nGram = 7; // the nGram level, any value between 7 and 12 works
		String[] mCategories = { "Positive", "Negative" }; //categories of the text i.e negative and positive text

		lmc = DynamicLMClassifier.createNGramProcess(mCategories, nGram);

		for (int i = 0; i < categories.length; ++i) {
			String category = categories[i];
			Classification classification = new Classification(category);
			File file = new File(trainDir, categories[i]);
			File[] trainFiles = file.listFiles();
			for (int j = 0; j < trainFiles.length; ++j) {
				File trainFile = trainFiles[j];
				String review = null;
				try {
					review = Files.readFromFile(trainFile, "ISO-8859-1");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Classified<String> classified = new Classified<String>(review,
						classification);
				((ObjectHandler<Classified<String>>) lmc).handle(classified);
			}
		}
		try {
			AbstractExternalizable.compileTo((Compilable) lmc, new File(
					"classifier.txt"));//saving serialize object to text file
			System.out.println("Succefully created a model");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
