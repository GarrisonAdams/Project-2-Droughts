package org.group4;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
//import org.group4.struct.County;

public class bucketOperations {
  private static Dotenv dotenv = Dotenv.load();
  private static AWSCredentials credentials = new BasicAWSCredentials(dotenv.get("S3_ACCESS_KEY"),
      dotenv.get("S3_SECRET_KEY"));

  private static AmazonS3 s3client = AmazonS3ClientBuilder.standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();

  public static void datasetToBucket(Dataset<Row> dataRow, String bucketFileName, String fileName) throws IOException {
    turnIntoCSV(dataRow, fileName);
    putInBucket(bucketFileName, fileName);
  }

  private static void turnIntoCSV(Dataset<Row> dataRow, String fileName) throws IOException {

    BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));


    String columnList = String.join(",", dataRow.columns()).replace("(","").replace(")","");
    
    
    bw.append(columnList + "\n");

    for (Row row : dataRow.collectAsList()) 
    {

      bw.append(row.toString().replace("[","").replace("]","") + "\n");
    }

    bw.close();
  }

  private static void putInBucket(String nameOfBucketFile, String nameOfLocalFile) {

    s3client.putObject("usdroughtsbycountybucket", nameOfBucketFile, new File(nameOfLocalFile));

  }

  public static void getFromBucket(String nameOfBucketFile, String nameOfLocalFile) {
    try {
      // S3Object o =
      // s3client.getObject("arn:aws:s3:us-east-2:259915497964:accesspoint/usdroughts",
      // nameOfFile);
      S3Object o = s3client.getObject("usdroughtsbycountybucket", nameOfBucketFile);

      S3ObjectInputStream s3is = o.getObjectContent();
      FileOutputStream fos = new FileOutputStream(new File(nameOfLocalFile));
      byte[] read_buf = new byte[1024];
      int read_len = 0;
      while ((read_len = s3is.read(read_buf)) > 0) {
        fos.write(read_buf, 0, read_len);
      }
      s3is.close();
      fos.close();
    } catch (AmazonServiceException e) {
      System.err.println(e.getErrorMessage());
      System.exit(1);
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  public static ArrayList<String> getContentsOfBucket()
  {
    ArrayList<String> contentsOfBucket = new ArrayList<String>();
        ObjectListing objectListing = s3client.listObjects("usdroughtsbycountybucket");
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            contentsOfBucket.add(os.getKey());
        }
    
        return contentsOfBucket;

  }
}