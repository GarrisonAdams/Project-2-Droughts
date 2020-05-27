package org.group4;

import java.io.IOException;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
//import org.group4.spark.sparkOperations;
//import org.group4.struct.County;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws IOException {
    


       // arn:aws:s3:us-east-2:259915497964:accesspoint/usdroughts
      
      bucketOperations.getFromBucket("us-droughts.csv","us-droughts.csv");
      SparkSession session = SparkSession.builder().appName("spark-job").getOrCreate();

      Dataset<Row> countyData = session.read().option("header", "true").option("multiline", "true").csv("us-droughts.csv");
      countyData.createOrReplaceTempView("droughts");      
      Dataset<Row> dataDisplay = session.sql("Select county,state,NONE,D0,D1,D2,D3,D4 FROM droughts WHERE D4 > 50 AND state = 'CA'");
      //bucketOperations.datasetToBucket(dataDisplay, "Datadisplay3", "dataDisplay.csv");

       session.close();
       
    }
}
