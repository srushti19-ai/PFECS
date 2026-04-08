
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteRecordServlet
 */
@WebServlet("/DeleteRecordServlet")
public class DeleteRecordServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pfecsdata","root","root");
			String Id=request.getParameter("id");
			PreparedStatement pst=con.prepareStatement("delete from educationdetails where id=?");
			pst.setString(1,Id);
			int r=pst.executeUpdate();
			if(r>0) {
				//out.println("Record Deleted");
				response.sendRedirect("ShowRecordServlet");
			}else {
				out.println("Error Deleting Records");
			}
			pst.close();
			con.close();
		}
		catch(ClassNotFoundException ce) {
			out.println("Class not found exception");
		}
		catch(SQLException se) {
			out.println("SQL Exception");
		}
	}

}
