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
                SparkSession session = SparkSession.builder().appName("spark-job").getOrCreate();
                Dataset<Row> countyData = session.read().option("header", "true").option("multiline", "true")
                                .option("sep", ",").option("inferSchema", "true").csv("us-droughts.csv").toDF();
                Dataset<Row> countyInfo = session.read().option("header", "true").option("multiline", "true")
                                .option("sep", ",").option("inferSchema", "true").csv("county_info_2016.csv").toDF();
                Dataset<County> countyMerged = countyData.join(countyInfo, "FIPS").drop("NAME")
                                .drop("domStatisticFormatID").drop("ANSICODE").drop("USPS")
                                .as(Encoders.bean(County.class)).persist();
                sparkOperations SparkOperations = new sparkOperations(countyMerged);
                Dataset<Row> r = SparkOperations.getDataStorageTypeRow("yearWithMostDroughts");
                bucketOperations.datasetToBucket(r.coalesce(1),"outputYearWithMostDroughts.csv", "outputYearWithMostDroughts.csv");
                session.close();
        }
}
