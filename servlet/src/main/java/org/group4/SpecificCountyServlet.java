package org.group4;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.group4.io.SQLRepoServlet;

public class SpecificCountyServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		String col = req.getParameter("DCounty");

		System.out.println("Get Request = " + col);
		resp.getWriter().println("Drought var: " + col);

		// group wind from lowest to highest based on browser request
		// ************************************************ */
		// Next Steps
		/*
		 * Call bash script to connect to EMR/Bucket with argument to start this
		 * operation script. Also Pass in the arguments col ie. the county Get results
		 * back from bucket return results to writer /* //-----------Database operations
		 * for Later----------// Dataset<Row> vars =
		 * ds.groupBy(col).count().orderBy(col("count")); String result =
		 * vars.showString(rnum, 10, false);
		 * /**************************************************
		 */

		String result = "No Database Found";
		resp.getWriter().println(result);
		try {
			String[] list = SQLRepoServlet.readSQLCountyData(col).toString().split(",");
			// System.out.println(SQLRepoServlet.readSQLCountyData(col));
			System.out.println(list[0]);
			// System.out.println(list[1]);

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
