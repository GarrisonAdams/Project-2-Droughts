package org.group4;

import org.apache.spark.sql.SparkSession;
import org.group4.spark.sparkOperations;
import org.group4.struct.County;
import org.group4.struct.CountyInfo;

import io.github.cdimascio.dotenv.Dotenv;

import org.apache.commons.io.FileUtils;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
        public static void main(String[] args) throws AnalysisException, IOException {
                // load bucket
                // Dotenv dotenv = Dotenv.load();
                // AWSCredentials credentials = new
                // BasicAWSCredentials(dotenv.get("S3_ACCESS_KEY"),
                // dotenv.get("S3_SECRET_KEY"));
                // AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                // .withCredentials(new
                // AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();
                // List<Bucket> buckets = s3client.listBuckets();
                // for (Bucket bucket : buckets) {
                // System.out.println(bucket.getName());
                // }

                // arn:aws:s3:us-east-2:259915497964:accesspoint/usdroughts

                // S3Object s3object = s3client.getObject("project-2-bucket-droughts",
                // "csv/us-droughts.csv");
                // S3ObjectInputStream inputStream = s3object.getObjectContent();
                // ObjectListing objectListing =
                // s3client.listObjects("usdroughtsbycountybucket");
                // for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
                // System.out.println(os.getKey());
                // }
                // S3Object fileContent = s3client.getObject("usdroughtsbycountybucket",
                // "output/output.csv");
                // S3ObjectInputStream inputStream = fileContent.getObjectContent();
                // FileUtils.copyInputStreamToFile(inputStream, new
                // File("spark-job/src/main/resources/downloadedTextFile.csv"));
                // initiate spark here
                SparkSession session = SparkSession.builder().appName("spark-job").master("local").getOrCreate();
                // // Dataset<Row> s3Data =
                // session.readStream().csv(s3object.getObjectContent())
                // // Dataset<Row> data = session.read().json("users.json").cache();
                // Dataset<Row> data2 = session.read().option("header",
                // "true").option("multiline", "true")
                // .csv("us-droughts.csv").cache();
                Dataset<Row> countyData = session.read().option("header", "true").option("multiline", "true")
                                .option("sep", ",").option("inferSchema", "true").csv("us-droughts.csv").toDF();

                Dataset<Row> countyInfo = session.read().option("header", "true").option("multiline", "true")
                                .option("sep", ",").option("inferSchema", "true").csv("county_info_2016.csv").toDF();

                Dataset<County> countyMerged = countyData.join(countyInfo, "FIPS").drop("NAME")
                                .drop("domStatisticFormatID").drop("ANSICODE").drop("USPS")
                                .as(Encoders.bean(County.class)).persist();
                // System.out.println(countyMerged.count());
                sparkOperations SparkOperations = new sparkOperations(countyMerged);

                // countyData.groupBy("county").count().limit(105).show();
                // countyData.printSchema();
                // SparkOperations.getDataStorageTypeCounty("countDrought").show();
                SparkOperations.getDataStorageTypeRow("yearWithMostDroughts").limit(20).show();

                // bucketOperations.datasetToBucket(SparkOperations.countCounties(countyData.limit(50));

                // "output.csv",
                // "spark-job/src/main/resources/textFileFromSparkJob.csv");
                // bucketOperations.getFromBucket("output/output.csv",
                // "spark-job/src/main/resources/downloadedTextFile2.csv");

                // String stringFromData2 = data2.showString(25, 999, false);
                // BufferedWriter bw = new BufferedWriter(new
                // FileWriter("spark-job/src/main/resources/textFileFromData.csv"));
                // bw.write(stringFromData2);
                // bw.close();

                // Dataset<Row> filteredRows = data2.filter(col("D4").gt(0));
                // Dataset<Row> numCounties = data2.groupBy("county").count();
                // System.out.println("num counties: " + numCounties.count());

                // Dataset<Row> data3 = data2.take(10);
                // data.show();

                // System.out.println("num d4 gt 0: " + filteredRows.count());
                // filteredRows.limit(20).show();

                // s3client.putObject("usdroughtsbycountybucket", "output/output.csv",
                // new File("spark-job/src/main/resources/myTextFile.csv"));

                // s3client.putObject("arn:aws:s3:us-east-2:259915497964:accesspoint/usdroughts",
                // "output/output.csv",
                // new File("spark-job/src/main/resources/myTextFile.csv"));

                // data2.createGlobalTempView("counties");
                // session.sql("select * from counties").show();
                // data.select("name").show();
                // only select statements?
                // data2.limit(10).printSchema();
                session.close();
        }
}
