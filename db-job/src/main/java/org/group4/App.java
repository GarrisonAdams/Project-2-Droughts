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
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
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

    //   Dataset<County> countyData = session.read().option("header", "true").option("multiline", "true").csv("us-droughts.csv").as(Encoders.bean(County.class)).persist();
    //   countyData.createOrReplaceTempView("droughts");      
    //   Dataset<Row> dataDisplay = session.sql("Select county,state,NONE,D0,D1,D2,D3,D4 FROM droughts WHERE D4 > 50 AND state = 'CA'").limit(50);

    //   bucketOperations.datasetToBucket(dataDisplay, "testfile.csv", "testfile.csv");

        //App.insertIntoDatabase("testfile.csv");
        System.out.println(App.printDatabase("tabs"));

      //  session.close();

    }

    public static void insertIntoDatabase(String bucketFile) throws SQLException, IOException {

        bucketOperations.getFromBucket(bucketFile, bucketFile);

        connection = DatabaseConnector.getConnection();

        FileReader fr = new FileReader(new File(bucketFile));
        BufferedReader br = new BufferedReader(fr);
        
        String [] columnList = br.readLine().split(",");
        stmt = connection.prepareStatement(createTableQuery(columnList, "tabs"));
        stmt.executeUpdate();

        String line;
        int i = 0;
        while((line = br.readLine()) != null && i < 50) {
            String [] rowEntry = line.split(",");
            stmt = connection.prepareStatement(createInsertQuery(rowEntry,columnList, "tabs"));
            stmt.executeUpdate();
            if(line != null)
                line = br.readLine();
            i++;
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

