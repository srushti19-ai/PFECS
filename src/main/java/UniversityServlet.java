import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UniversityServlet")
public class UniversityServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String country = request.getParameter("country");
        String course = request.getParameter("course");

        out.println("<h3>Universities</h3>");
        out.println("<p><b>Country:</b> " + country + " | <b>Course:</b> " + course + "</p>");

        out.println("<table border='1' width='100%'>");
        out.println("""
            <tr>
                <th>ID</th>
                <th>Country</th>
                <th>Course</th>
                <th>University</th>
                <th>Type</th>
                <th>Application</th>
                <th>Degree</th>
                <th>Language</th>
                <th>Stream</th>
                <th>Semester</th>
                <th>Deadline</th>
                <th>Requirement</th>
                <th>Fee</th>
                <th>Link</th>
            </tr>
        """);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pfecsdata", "root", "root");

            PreparedStatement ps = con.prepareStatement(
                "SELECT id,country,course,university,type,application,degree,language,stream,semester,deadline,requirement,fee,link " +
                "FROM educationdetails WHERE country=? AND course=?");

            ps.setString(1, country);
            ps.setString(2, course);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("country") + "</td>");
                out.println("<td>" + rs.getString("course") + "</td>");
                out.println("<td>" + rs.getString("university") + "</td>");
                out.println("<td>" + rs.getString("type") + "</td>");
                out.println("<td>" + rs.getString("application") + "</td>");
                out.println("<td>" + rs.getString("degree") + "</td>");
                out.println("<td>" + rs.getString("language") + "</td>");
                out.println("<td>" + rs.getString("stream") + "</td>");
                out.println("<td>" + rs.getString("semester") + "</td>");
                out.println("<td>" + rs.getString("deadline") + "</td>");
                out.println("<td>" + rs.getString("requirement") + "</td>");
                out.println("<td>" + rs.getString("fee") + "</td>");
                out.println("<td><a href='" + rs.getString("link") + "' target='_blank'>Visit</a></td>");
                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch (Exception e) {
            out.println("Error loading universities");
        }
    }
}
