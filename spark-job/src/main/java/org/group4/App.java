package org.group4;

import java.io.IOException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
//import org.group4.spark.sparkOperations;
//import org.group4.struct.County;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws IOException {
    
        Dotenv dotenv = Dotenv.load();
        AWSCredentials credentials = new BasicAWSCredentials(dotenv.get("S3_ACCESS_KEY"),
            dotenv.get("S3_SECRET_KEY"));

       // arn:aws:s3:us-east-2:259915497964:accesspoint/usdroughts
      
      System.out.println("*******************************************************************************************");
      SparkSession session = SparkSession.builder().master("local[*]").appName("spark-job").getOrCreate();
      System.out.println("*******************************************************************************************");
     // bucketOperations.getFromBucket("us-droughts.csv","us-droughts.csv");
      System.out.println("Hopefully bucket got something");
      System.out.println("*******************************************************************************************");
      Dataset<Row> countyData = session.read().option("header", "true").csv("s3://usdroughtsbycountybucket/us-droughts.csv");
       countyData.createOrReplaceTempView("droughts");      
       System.out.println("*******************************************************************************************");
       session.sql("Select county,state,NONE,D0,D1,D2,D3,D4 FROM droughts WHERE D4 > 50 AND state = 'CA'").limit(50).show();
       System.out.println("*******************************************************************************************");

    
    
       //   bucketOperations.datasetToBucket(dataDisplay, "Datadisplay3", "dataDisplay.csv");

       session.close();
       
    }
}
