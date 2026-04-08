
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

@WebServlet("/AddRecordServlet")
public class AddRecordServlet extends HttpServlet 
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException
	{
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pfecsdata","root","root");
			//String idname=request.getParameter("id");
			String country=request.getParameter("Country");
			String otherCountry = request.getParameter("OtherCountry");
			if("other".equals(country)) {
				country=otherCountry;
			}
			String course=request.getParameter("Course");
			String otherCourse = request.getParameter("OtherCourse");
			if("other".equals(course)) {
				course=otherCourse;
			}
			String university=request.getParameter("University");
			String otherUniversity = request.getParameter("OtherUniversity");
			if("other".equals(university)) {
				university=otherUniversity;
			}
			String type=request.getParameter("Type");
			String application=request.getParameter("Application");
			String degree=request.getParameter("Degree");
			String otherDegree = request.getParameter("OtherDegree");
			if("other".equals(degree)) {
				degree=otherDegree;
			}
			String language=request.getParameter("Language");
			String otherLanguage = request.getParameter("OtherLanguage");
			if("other".equals(language)) {
				language=otherLanguage;
			}
			String stream=request.getParameter("Stream");
			String otherStream = request.getParameter("OtherStream");
			if("other".equals(stream)) {
				stream=otherStream;
			}
			String semester=request.getParameter("Semester");
			String otherSemester = request.getParameter("OtherSemester");
			if("other".equals(semester)) {
				semester=otherSemester;
			}
			String deadline=request.getParameter("Deadline");
			String requirement=request.getParameter("Requirement");
			String fee=request.getParameter("Fee");
			String currency=request.getParameter("currency");
			//String feeWithCurrency = fee + " " + currency.toUpperCase();
			String otherCurrency = request.getParameter("OtherCurrency");
			if("other".equals(currency)) {
				currency=otherCurrency;
			}
			String feeWithCurrency = fee + " " + currency.toUpperCase();
			String link=request.getParameter("Link");
			
			PreparedStatement pstmt=con.prepareStatement("select * from educationdetails where Link=?");
			pstmt.setString(1,link);
			ResultSet re=pstmt.executeQuery();
			if (re.next()) {
            	out.println("Already Exist,try again");
			}else {
        	    PreparedStatement pst=con.prepareStatement("insert into educationdetails(country, course, university, type, application, degree, language, stream, semester, deadline, requirement, fee, link) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	    pst.setString(1, country);
        	    pst.setString(2, course);
        	    pst.setString(3, university);
        	    pst.setString(4, type);
        	    pst.setString(5, application);
        	    pst.setString(6, degree);
        	    pst.setString(7, language);
        	    pst.setString(8, stream);
        	    pst.setString(9, semester);
        	    pst.setString(10, deadline);
        	    pst.setString(11, requirement);
        	    pst.setString(12, feeWithCurrency);
        	    pst.setString(13, link);
        	    int r=pst.executeUpdate();
        	    if(r>0) {
        	    	    //out.println("Record Inserted Successfully");
        	    	    response.sendRedirect("index.html");
        	    }else {
        	    	    out.println("Error occured during execution");
        	     }
		    }
		}
		catch(ClassNotFoundException ce) 
		{
			out.println("Class Not Found Exception");	
		}
		catch(SQLException se)
		{
			out.println("SQL Exception "+se);
		}
		
	}
}
