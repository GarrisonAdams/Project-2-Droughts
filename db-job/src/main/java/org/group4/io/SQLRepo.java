package org.group4.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.group4.bucketOperations;

public class SQLRepo {
  static Connection connection = null;
  static PreparedStatement stmt = null;

  public static void insertIntoDatabase(String bucketFile, String tableName) throws SQLException, IOException {

    bucketOperations.getFromBucket(bucketFile, bucketFile);

    FileReader fr = new FileReader(new File(bucketFile));
    BufferedReader br = new BufferedReader(fr);

    connection = DatabaseConnector.getConnection();

    String[] columnList = br.readLine().split(",");
    stmt = connection.prepareStatement(createTableQuery(columnList, tableName));
    stmt.executeUpdate();

    String line;

    while ((line = br.readLine()) != null) {

      String[] rowEntry = line.split(",");
      stmt = connection.prepareStatement(createInsertQuery(rowEntry, columnList, tableName));
      stmt.executeUpdate();

    }
    br.close();

  }

  public static String createTableQuery(String[] columnList, String tableName) {

    StringBuilder s = new StringBuilder();

    s.append("create table " + tableName + "(");
    for (int i = 0; i < columnList.length; i++) {
      if (i == columnList.length - 1) {
        s.append(columnList[i] + " " + getColumnDataType(columnList[i]) + " );");
      } else {
        s.append(columnList[i] + " " + getColumnDataType(columnList[i]) + ",\n");
      }
    }
    System.out.println(s);
    return s.toString();
  }

  public static String createInsertQuery(String[] rowEntry, String[] columnList, String tableName) {
    StringBuilder s = new StringBuilder();

    s.append("INSERT INTO " + tableName + " VALUES (");

    for (int i = 0; i < rowEntry.length; i++) {
      if (i == rowEntry.length - 1) {
        if (getColumnDataType(columnList[i]).equals("VARCHAR") || getColumnDataType(columnList[i]).equals("DATE")) {
          s.append("'" + rowEntry[i] + "'" + ");");
        } else {
          s.append(rowEntry[i] + ");");
        }
      } else {
        if (getColumnDataType(columnList[i]).equals("VARCHAR") || getColumnDataType(columnList[i]).equals("DATE")) {
          s.append("'" + rowEntry[i] + "'" + ", ");
        } else {
          s.append(rowEntry[i] + ", ");
        }

      }
    }
    System.out.println(s);
    return s.toString();
  }

  public static String printDatabase(String table) throws SQLException {
    connection = DatabaseConnector.getConnection();
    String sql = "SELECT * FROM " + table; // Our SQL query
    stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
    ResultSet rs = stmt.executeQuery(); // Queries the database

    StringBuffer string = new StringBuffer();

    string.append("Printing " + table + "\n");
    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
      if (i == rs.getMetaData().getColumnCount())
        string.append(rs.getMetaData().getColumnName(i) + "\n");
      else
        string.append(rs.getMetaData().getColumnName(i) + " ");
    }

    while (rs.next()) {
      for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
        if (i == rs.getMetaData().getColumnCount())
          string.append(rs.getArray(i) + "\n");
        else
          string.append(rs.getArray(i) + " ");
    }

    return string.toString();

  }

  public static String getColumnDataType(String columnName) {
    String[] doubleColumns = { "NONE", "D0", "D1", "D2", "D3", "D4", "ALAND", "AWATER", "AWATER_SQMI", "ALAND_SQMI",
        "INTPTLAT", "INTPTLONG" };
    String[] dateColumns = { "releaseDate", "validStart", "validEnd" };

    if (Arrays.asList(doubleColumns).contains(columnName)) {
      return "DOUBLE PRECISION";
    } else if (Arrays.asList(dateColumns).contains(columnName)) {
      return "DATE";
    } else {
      return "VARCHAR";
    }
  }
}