package org.group4.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.group4.struct.County;

import static org.apache.spark.sql.functions.*;

import java.util.LinkedHashMap;

public class sparkOperations {
  Dataset<County> counties;
  LinkedHashMap<String, Dataset<County>> dataStorageTypeCounty = new LinkedHashMap<>();
  LinkedHashMap<String, Dataset<Row>> dataStorageTypeRow = new LinkedHashMap<>();

  public sparkOperations(Dataset<County> counties) {
    this.counties = counties;
    // dataStorageTypeCounty.put("countDrought", countDrought(counties, "D4"));
    // dataStorageTypeRow.put("avgDroughtCol", avgDroughtCol(counties, "D3"));
    // dataStorageTypeRow.put("avgDrought", avgDrought(counties));
    // dataStorageTypeRow.put("waterToLandRatio", waterToLandRatio(counties));
    // dataStorageTypeRow.put("yearWithMostDroughts",
    // yearWithMostDroughts(counties));

    dataStorageTypeRow.put("waterToLandRatioState", waterToLandRatioState(counties));
  }

  public LinkedHashMap<String, Dataset<County>> getDataStorageTypeCounty() {
    return dataStorageTypeCounty;
  }

  public Dataset<County> getDataStorageTypeCounty(String key) {
    return dataStorageTypeCounty.get(key);
  }

  public void setDataStorageTypeCounty(LinkedHashMap<String, Dataset<County>> dataStorageTypeCounty) {
    this.dataStorageTypeCounty = dataStorageTypeCounty;
  }

  public LinkedHashMap<String, Dataset<Row>> getDataStorageTypeRow() {
    return dataStorageTypeRow;
  }

  public Dataset<Row> getDataStorageTypeRow(String key) {
    return dataStorageTypeRow.get(key);
  }

  public void setDataStorageTypeRow(LinkedHashMap<String, Dataset<Row>> dataStorageTypeRow) {
    this.dataStorageTypeRow = dataStorageTypeRow;
  }

  //
  public Dataset<County> countDrought(Dataset<County> counties, String droughtLevel) {
    return counties.filter(col(droughtLevel).gt(0));
  }

  // 2000 rows
  public Dataset<Row> countCounties(Dataset<County> counties) {
    return counties.groupBy("county").count();
  }

  // <800 rows
  public Dataset<County> specificCountyData(Dataset<County> counties, String countyName) {
    return counties.filter(col("county").equalTo(countyName));
  }

  // <2000? rows
  public Dataset<County> specificStateData(Dataset<County> counties, String stateName) {
    return counties.filter(col("state").equalTo(stateName));
  }

  // 2000 rows data
  public Dataset<Row> avgDroughtCol(Dataset<County> counties, String droughtLevel) {
    return counties.groupBy("county").avg(droughtLevel);
  }

  // 2000 rows data
  public Dataset<Row> avgDrought(Dataset<County> counties) {
    return counties.groupBy("county", "state", "fips").avg("D0", "D1", "D2", "D3", "D4");
  }

  // 2000 rows data
  public Dataset<Row> waterToLandRatioCounty(Dataset<County> counties) {
    return counties.withColumn("waterToLandRatio", counties.col("AWATER_SQMI").divide(counties.col("ALAND_SQMI")))
        .groupBy("county").count();
  }

  // 50 rows data
  public Dataset<Row> waterToLandRatioState(Dataset<County> counties) {
    return counties.withColumn("waterToLandRatio", counties.col("AWATER_SQMI").divide(counties.col("ALAND_SQMI")))
        .groupBy("state").count();
  }
  // where do the droughts occur

  // what states get droughts the most often 50 rows data
  public Dataset<Row> mostDroughtStates(Dataset<County> counties) {
    return counties.groupBy("state").avg("D0", "D1", "D2", "D3", "D4");
  }

  // year with most droughts 16 rows of data
  public Dataset<Row> yearWithMostDroughts(Dataset<County> counties) {
    return counties.groupBy(year(col("releaseDate"))).avg("D0", "D1", "D2", "D3", "D4");
  }

  // public Dataset<County>

}