package org.group4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.IntegerType;

import static org.apache.spark.sql.functions.*;



@WebServlet("/DroughtAvgCatServlet")
public class DroughtAvgCatServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  // Set response content type



		resp.getWriter().println("Hello!");
		String cat = req.getParameter("DCat");
		String num = req.getParameter("DNum");
		
		int rnum = 100;
		if(!num.equals(""))
		{
			rnum = Integer.parseInt(num);
		}
		System.out.println("Get Request Cat= " + cat);

		resp.getWriter().println("Draught Level cat: " + cat);

		//group wind from lowest to highest based on browser request
		//************************************************ */
		//Next Steps
		/*
			Call bash script to connect to EMR/Bucket with argument to start this operation script
			Get results back from bucket
			return results to writer
		/*
		//-----------Database operations for Later----------//
		//Dataset<Row> vars = ds.filter(col("State").gt(var));
		//String result = vars.showString(rnum, 100, false);
		/************************************************** */
		String result = "Fill this with proper response later";
		resp.getWriter().println(result);


	}

}
