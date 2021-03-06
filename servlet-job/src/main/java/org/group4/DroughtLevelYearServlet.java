package org.group4;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.group4.io.SQLRepoServlet;

public class DroughtLevelYearServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Set response content type
		resp.setContentType("text/html");

		String desc = req.getParameter("DBool");
		// String num = req.getParameter("DNum");

		// int rnum = 100;
		// if (!num.equals("")) {
		// rnum = Integer.parseInt(num);
		// }
		System.out.println("Get Request DBool= " + desc);
		System.out.println("Get Request Cat= " + desc);

		resp.getWriter().println("Draught Level col: " + desc);

		// group wind from lowest to highest based on browser request
		// ************************************************ */
		// Next Steps
		/*
		 * Call bash script to connect to EMR/Bucket with argument to start this
		 * operation script Get results back from bucket return results to writer /*
		 * //-----------Database operations for Later----------// //Dataset<Row> vars =
		 * ds.filter(col("State").gt(var)); //String result = vars.showString(rnum, 100,
		 * false); /**************************************************
		 */
		String result = "Fill this with proper response later";
		resp.getWriter().println(result);
		try {
			String[] list = SQLRepoServlet.readSQLYear(desc).toString().split(",");
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
