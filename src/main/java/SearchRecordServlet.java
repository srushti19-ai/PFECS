
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/SearchRecordServlet")
public class SearchRecordServlet extends HttpServlet {
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException
		{
			PrintWriter out=response.getWriter();
			response.setContentType("text/html");
			String keyword=request.getParameter("keyword");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pfecsdata","root","root");
				out.println("<link rel='stylesheet' href='show.css'>");
				PreparedStatement pst=con.prepareStatement("select * from educationdetails where Country Like ? OR Course Like ?");
				pst.setString(1,"%"+keyword+"%");
				pst.setString(2,"%"+keyword+"%");
				ResultSet r=pst.executeQuery();
				out.println("<div class='newcls'>");
	            out.println("<table border='1'>");
	            out.println("<tr>"
	                    + "<th>ID</th><th>Country</th><th>Course</th>"
	                    + "<th>University</th><th>Type</th><th>Application</th><th>Degree</th>"
	                    + "<th>Language</th><th>Stream</th><th>Semester</th><th>Deadline</th>"
	                    + "<th>Requirement</th><th>Fee</th><th>Link</th>"
	                    + "</tr>");
	            boolean found = false;
				while (r.next()) {
					found=true;
					out.println("<tr>");
	                out.println("<td>" + r.getString(1) + "</td>");
	                out.println("<td>" + r.getString(2) + "</td>");
	                out.println("<td>" + r.getString(3) + "</td>");
	                out.println("<td>" + r.getString(4) + "</td>");
	                out.println("<td>" + r.getString(5) + "</td>");
	                out.println("<td>" + r.getString(6) + "</td>");
	                out.println("<td>" + r.getString(7) + "</td>");
	                out.println("<td>" + r.getString(8) + "</td>");
	                out.println("<td>" + r.getString(9) + "</td>");
	                out.println("<td>" + r.getString(10) + "</td>");
	                out.println("<td>" + r.getString(11) + "</td>");
	                out.println("<td>" + r.getString(12) + "</td>");
	                out.println("<td>" + r.getString(13) + "</td>");
	                out.println("<td>" + r.getString(14) + "</td>");
	                out.println("</tr>");
				}
				if(!found) {
					out.println("Error Displaying Records");
				}
				out.println("</table>");
	            out.println("</div>");
				pst.close();
				con.close();
			}catch(ClassNotFoundException ce)
			{
				out.println("Class Not Found Exception");
			}
			catch(SQLException se)
			{
				out.println("SQL Exception "+se);
			}
		}
	}