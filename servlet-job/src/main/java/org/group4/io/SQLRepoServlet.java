package org.group4.io;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
      // s += "<th>" + rsmd.getColumnName(2) + "</th>";
      s += "<th>D" + level + "</th>";

      output.add(s);
      while (rstmt.next()) {
        output.add("<td>" + rstmt.getString(1) + "</td><td>" + rstmt.getDouble("avgD" + level) + "</td>");
      }
    }
    return output;
  }

  public static ArrayList<String> readSQLPR(String table, String dir, String limit) throws SQLException {
    ArrayList<String> output = new ArrayList<String>();
    String s = "";
    LinkedHashMap<String, String> tableStuff = new LinkedHashMap<>();
    switch (table) {
      case "percentageWaterCounty":
        tableStuff.put(table, "avgpercentageWaterCounty");
        break;
      case "percentageWaterState":
        tableStuff.put(table, "avgpercentageWaterCounty");
        break;
      case "waterToLandRatioCounty":
        tableStuff.put(table, "avgwaterToLandRatio");
        break;
      case "waterToLandRatioState":
        tableStuff.put(table, "avgwaterToLandRatio");
        break;
      default:
        break;
    }
    String query = "select * from " + table + " order by cast(" + tableStuff.get(table) + " as decimal(18,3)) " + dir
        + " limit " + limit;
    try (Connection conn = DatabaseConnector.getConnection();) {
      Statement stmt = conn.createStatement();
      ResultSet rstmt = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rstmt.getMetaData();
      s += "<th>" + rsmd.getColumnName(1) + "</th>";
      s += "<th>" + tableStuff.get(table) + "</th>";

      output.add(s);
      while (rstmt.next()) {
        output.add("<td>" + rstmt.getString(1) + "</td><td>" + rstmt.getDouble(tableStuff.get(table)) + "</td>");
      }
    }
    return output;
  }

  public static ArrayList<String> readSQLAll(String countyOrState, String limit) throws SQLException {
    ArrayList<String> output = new ArrayList<String>();
    String query = "select * from mostdroughts" + countyOrState + " limit " + limit;
    try (Connection conn = DatabaseConnector.getConnection();) {
      Statement stmt = conn.createStatement();
      ResultSet rstmt = stmt.executeQuery(query);
      output.add(getTableHeaders(rstmt));
      while (rstmt.next()) {
        output.add("<td>" + rstmt.getString(1) + "</td>" + "<td>" + rstmt.getDouble("avgD0") + "</td>" + "<td>"
            + rstmt.getDouble("avgD1") + "</td>" + "<td>" + rstmt.getDouble("avgD2") + "</td>" + "<td>"
            + rstmt.getDouble("avgD3") + "</td>" + "<td>" + rstmt.getDouble("avgD4") + "</td>");
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

  public static ArrayList<String> readSQLStateData(String stateName) throws SQLException {
    ArrayList<String> output = new ArrayList<String>();
    String query = "select mostdroughtsstates.state, avgd0, avgd1, avgd2, avgd3, avgd4, watertolandratiostate.avgwatertolandratio,percentagewaterstate. avgpercentagewatercounty from mostdroughtsstates inner join watertolandratiostate on mostdroughtsstates.state=watertolandratiostate.state inner join percentagewaterstate on mostdroughtsstates.state = percentagewaterstate.state where mostdroughtsstates.state="
        + "'" + stateName + "'";
    String s = "";
    try (Connection conn = DatabaseConnector.getConnection();) {
      Statement stmt = conn.createStatement();
      ResultSet rstmt = stmt.executeQuery(query);
      output.add(getTableHeaders(rstmt));
      while (rstmt.next()) {
        output.add("<td>" + rstmt.getString("state") + "</td>" + "<td>" + rstmt.getDouble("avgD0") + "</td>" + "<td>"
            + rstmt.getDouble("avgD1") + "</td>" + "<td>" + rstmt.getDouble("avgD2") + "</td>" + "<td>"
            + rstmt.getDouble("avgD3") + "</td>" + "<td>" + rstmt.getDouble("avgD4") + "</td>" + "<td>"
            + rstmt.getDouble("avgwatertolandratio") + "</td>" + "<td>" + rstmt.getDouble("avgpercentagewatercounty")
            + "</td>");
      }
    }
    return output;
  }

  public static ArrayList<String> readSQLYear(String order) throws SQLException {
    ArrayList<String> output = new ArrayList<String>();
    String s = "";
    if (order.equals("Chronological")) {
      s = "yearreleasedate asc";
    } else if (order.equals("Desc")) {
      s = "cast(avgD0 as decimal(18,3)) desc";
    }
    String query = "select * from outputyearwithmostdroughts order by " + s;
    try (Connection conn = DatabaseConnector.getConnection();) {
      Statement stmt = conn.createStatement();
      ResultSet rstmt = stmt.executeQuery(query);
      output.add(getTableHeaders(rstmt));
      while (rstmt.next()) {
        output.add("<td>" + rstmt.getString("yearreleasedate") + "</td>" + "<td>" + rstmt.getDouble("avgD0") + "</td>"
            + "<td>" + rstmt.getDouble("avgD1") + "</td>" + "<td>" + rstmt.getDouble("avgD2") + "</td>" + "<td>"
            + rstmt.getDouble("avgD3") + "</td>" + "<td>" + rstmt.getDouble("avgD4") + "</td>");
      }
    }
    return output;
  }
}