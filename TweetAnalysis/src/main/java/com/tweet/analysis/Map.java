package com.tweet.analysis;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	Classify classify = new Classify();

	/**
	 * Mapper which reads Tweets text file Store 
	 * as <"Positive",1> or <"Negative",1>
	 */
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();//streaming each tweet from the text file
		if (line != null) {
			word.set(classify.classify(line)); //invoke classify class to get tweet group of each text
			context.write(word, one);
		} else {
			word.set("Error");
			context.write(word, one);//Key,value for Mapper
		}
	}

}
