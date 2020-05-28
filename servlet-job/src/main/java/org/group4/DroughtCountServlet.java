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
import static org.apache.spark.sql.functions.*;



@WebServlet("/DroughtCountServlet")
public class DroughtCountServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  // Set response content type



		resp.getWriter().println("Hello!");
		String col = req.getParameter("DCol");
		String num = req.getParameter("DNum");
		
		int rnum = 1000;
		if(!num.equals(""))
		{
			rnum = Integer.parseInt(num);
		}
		System.out.println("Get Request = " + col);
		resp.getWriter().println("Drought var: " + col);

				//group wind from lowest to highest based on browser request
		//************************************************ */
		//Next Steps
		/*
			Call bash script to connect to EMR/Bucket with argument 
				to start this operation script. Also Pass in the arguments rnum
				and col ie. the number of results and the column you wish to count
			Get results back from bucket
			return results to writer
		/*
		//-----------Database operations for Later----------//
		Dataset<Row> vars = ds.groupBy(col).count().orderBy(col("count"));
		String result = vars.showString(rnum, 10, false);
		/************************************************** */

		String result = "No Database Found";
		resp.getWriter().println(result);


	}



}
