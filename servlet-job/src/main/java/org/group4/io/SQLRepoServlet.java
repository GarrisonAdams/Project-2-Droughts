package org.group4.io;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLRepoServlet {
  public static ArrayList<String> readSQL(String table, String level, String limit) throws SQLException {
    ArrayList<String> output = new ArrayList<String>();
    String query = "select * from " + table + " order by cast(avgD" + level + " as decimal(18,3)) desc limit " + limit;
    String s = "";
    try (Connection conn = DatabaseConnector.getConnection();) {
      Statement stmt = conn.createStatement();
      ResultSet rstmt = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rstmt.getMetaData();
      s += "<th>" + rsmd.getColumnName(1) + "</th>";
      s += "<th>" + rsmd.getColumnName(2) + "</th>";

      s += "<th>D" + level + "</th>";

      output.add(s);
      while (rstmt.next()) {
        output.add("<td>" + rstmt.getString("county") + "</td><td>" + rstmt.getString("state") + "</td><td>"
            + rstmt.getDouble("avgD" + level) + "</td>");
      }
    }
    return output;
  }

  public static String getTableHeaders(ResultSet rstmt) throws SQLException {
    String s = "";
    ResultSetMetaData rsmd = rstmt.getMetaData();
    for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
      s += "<th>" + rsmd.getColumnName(i) + "</th>";
    }
    return s;
  }

  public static ArrayList<String> readSQLCountyData(String countyName) throws SQLException {
    ArrayList<String> output = new ArrayList<String>();
    String query = "select avgdrought.county, state, avgd0, avgd1, avgd2, avgd3, avgd4, watertolandratiocounty.avgwatertolandratio,percentagewatercounty. avgpercentagewatercounty from avgdrought inner join watertolandratiocounty on avgdrought.county=watertolandratiocounty.county inner join percentagewatercounty on avgdrought.county = percentagewatercounty.county where avgdrought.county="
        + "'" + countyName + "'";
    try (Connection conn = DatabaseConnector.getConnection();) {
      Statement stmt = conn.createStatement();
      ResultSet rstmt = stmt.executeQuery(query);
      output.add(getTableHeaders(rstmt));
      while (rstmt.next()) {
        output.add("<td>" + rstmt.getString("county") + "</td>" + "<td>" + rstmt.getString("state") + "</td>" + "<td>"
            + rstmt.getDouble("avgD0") + "</td>" + "<td>" + rstmt.getDouble("avgD1") + "</td>" + "<td>"
            + rstmt.getDouble("avgD2") + "</td>" + "<td>" + rstmt.getDouble("avgD3") + "</td>" + "<td>"
            + rstmt.getDouble("avgD4") + "</td>" + "<td>" + rstmt.getDouble("avgwatertolandratio") + "</td>" + "<td>"
            + rstmt.getString("avgpercentagewatercounty") + "</td>");
      }
    }
    return output;
  }
}