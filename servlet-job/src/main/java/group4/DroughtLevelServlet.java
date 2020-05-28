package sparkproj;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/DroughtLevelServlet")
public class DroughtLevelServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  // Set response content type



		resp.getWriter().println("Hello!");
		String var = req.getParameter("DLevel");
		String num = req.getParameter("ONum");
		
		int rnum = 100;
		if(!num.equals(""))
		{
			rnum = Integer.parseInt(num);
		}
		System.out.println("Get Request = " + var);
		resp.getWriter().println("Draught Level var: " + var);
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


