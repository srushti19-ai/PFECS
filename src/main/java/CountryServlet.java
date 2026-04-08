//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@WebServlet("/CountryServlet")
//public class CountryServlet extends HttpServlet {
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws IOException {
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//        out.println("<h3>Select Country</h3>");
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/pfecsdata", "root", "root");
//
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(
//                    "SELECT DISTINCT country FROM educationdetails");
//
//            while (rs.next()) {
//                String country = rs.getString("country");
//
//                out.println(
//                    "<div class='list-item' onclick=\"selectCountry('" 
//                    + country + "')\">" 
//                    + country + 
//                    "</div>"
//                );
//            }
//
//            con.close();
//
//        } catch (Exception e) {
//            out.println("Error loading countries");
//        }
//    }
//}

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CountryServlet")
public class CountryServlet extends HttpServlet {

 protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException {

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/pfecsdata","root","root");

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT DISTINCT country FROM educationdetails");

        out.println("<h6>Select Country</h6>");

        while(rs.next()){
            String c = rs.getString(1);
            out.println(
              "<label>" +
              "<input type='radio' name='country' " +
              "onclick=\"selectCountry('"+c+"')\"> " +
              c +
              "</label><br>"
            );
        }

        con.close();

    } catch(Exception e){
        out.println("Error loading countries");
    }
 }
}

