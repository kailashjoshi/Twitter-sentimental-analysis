package com.tweet.analysis;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
	/**
	 * Count the frequency of each classified text group
	 */
	public void reduce(Text key, Iterable<IntWritable> classifiedText,
			Context context) throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable classification : classifiedText) {
			sum += classification.get(); //Sum the frequency
		}
		context.write(key, new IntWritable(sum));
	}
}
