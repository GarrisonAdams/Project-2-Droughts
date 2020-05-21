package org.group4;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;


public class BucketOperations {
    private static Dotenv dotenv = Dotenv.load();
    private static AWSCredentials credentials = new BasicAWSCredentials(dotenv.get("S3_ACCESS_KEY"), dotenv.get("S3_SECRET_KEY"));
    
     private static AmazonS3 s3client = AmazonS3ClientBuilder
      .standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .withRegion(Regions.US_EAST_2)
      .build();


    public static void datasetToBucket(Dataset<Row> dataRow, String fileName) throws IOException {
        turnIntoCSV(dataRow,fileName);
        putInBucket(fileName);
    }
    
    private static void turnIntoCSV(Dataset<Row> dataRow, String fileName) throws IOException {
      BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

      for(Row row : dataRow.collectAsList())
      {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < row.length(); i++)
        {
            if(i == row.length())
            {
                s.append(row.getString(i));
            }
            else
            {
                s.append(row.getString(i) + ",");
            }
        }

        bw.append(s + "\n");
      }

      bw.close();
    }

    private static void putInBucket(String nameOfFile) {
      s3client.putObject("arn:aws:s3:us-east-2:259915497964:accesspoint/usdroughts", nameOfFile, nameOfFile);
    }

    public static void getFromBucket(String nameOfFile) {
        try {
          S3Object o = s3client.getObject("arn:aws:s3:us-east-2:259915497964:accesspoint/usdroughts", nameOfFile);
          S3ObjectInputStream s3is = o.getObjectContent();
          FileOutputStream fos = new FileOutputStream(new File(nameOfFile));
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
}