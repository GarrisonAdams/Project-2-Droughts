package org.group4.io;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class SQLRepo {
  public static void insertIntoSQLDatabase(String countyData) throws SQLException {
    try (Connection conn = SQLSource.getConnection();) {
      Statement statement = conn.createStatement();

    }
  }
}