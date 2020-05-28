package org.group4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.group4.DatabaseConnector;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SparkSession;
import org.group4.struct.County;


/**
 * Hello world!
 *
 */
public class App 
{
     static Connection connection = null;
     static PreparedStatement stmt = null;

    public static void main(String[] args) throws IOException, SQLException {

    //     SparkSession session = SparkSession.builder().master("local[*]").appName("spark-job").getOrCreate();

    //   Dataset<County> countyData = session.read().option("header", "true").option("multiline", "true")
    //         .csv("us-droughts.csv").as(Encoders.bean(County.class)).persist();
    //   countyData.createOrReplaceTempView("droughts");      
    //   Dataset<Row> dataDisplay = 
    //         session.sql("Select county,state,NONE,D0,D1,D2,D3,D4 FROM droughts WHERE D3 > 50 AND state = 'CA'").limit(50);

    //   bucketOperations.datasetToBucket(dataDisplay, "testfile2.csv", "testfile2.csv");

    ArrayList<String> contents = bucketOperations.getContentsOfBucket();
    System.out.println(contents);

    for(String s: contents)
    {   System.out.println("Inside" + s);
        if(s.substring(s.length()-4,s.length()).equals(".csv") && !s.equals("us-droughts.csv") && !s.equals("county_info_2016.csv"))
        {
        System.out.println(!s.equals("us-droughts.csv"));
        System.out.println(!s.equals("county_info_2016.csv"));
        System.out.println("Inside " + s + " insertIntoDatabase");
        App.insertIntoDatabase(s,s.substring(0, s.length() - 4));
        System.out.println(App.printDatabase(s.substring(0, s.length() - 4)));
        }
    }


     //  session.close();

    }

    public static void insertIntoDatabase(String bucketFile, String tableName) throws SQLException, IOException {

        bucketOperations.getFromBucket(bucketFile, bucketFile);

        
        FileReader fr = new FileReader(new File(bucketFile));
        BufferedReader br = new BufferedReader(fr);
        
        connection = DatabaseConnector.getConnection();

        String [] columnList = br.readLine().split(",");
        stmt = connection.prepareStatement(createTableQuery(columnList, tableName));
        stmt.executeUpdate();

        String line;

        while((line = br.readLine()) != null) {
            
   
            String [] rowEntry = line.split(",");
            stmt = connection.prepareStatement(createInsertQuery(rowEntry,columnList, tableName));
            stmt.executeUpdate();
 
        }
        br.close();
           

        }

    

    public static String createTableQuery(String [] columnList,String tableName) {
       
        StringBuilder s = new StringBuilder();

        s.append("create table " + tableName + "(");
        for (int i = 0; i < columnList.length; i++)
        {
            if(i == columnList.length-1)
            {
                s.append(columnList[i] + " " + getColumnDataType(columnList[i]) + " );");
            }
            else
            {
                s.append(columnList[i] + " " + getColumnDataType(columnList[i]) + ",\n");
            }
        }
        System.out.println(s);
        return s.toString();
    }

    public static String createInsertQuery(String [] rowEntry, String [] columnList, String tableName)
    {
        StringBuilder s = new StringBuilder();

        s.append("INSERT INTO " + tableName + " VALUES (");

        for (int i = 0; i < rowEntry.length; i++)
        {
            if(i == rowEntry.length-1)
            {
                if(getColumnDataType(columnList[i]).equals("VARCHAR") || getColumnDataType(columnList[i]).equals("DATE"))
                {
                    s.append("'" +rowEntry[i] + "'" + ");");
                }
                else
                {
                    s.append(rowEntry[i] + ");");
                }
            }
            else
            {
                if(getColumnDataType(columnList[i]).equals("VARCHAR") || getColumnDataType(columnList[i]).equals("DATE"))
                {
                    s.append("'" +rowEntry[i] + "'" + ", ");
                }
                else
                {
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
        for(int i = 1; i <= rs.getMetaData().getColumnCount();i++)
        {
            if(i == rs.getMetaData().getColumnCount())
                string.append(rs.getMetaData().getColumnName(i) + "\n");
            else
                string.append(rs.getMetaData().getColumnName(i) + " ");
        }

        while (rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount();i++)
            if(i == rs.getMetaData().getColumnCount())
                string.append(rs.getArray(i) + "\n");
            else
                string.append(rs.getArray(i) + " ");
        }

        return string.toString();

    }
    
  
    public static String getColumnDataType(String columnName)
    {
               if(columnName.equals("NONE") || columnName.equals("D0")
               || columnName.equals("D1")   || columnName.equals("D2") 
               || columnName.equals("D3")   || columnName.equals("D4")) {
                   return "DOUBLE PRECISION";
               }
               else if(columnName.equals("releaseDate") || columnName.equals("validStart") 
                    || columnName.equals("validEnd")) { 
                        return "DATE";
                    }
                else {
                    return "VARCHAR";
                }
            }    
        }

