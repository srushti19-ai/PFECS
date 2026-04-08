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

@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String country = request.getParameter("country");

        out.println("<h3>Courses in " + country + "</h3>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pfecsdata", "root", "root");

            PreparedStatement ps = con.prepareStatement(
                "SELECT DISTINCT course FROM educationdetails WHERE country=?");
            ps.setString(1, country);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String course = rs.getString("course");
                out.println(
                    "<div class='list-item' onclick=\"selectCourse('" + course + "')\">" 
                    + course + 
                    "</div>"
                );
            }

            con.close();

        } catch (Exception e) {
            out.println("Error loading courses");
        }
    }
}
