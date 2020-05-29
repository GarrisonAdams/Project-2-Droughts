package org.group4;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.group4.io.SQLRepoServlet;

import static org.apache.spark.sql.functions.*;

public class DroughtPRServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Set response content type
		resp.setContentType("text/html");

		resp.getWriter().println("Hello!");
		String table = req.getParameter("DPR");
		String dir = req.getParameter("DBoolPR");
		String num = req.getParameter("DNum");

		int rnum = 1000;
		if (!num.equals("")) {
			rnum = Integer.parseInt(num);
		}
		System.out.println("Get Request = " + table);
		System.out.println("Get Request = " + dir);
		System.out.println("Get Request = " + num);
		resp.getWriter().println("Drought var: " + table);

		// group wind from lowest to highest based on browser request
		// ************************************************ */
		// Next Steps
		/*
		 * Call bash script to connect to EMR/Bucket with argument to start this
		 * operation script. Also Pass in the arguments rnum and col ie. the number of
		 * results and the column you wish to count Get results back from bucket return
		 * results to writer /* //-----------Database operations for Later----------//
		 * Dataset<Row> vars = ds.groupBy(col).count().orderBy(col("count")); String
		 * result = vars.showString(rnum, 10, false);
		 * /**************************************************
		 */

		String result = "No Database Found";
		resp.getWriter().println(result);
		try {
			String[] list = SQLRepoServlet.readSQLPR(table, dir, num).toString().split(",");
			resp.getWriter().println("<table>");
			resp.getWriter().println("<tr>");
			resp.getWriter().println(list[0]);
			resp.getWriter().println("</tr>");
			for (int i = 1; i < list.length; i++) {
				resp.getWriter().println("<tr>" + list[i] + "</tr>");
				// resp.getWriter().println("<br>");
			}
			resp.getWriter().println("</table>");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
