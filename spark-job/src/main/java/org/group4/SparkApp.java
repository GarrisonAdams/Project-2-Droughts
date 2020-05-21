package org.group4;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;



import java.io.IOException;


/**
 * Hello world!
 *
 */
public class SparkApp {
  public static void main(String[] args) throws AnalysisException, IOException {

        SparkSession session = SparkSession.builder().appName("spark-job").master("local").getOrCreate();

        Dataset<Row> data2 = session.read().option("header", "true").option("multiline", "true").csv("us-droughts.csv")
                .cache();


        BucketOperations.datasetToBucket(data2.limit(25), "new.csv");
        BucketOperations.getFromBucket("HelloText.txt");

        session.close();

      }

      
 
}
