package org.group4;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.group4.io.SQLRepoServlet;

@WebServlet("/DroughtLevelServlet")
public class DroughtLevelServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Set response content type
		resp.setContentType("text/html");
		resp.getWriter().println("Hello! <br>");
		String var = req.getParameter("DLevel");
		String num = req.getParameter("ONum");

		int rnum = 100;
		if (!num.equals("")) {
			rnum = Integer.parseInt(num);
		}
		// number of requests
		System.out.println("Get Request = " + var + "<br>");
		// drought level
		resp.getWriter().println("Draught Level var: " + var + "<br>");
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
		String result = "Fill this with proper response later <br>";
		resp.getWriter().println(result);
		try {
			String[] list = SQLRepoServlet.readSQL("avgdrought", var.toUpperCase(), num).toString().split(",");
			resp.getWriter().println("<table>");
			resp.getWriter().println("<tr>");
			resp.getWriter().println(list[0]);
			resp.getWriter().println("</tr>");
			for (int i = 0; i < list.length; i++) {
				resp.getWriter().println("<tr>" + list[i + 1] + "</tr>");
				// resp.getWriter().println("<br>");
			}
			resp.getWriter().println("</table>");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
