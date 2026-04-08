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

@WebServlet("/EducationFormServlet")
public class EducationFormServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>PFECS</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("<script>");
        out.println("function showOther(selectId, inputId) {");
        out.println("  var s = document.getElementById(selectId);");
        out.println("  var t = document.getElementById(inputId);");
        out.println("  if (s.value === 'other') {");
        out.println("    t.style.display = 'block';");
        out.println("  } else {");
        out.println("    t.style.display = 'none';");
        out.println("    t.value = '';");
        out.println("  }");
        out.println("}");
        out.println("</script>");

        out.println("</head>");

        out.println("<body class='bg-light'>");
        
       
//        out.println("<div class='navbar'>");
//        out.println("<a href='index.html'>Home</a>");
//        out.println("<a href='EducationFormServlet'>Add Record</a>");
//        out.println("<a href='deleteRecord.html'>Delete Record</a>");
//        out.println("<a href='showRecord.html'>Show Records</a>");
//        out.println("<a href='about.html'>About</a>");
//        out.println("<a href='contact.html'>Contact</a>");
//        out.println("</div>");
        out.println("<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\r\n"
        		+ "  <div class=\"container\">\r\n"
        		+ "    <a class=\"navbar-brand fw-bold\" href=\"#\">PFECS</a>\r\n"
        		+ "    <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNav\">\r\n"
        		+ "      <span class=\"navbar-toggler-icon\"></span>\r\n"
        		+ "    </button>\r\n"
        		+ "    <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\r\n"
        		+ "      <ul class=\"navbar-nav ms-auto\">\r\n"
        		+ "        <li class=\"nav-item\"><a class=\"nav-link\" href=\"index.html\">Home</a></li>\r\n"
        		+ "        <li class=\"nav-item\"><a class=\"nav-link\" href=\"EducationFormServlet\">Add Record</a></li>\r\n"
        		+ "        <li class=\"nav-item\"><a class=\"nav-link\" href=\"deleteRecord.html\">Delete Record</a></li>\r\n"
        		+ "        <li class=\"nav-item\"><a class=\"nav-link\" href=\"ShowRecordServlet\">Show Records</a></li>\r\n"
        		+ "        <li class=\"nav-item\"><a class=\"nav-link\" href=\"about.html\">About</a></li>\r\n"
        		+ "        <li class=\"nav-item\"><a class=\"nav-link\" href=\"contact.html\">Contact</a></li>\r\n"
        		+ "      </ul>\r\n"
        		+ "    </div>\r\n"
        		+ "  </div>\r\n"
        		+ "</nav>");
//        out.println("<nav class=\"navbar\">\r\n"
//        		+ "    <div class=\"nav-left\">PFECS</div>\r\n"
//        		+ "    <div class=\"nav-right\">\r\n"
//        		+ "         <a href=\"index.html\">Home</a></h3>\r\n"
//        		+ "         <a href=\"EducationFormServlet\">Add Record</a>\r\n"
//        		+ "         <a href=\"deleteRecord.html\">Delete Record</a>\r\n"
//        		+ "         <a href=\"showRecord.html\">Show Records</a>\r\n"
//        		+ "         <a href=\"about.html\">About</a>\r\n"
//        		+ "         <a href=\"contact.html\">Contact</a>\r\n"
//        		+ "    </div>\r\n"
//        		+ "</nav>  ");

        out.println("<div class='container mt-4'>");
        out.println("<div class='row justify-content-center'>");
        out.println("<div class='col-md-8'>");

        out.println("<div class='card shadow'>");
        out.println("<div class='card-body'>");
        out.println("<h2>Registration Page</h2>");
        out.println("<form method='post' action='AddRecordServlet'>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pfecsdata", "root", "root");

            
            out.println("<label>Choose Country:</label>");
           // out.println("<select name='Country' id='Country' required >"); 
            out.println("<select class='form-select mb-3' name='Country' id='Country' required "
                    + "onchange=\"showOther('Country','otherCountry')\">");

            out.println("<option value=''>--Select--</option>");

            PreparedStatement psCountry =
                    con.prepareStatement("SELECT DISTINCT Country FROM educationdetails");
            ResultSet rsCountry = psCountry.executeQuery();

            while (rsCountry.next()) {
                out.println("<option value='" + rsCountry.getString("Country") + "'>"
                        + rsCountry.getString("Country") + "</option>");
            }
            out.println("<option value='other'>--Other--</option>");
            out.println("</select>");
            out.println("<div align=center><input class='form-control mb-3' type='text' name='OtherCountry' id='otherCountry' "
                      + "placeholder='Enter Country' style='display:none'></div>");

            
            out.println("<label style: width:50%>Choose Course:</label>");
//            out.println("<select name='Course' id='Course' required"
//                    + "onchange=\"showOther('Course','otherCourse')\">");
            out.println("<select class='form-select mb-3' name='Course' id='Course' required "
                    + "onchange=\"showOther('Course','otherCourse')\">");
            out.println("<option value=''>--Select--</option>");

            PreparedStatement psCourse =
                    con.prepareStatement("SELECT DISTINCT Course FROM educationdetails");
            ResultSet rsCourse = psCourse.executeQuery();

            while (rsCourse.next()) {
                out.println("<option value='" + rsCourse.getString("Course") + "'>"
                        + rsCourse.getString("Course") + "</option>");
            }
            out.println("<option value='other'>--Other--</option>");
            out.println("</select>");
            out.println("<div align=center><input class='form-control mb-3' type='text' name='OtherCourse' id='otherCourse' "
                    + "placeholder='Enter Course' style='display:none'></div>");

            
            out.println("<label>Choose University:</label>");
            out.println("<select class='form-select mb-3' name='University' id='University' required "
                   + "onchange=\"showOther('University','otherUniversity')\">");
            out.println("<option value=''>--Select--</option>");

            PreparedStatement psUni =
                    con.prepareStatement("SELECT DISTINCT University FROM educationdetails");
            ResultSet rsUni = psUni.executeQuery();

            while (rsUni.next()) {
                out.println("<option value='" + rsUni.getString("University") + "'>"
                        + rsUni.getString("University") + "</option>");
            }
            out.println("<option value='other'>--Other--</option>");
            out.println("</select>");
            out.println("<div align=center><input class='form-control mb-3' type='text' name='OtherUniversity' id='otherUniversity' "
                    + "placeholder='Enter University' style='display:none'></div>");
            
        out.println("</select>\r\n"
        		 +"<div>"
        		+ "  Choose Type:<input type=\"radio\" id=\"private\" name=\"Type\" value=\"private\">"
        		+ "  <label for=\"private\">Private</label>"
        		+ "  <input type=\"radio\" id=\"public\" name=\"Type\" value=\"public\">"
        		+ "  <label for=\"public\">Public</label>"
        		+ "</div>"
        		+"<div>"
        		+ "  Choose Application:<input type=\"radio\" id=\"direct\" name=\"Application\" value=\"direct\">"
        		+ "  <label for=\"direct\">Direct</label>"
        		+ "  <input type=\"radio\" id=\"uniassist\" name=\"Application\" value=\"uniassist\">"
        		+ "  <label for=\"uniassist\">Uniassist</label>"
        		+ "</div>"
        		+ "<label for=\"Degree\">Choose Degree:</label>\r\n"
        		//+ "<select name=\"Degree\" id=\"Degree\" required>\r\n"
        		+"<select class='form-select mb-3' name='Degree' id='Degree' required "
              + "onchange=\"showOther('Degree','otherDegree')\">"
        		+ "  <option value=\"\">--Select an Option--</option>\r\n"
        		+ "  <option value=\"Bachelor\">Bachelor</option>\r\n"
        		+ "  <option value=\"MA\">MA</option>\r\n"
        		+ "  <option value=\"MBA\">MBA</option>\r\n"
        		+ "  <option value=\"MBA&MEng\">MBA & MEng</option>\r\n"
        		+ "  <option value=\"MEng\">MEng</option>\r\n"
        		+ "  <option value=\"MSc\">MSc</option>\r\n" 
        		+ "  <option value='other'>--Other--</option>"
        		+ "</select>"
        		+ "<div align=center><input class='form-control mb-3' type='text' name='OtherDegree' id='otherDegree' "
              + "placeholder='Enter Degree' style='display:none'></div>"
        		+ "<label for=\"Language\">Choose Language:</label>\r\n"
        		//+ "<select name=\"Language\" id=\"Language\" required>\r\n"
        		+"<select class='form-select mb-3' name='Language' id='Language' required "
                + "onchange=\"showOther('Language','otherLanguage')\">"
        		+ "  <option value=\"\">--Select an Option--</option>\r\n"
        		+ "  <option value=\"English\">English</option>\r\n" 
        		+ "  <option value='other'>--Other--</option>"
        		+ "</select>\r\n"
        		+ "<div align=center><input class='form-control mb-3' type='text' name='OtherLanguage' id='otherLanguage' "
                + "placeholder='Enter Language' style='display:none'></div>");
        
        out.println("<label >Choose Stream:</label>");
        //out.println("<select name='Stream' id='Stream'' required>");
        out.println("<select class='form-select mb-3' name='Stream' id='Stream' required "
                + "onchange=\"showOther('Stream','otherStream')\">");
        out.println("<option value=''>--Select an Option--</option>");

        PreparedStatement psStream =
                con.prepareStatement("SELECT DISTINCT Stream FROM educationdetails");
        ResultSet rsStream = psStream.executeQuery();

        while (rsStream.next()) {
            out.println("<option value='" + rsStream.getString("Stream") + "'>"
                    + rsStream.getString("Stream") + "</option>");
        }
        out.println("<option value='other'>--Other--</option>");
        out.println("</select>");
        out.println("<div align=center><input class='form-control mb-3' type='text' name='OtherStream' id='otherStream' "
                + "placeholder='Enter Stream' style='display:none'></div>");
        
        	out.println("<label for=\"Semester\">Choose Semester:</label>\r\n"
        		//+ "<select name=\"Semester\" id=\"Semester\" required>\r\n"
        		+"<select class='form-select mb-3' name='Semester' id='Semester' required "
                + "onchange=\"showOther('Semester','otherSemester')\">"
        		+ "  <option value=\"\">--Select an Option--</option>\r\n"
        		+ "  <option value=\"MainlyWinter&Summer\">Mainly Winter & Summer</option>\r\n"
        		+ "  <option value=\"Summer\">Summer</option>\r\n"
        		+ "  <option value=\"Summer&Winter\">Summer & Winter</option>\r\n"
        		+ "  <option value=\"SummerandWinter\">Summer and Winter</option>\r\n"
        		+ "  <option value=\"Winter\">Winter</option>\r\n"
        		+ "  <option value=\"Winter&Summer\">Winter & Summer</option>\r\n"
        		+"   <option value='other'>--Other--</option>"
        		+ "</select>"
        		+"<div align=center><input class='form-control mb-3' type='text' name='OtherSemester' id='otherSemester' "
                + "placeholder='Enter Semester' style='display:none'></div>");
        out.println("Deadline: <input class='form-control mb-3'  type='date' name='Deadline' placeholder='Deadline'>");
        out.println("Requirement:");
        out.println("<div class='form-floating'>");
        out.println("<textarea class=\"form-control\" name='Requirement' rows='3' cols='25' placeholder='type here'></textarea><br>");
        out.println("</div>");
        out.println("Fee: <input class='form-control mb-3' type='text' name='Fee' id='Fee' placeholder='Fee'>"
        		//+"<select name=currency>"
        		+"<select class='form-select mb-3' name='currency' id='currency' required "
                + "onchange=\"showOther('currency','otherCurrency')\">"
        		+"<option value=' '>Select Currency</option>"
        		+"<option value='inr '>INR</option>"
        		+"<option value='eur'>EUR</option>"
        		+"<option value='usd'>USD</option>"
        		+"<option value='other'>--Other--</option>"
        		+"</select>"
        		+"<div align=center><input class='form-control mb-3' type='text' name='OtherCurrency' id='otherCurrency' "
                + "placeholder='Enter Currency' style='display:none'></div>"
        		+ "");
        out.println("Link: <input class='form-control mb-3' type='text' name='Link' id='https://' placeholder='https://'>");

        out.println("<input class=\"btn btn-outline-primary\" type='submit' style=' border-radius:10px; ' value='Submit'>");
        out.println("</form>");
        

        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
        out.println("</body>");
        out.println("</div></div></div></div></div>");
        out.println("<footer>\r\n"
        		+ "<div class=\"data\">\r\n"
        		+ "2025 PFECS Dashboard</div>\r\n"
        		+ "<div class=\"data-contact\">\r\n"
        		+ "Contact : +$9 157 3010 1262 .<br> Mail : \r\n"
        		+ "<a href=\"showRecord.html\">harald.pecher@hpconsulting.de</a></div>\r\n"
        		+ "</footer>");
        out.println("</html>");
        con.close();

        } catch (Exception e) {
            out.println("<p style='color:red'>Error loading dropdowns</p>");
        }
    }
}
