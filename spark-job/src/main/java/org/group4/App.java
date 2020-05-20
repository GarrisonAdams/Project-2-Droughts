package org.group4;

import org.apache.spark.sql.SparkSession;

import io.github.cdimascio.dotenv.Dotenv;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.*;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws AnalysisException {
        // load bucket
        Dotenv dotenv = Dotenv.load();
        AWSCredentials credentials = new BasicAWSCredentials(dotenv.get("S3_ACCESS_KEY"), dotenv.get("S3_SECRET_KEY"));
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();
        List<Bucket> buckets = s3client.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }

        // arn:aws:s3:us-east-2:259915497964:accesspoint/usdroughts

        // S3Object s3object = s3client.getObject("project-2-bucket-droughts",
        // "csv/us-droughts.csv");
        // S3ObjectInputStream inputStream = s3object.getObjectContent();
        ObjectListing objectListing = s3client.listObjects("usdroughtsbycountybucket");
        for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
            System.out.println(os.getKey());
        }

        // initiate spark here
        // SparkSession session =
        // SparkSession.builder().appName("spark-job").master("local").getOrCreate();
        // Dataset<Row> s3Data = session.readStream().csv(s3object.getObjectContent())
        // Dataset<Row> data = session.read().json("users.json").cache();
        // Dataset<Row> data2 = session.read().option("header",
        // "true").option("multiline", "true").csv("us-droughts.csv").cache();
        // Dataset<Row> filteredRows = data2.filter(col("D4").gt(0));
        // Dataset<Row> numCounties = data2.groupBy("county").count();
        // System.out.println("num counties: " + numCounties.count());

        // Dataset<Row> data3 = data2.take(10);
        // data.show();

        // System.out.println("num d4 gt 0: " + filteredRows.count());
        // filteredRows.limit(20).show();

        // data2.createGlobalTempView("counties");
        // session.sql("select * from counties").show();
        // data.select("name").show();
        // only select statements?
        // data2.limit(10).printSchema();
        // session.close();
    }
}
