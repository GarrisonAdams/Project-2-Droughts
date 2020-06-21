package org.group4;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.group4.io.SQLRepo;

//import org.apache.spark.sql.SparkSession;
//import org.group4.struct.County;

/**
 * Hello world!
 *
 */
public class App {
    static Connection connection = null;
    static PreparedStatement stmt = null;

    public static void main(String[] args) throws IOException, SQLException {

        // SparkSession session =
        // SparkSession.builder().master("local[*]").appName("spark-job").getOrCreate();

        // Dataset<County> countyData = session.read().option("header",
        // "true").option("multiline", "true")
        // .csv("us-droughts.csv").as(Encoders.bean(County.class)).persist();
        // countyData.createOrReplaceTempView("droughts");
        // Dataset<Row> dataDisplay =
        // session.sql("Select county,state,NONE,D0,D1,D2,D3,D4 FROM droughts WHERE D3 >
        // 50 AND state = 'CA'").limit(50);

        // bucketOperations.datasetToBucket(dataDisplay, "testfile2.csv",
        // "testfile2.csv");

        ArrayList<String> contents = BucketOperations.getContentsOfBucket();
        System.out.println(contents);

        for (String s : contents) {
            System.out.println("Inside" + s);
            if (s.substring(s.length() - 4, s.length()).equals(".csv") && !s.equals("us-droughts.csv")
                    && !s.equals("county_info_2016.csv")) {
                System.out.println(!s.equals("us-droughts.csv"));
                System.out.println(!s.equals("county_info_2016.csv"));
                System.out.println("Inside " + s + " insertIntoDatabase");
                SQLRepo.insertIntoDatabase(s, s.substring(0, s.length() - 4));
                System.out.println(SQLRepo.printDatabase(s.substring(0, s.length() - 4)));
            }
        }
        // session.close();
    }
}
