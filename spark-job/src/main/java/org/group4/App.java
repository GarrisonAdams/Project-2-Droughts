package org.group4;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.col;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws AnalysisException {
        SparkSession session = SparkSession.builder().appName("spark-job").master("local").getOrCreate();
        // Dataset<Row> data = session.read().json("users.json").cache();
        Dataset<Row> data2 = session.read().option("header", "true").option("multiline", "true").csv("us-droughts.csv")
                .cache();
        Dataset<Row> filteredRows = data2.filter(col("D4").gt(0));
        // Dataset<Row> data3 = data2.take(10);
        // data.show();
        System.out.println(filteredRows.count());
        filteredRows.limit(20).show();
        // data2.createGlobalTempView("counties");
        // session.sql("select * from counties").show();
        // data.select("name").show();
        // only select statements?
        // data2.limit(10).printSchema();
        session.close();
    }
}
