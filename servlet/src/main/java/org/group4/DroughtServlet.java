package org.group4;

import java.io.IOException;
<<<<<<< HEAD
=======
import java.io.PrintWriter;
>>>>>>> GA_Branch

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DroughtServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         
<<<<<<< HEAD
        resp.getWriter().println("Servlet Activated");
        resp.getWriter().println("Entered " + req.getParameter("token"));

=======
		PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        

        out.print("<html><body>");
        out.print("<h2> Droughts by U.S. County</h2>");
        out.print("<h3> Project 2 Group 4, by Mason, Jacob, Garrison, and Anthony</h3>");
        
        
        out.println("doGET method");
        out.println("Entered " + req.getParameter("token"));
        out.println("<hr></hr>");
        out.print("<button type=\"button\" name=\"back\" onclick=\"history.back()\">back</button>");
        out.print("</body></html>");
>>>>>>> GA_Branch
	}
}