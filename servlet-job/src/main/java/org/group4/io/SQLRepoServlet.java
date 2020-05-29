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
    for (int i = 1; i < rsmd.getColumnCount(); i++) {
      s += "<th>" + rsmd.getColumnName(i) + "</th>";
    }
    return s;
  }

  public static ArrayList<String> readSQLCountyData(String table, String countyName) throws SQLException {
    ArrayList<String> output = new ArrayList<String>();
    String query = "select avgdrought.county,state,avgd0,avgd1,avgd2,avgd3,avgd4,percentagewatercounty.avgpercentagewatercounty,watertolandratiocounty.avgwatertolandratio from avgdrought inner join percentagewatercounty on avgdrought.county=percentagewatercounty.county inner join watertolandratiocounty on avgdrought.county=watertolandratiocounty.county";
    ;
    try (Connection conn = DatabaseConnector.getConnection();) {
      Statement stmt = conn.createStatement();
      ResultSet rstmt = stmt.executeQuery(query);
      output.add(getTableHeaders(rstmt));
      while (rstmt.next()) {
        output.add("<td>" + rstmt.getString("county") + "</td>" + "<td>" + rstmt.getString("state") + "</td>" + "<td>"
            + rstmt.getDouble("avgD0") + "</td>" + "<td>" + rstmt.getString("state") + "</td>" + "<td>"
            + rstmt.getString("state") + "</td>" + "<td>" + rstmt.getString("state") + "</td>" + "<td>"
            + rstmt.getString("state") + "</td>" + "<td>" + rstmt.getString("state") + "</td>" + "<td>"
            + rstmt.getString("state") + "</td>" + "<td>" + rstmt.getString("state") + "</td>" + "<td>"
            + rstmt.getString("state") + "</td>" + "<td>" + rstmt.getString("state") + "</td>" +

            "");
      }
    }
    return output;
  }

SELECT avgdrought.state,avgdrought.d0,avgdought.d1,avgdrought.d2,avgdrought.d3,
  avgdrought.d4 as OwnerAddress,
  i.url FROM
  house AS
  h
INNER
  JOIN person
  AS p
  ON p.personID=
  h.personID INNER
  JOIN images
  AS i
  ON i.personID=
  p.personID GROUP
  BY h.houseID
}