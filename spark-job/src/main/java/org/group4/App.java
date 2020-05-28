package org.group4;

import org.apache.spark.sql.SparkSession;
import org.group4.spark.sparkOperations;
import org.group4.struct.County;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
        public static void main(String[] args) throws AnalysisException, IOException {
                // SparkSession session =
                // SparkSession.builder().appName("spark-job").getOrCreate();
                BucketOperations.getFromBucket("usdroughtsbycountydata", "us-droughts.csv");
                BucketOperations.getFromBucket("usdroughtsbycountydata", "county_info_2016.csv");
                SparkSession session = SparkSession.builder().appName("spark-job").master("local").getOrCreate();
                Dataset<Row> countyData = session.read().option("header", "true").option("multiline", "true")
                                .option("sep", ",").option("inferSchema", "true").csv("us-droughts.csv").toDF();
                Dataset<Row> countyInfo = session.read().option("header", "true").option("multiline", "true")
                                .option("sep", ",").option("inferSchema", "true").csv("county_info_2016.csv").toDF();
                Dataset<County> countyMerged = countyData.join(countyInfo, "FIPS").drop("NAME")
                                .drop("domStatisticFormatID").drop("ANSICODE").drop("USPS")
                                .as(Encoders.bean(County.class)).persist();
                sparkOperations SparkOperations = new sparkOperations(countyMerged);
                Dataset<Row> r = SparkOperations.getDataStorageTypeRow("waterToLandRatioState");
                // BucketOperations.datasetToBucket(r.coalesce(1),
                // "outputYearWithMostDroughts.csv",
                // "outputYearWithMostDroughts.csv");
                r.printSchema();
                r.show();
                session.close();
        }
}
