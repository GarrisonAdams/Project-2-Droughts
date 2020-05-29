package org.group4;

import org.apache.spark.sql.SparkSession;
import org.group4.spark.sparkOperations;
import org.group4.struct.County;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Hello world!
 *
 */
public class App {
        public static void main(String[] args) throws AnalysisException, IOException {
                SparkSession session = SparkSession.builder().appName("spark-job").master("local").getOrCreate();
                Dataset<Row> countyData = session.read().option("header", "true").option("multiline", "true")
                                .option("sep", ",").option("inferSchema", "true").csv("us-droughts.csv").toDF();
                Dataset<Row> countyInfo = session.read().option("header", "true").option("multiline", "true")
                                .option("sep", ",").option("inferSchema", "true").csv("county_info_2016.csv").toDF();
                Dataset<County> countyMerged = countyData.join(countyInfo, "FIPS").drop("NAME")
                                .drop("domStatisticFormatID").drop("ANSICODE").drop("USPS")
                                .as(Encoders.bean(County.class)).persist();
                sparkOperations SparkOperations = new sparkOperations(countyMerged);
                LinkedHashMap<String, Dataset<Row>> dataStorageRow = SparkOperations.getDataStorageTypeRow();
                LinkedHashMap<String, Dataset<County>> dataStorageCounty = SparkOperations.getDataStorageTypeCounty();
                for (String key : dataStorageRow.keySet()) {
                        BucketOperations.datasetToBucket(dataStorageRow.get(key), key + ".csv", key + ".csv");
                }
                for (String key : dataStorageCounty.keySet()) {
                        BucketOperations.datasetToBucketCounty(dataStorageCounty.get(key), key + ".csv", key + ".csv");
                }
                session.close();
        }
}
