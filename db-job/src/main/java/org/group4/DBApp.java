package org.group4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;


/**
 * Hello world!
 *
 */
public class DBApp 
{
    public static void main(String[] args)
    {
            
        AWSCredentials credentials = new BasicAWSCredentials(
            "AKIAJ76SGDCKPOA2UF3A" , 
          "0gnbSdfSTbl8hXjyh/AR1fMHvfNlbjGrAhFVG3yh "
        );
        
         AmazonS3 s3client = AmazonS3ClientBuilder
          .standard()
          .withCredentials(new AWSStaticCredentialsProvider(credentials))
          .withRegion(Regions.US_EAST_2)
          .build();

        List<Bucket> buckets = s3client.listBuckets();
        for(Bucket bucket : buckets) 
        {
            System.out.println(bucket.getName());
        }

        //s3client.putObject("arn:aws:s3:us-east-2:259915497964:accesspoint/usdroughts", "HelloText", new File("C:\\Users\\Garrison\\Desktop\\hello.txt"));

        try {
          S3Object o = s3client.getObject("arn:aws:s3:us-east-2:259915497964:accesspoint/usdroughts", "HelloText");
          S3ObjectInputStream s3is = o.getObjectContent();
          FileOutputStream fos = new FileOutputStream(new File("HelloText.txt"));
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
