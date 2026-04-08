import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/education")
public class EducationApiServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	  response.setHeader("Access-Control-Allow-Origin", "*");
    	  response.setContentType("application/json;charset=UTF-8");
      
        response.setCharacterEncoding("UTF-8");
        
       

        PrintWriter out = response.getWriter();

        StringBuilder json = new StringBuilder();
        json.append("[");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pfecsdata", "root", "root");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM educationdetails");

            boolean first = true;

            while (rs.next()) {
                if (!first) {
                    json.append(",");
                }
                first = false;

                json.append("{");
                json.append("\"id\":\"").append(rs.getString(1)).append("\",");
                json.append("\"country\":\"").append(rs.getString(2)).append("\",");
                json.append("\"course\":\"").append(rs.getString(3)).append("\",");
                json.append("\"university\":\"").append(rs.getString(4)).append("\",");
                json.append("\"type\":\"").append(rs.getString(5)).append("\",");
                json.append("\"application\":\"").append(rs.getString(6)).append("\",");
                json.append("\"degree\":\"").append(rs.getString(7)).append("\",");
                json.append("\"language\":\"").append(rs.getString(8)).append("\",");
                json.append("\"stream\":\"").append(rs.getString(9)).append("\",");
                json.append("\"semester\":\"").append(rs.getString(10)).append("\",");
                json.append("\"deadline\":\"").append(rs.getString(11)).append("\",");
                json.append("\"requirement\":\"").append(rs.getString(12)).append("\",");
                json.append("\"fee\":\"").append(rs.getString(13)).append("\",");
                json.append("\"link\":\"").append(rs.getString(14)).append("\"");
                json.append("}");
            }
            json.append("]");

            out.print(json.toString());            
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.print("[]"); // return empty array on error
        }
    }
}
