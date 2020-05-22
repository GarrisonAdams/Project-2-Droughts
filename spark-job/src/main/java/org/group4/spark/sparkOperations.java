package org.group4.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.group4.struct.County;

import static org.apache.spark.sql.functions.*;

public class sparkOperations {
  Dataset<County> counties;
  private Dataset<County> countyOutput;

  public sparkOperations(Dataset<County> counties) {
    this.counties = counties;
    // countyOutput = countDrought(counties, )
  }

  public Dataset<County> getData() {
    return countyOutput;
  }

  public Dataset<County> countDrought(Dataset<County> counties, String droughtLevel) {
    return counties.filter(col(droughtLevel).gt(0));
  }

  public Dataset<Row> countCounties(Dataset<County> counties) {
    return counties.groupBy("county").count();
  }

  public Dataset<County> specificCountyData(Dataset<County> counties, String countyName) {
    return counties.filter(col("county").equalTo(countyName));
  }

  public Dataset<County> specificStateData(Dataset<County> counties, String stateName) {
    return counties.filter(col("state").equalTo(stateName));
  }

  public Dataset<County> averageDrought(Dataset<County> counties) {
    return counties;
  }
}