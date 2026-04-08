import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ShowRecordServlet")
public class ShowRecordServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel='stylesheet' href='style1.css'");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<title>PFECS</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");

        // CSS
        out.println("<style>");
        out.println("body{margin:0;padding:0;}");
        out.println(".navbar{background:#6f42c1;padding:10px;display:flex;gap:10px;flex-wrap:wrap;align-items:center;}");
        out.println(".navbar a,.navbar button{color:black;text-decoration:none;}");
        out.println("th{background:navy !important;color:white !important;}");
        //this--- out.println("#filtersDesktop{width:350px;padding:10px;background:#f5f5f5;height:100vh;overflow:auto;}");
        out.println("#filtersDesktop{\r\n"
        		+ "  max-height:calc(100vh - 70px);\r\n"
        		+ "  overflow:auto;\r\n"
        		+ "}");
        out.println("@media(max-width:768px){ #filtersDesktop{display:none;} }");
        out.println("</style>");

        // JavaScript
        out.println("<script>");
        out.println("let allData = [];");
        out.println("let currentPage = 1;");
        out.println("const pageSize = 10;");
        out.println("const filterColumns = ['country','course','university','type','application','degree','language','stream','semester'];");

        out.println("window.onload = function(){");
        out.println(" fetch('" + request.getContextPath() + "/api/education')");
        out.println(" .then(res => res.json())");
        out.println(" .then(data => { allData = data; buildAllFilters(allData); renderPage(allData); });");
        out.println("};");

        // Fix broken encoding text
        out.println("function cleanText(val){");
        out.println(" if(!val) return '';");
        out.println(" return String(val)");
        out.println("  .replace(/Ã©/g,'é')");
        out.println("  .replace(/Ã¨/g,'è')");
        out.println("  .replace(/Ãª/g,'ê')");
        out.println("  .replace(/Ã /g,'à')");
        out.println("  .replace(/Ã¢/g,'â')");
        out.println("  .replace(/Ã´/g,'ô')");
        out.println("  .replace(/Ã¹/g,'ù')");
        out.println("  .replace(/Ã¼/g,'ü')");
        out.println("  .replace(/Ã¶/g,'ö')");
        out.println("  .replace(/Å¡/g,'š')");
        out.println("  .replace(/Â/g,'')");
        out.println("  .replace(/â‚¬/g,'');");
        out.println("}");

        out.println("function renderPage(data){");
        out.println(" const start = (currentPage-1)*pageSize;");
        out.println(" const pageData = data.slice(start, start+pageSize);");
        out.println(" renderTable(pageData);");
        out.println(" document.getElementById('pageInfo').innerText = 'Page ' + currentPage + ' / ' + Math.ceil(data.length/pageSize);");
        out.println("}");

        out.println("function renderTable(data){");
        out.println(" const tbody = document.getElementById('tableBody');");
        out.println(" tbody.innerHTML = '';");
        out.println(" data.forEach(row => {");
        out.println("  const tr = document.createElement('tr');");
        out.println("  tr.innerHTML = `");
        out.println("   <td><input type='checkbox' class='rowCheck' data-row='${JSON.stringify(row)}'></td>");
        out.println("   <td>${row.id ?? ''}</td>");
        out.println("   <td>${cleanText(row.country)}</td>");
        out.println("   <td>${cleanText(row.course)}</td>");
        out.println("   <td>${cleanText(row.university)}</td>");
        out.println("   <td>${cleanText(row.type)}</td>");
        out.println("   <td>${cleanText(row.application)}</td>");
        out.println("   <td>${cleanText(row.degree)}</td>");
        out.println("   <td>${cleanText(row.language)}</td>");
        out.println("   <td>${cleanText(row.stream)}</td>");
        out.println("   <td>${cleanText(row.semester)}</td>");
        out.println("   <td>${cleanText(row.deadline)}</td>");
        out.println("   <td>${cleanText(row.requirement)}</td>");
        out.println("   <td>${cleanText(row.fee)}</td>");
        out.println("   <td><a href='${row.link ?? '#'}' target='_blank'>Open</a></td>`;");
        out.println("  tbody.appendChild(tr);");
        out.println(" });");
        out.println("}");

        out.println("function buildAllFilters(data){");
        out.println(" const panel = document.getElementById('filterPanel');");
        out.println(" const panelMobile = document.getElementById('filterPanelMobile');");
        out.println(" panel.innerHTML='';");
        out.println(" if(panelMobile) panelMobile.innerHTML='';");

        out.println(" filterColumns.forEach(col => {");
        out.println("  const div = document.createElement('div');");
        out.println("  div.className = 'mb-3';");
        out.println("  div.innerHTML = `<strong>${col.toUpperCase()}</strong><br>`;");
        out.println("  const values = [...new Set(data.map(d=>cleanText(d[col])).filter(v=>v))];");
        out.println("  values.forEach(val => {");
        out.println("   div.innerHTML += `<label><input type='checkbox' data-col='${col}' value='${val}' onchange='applyFilters()'> ${val}</label><br>`;");
        out.println("  });");
        out.println("  panel.appendChild(div);");
        out.println("  if(panelMobile){ panelMobile.appendChild(div.cloneNode(true)); }");
        out.println(" });");
        out.println("}");

        out.println("function applyFilters(){");
        out.println(" const checked = Array.from(document.querySelectorAll('#filterPanel input:checked, #filterPanelMobile input:checked'));");
        out.println(" if(checked.length===0){ currentPage=1; renderPage(allData); return; }");
        out.println(" const active = {};");
        out.println(" checked.forEach(cb=>{ if(!active[cb.dataset.col]) active[cb.dataset.col]=[]; active[cb.dataset.col].push(cb.value); });");
        out.println(" const filtered = allData.filter(row => \r\n"
        		+ "  Object.keys(active).every(col => active[col].includes(cleanText(row[col])))\r\n"
        		+ ");");
   
        
        out.println(" currentPage=1; renderPage(filtered);");
        out.println("}");

        
        out.println("function prevPage(){ if(currentPage>1){ currentPage--; renderPage(allData); } }");
        out.println("function nextPage(){ if(currentPage < Math.ceil(allData.length/pageSize)){ currentPage++; renderPage(allData); } }");

        // CSV Download
        out.println("function downloadCSV(){");
        out.println(" const rows = Array.from(document.querySelectorAll('.rowCheck:checked')).map(cb => JSON.parse(cb.dataset.row));");
        out.println(" if(rows.length===0){ alert('Select at least one row'); return; }");
        out.println(" let csv = Object.keys(rows[0]).join(',') + '\\n';");
        out.println(" rows.forEach(r=>{ csv += Object.values(r).join(',') + '\\n'; });");
        out.println(" const blob = new Blob([csv], {type:'text/csv'});");
        out.println(" const a = document.createElement('a'); a.href = URL.createObjectURL(blob); a.download='data.csv'; a.click();");
        out.println("}");

        // PDF Download
        out.println("function downloadPDF(){ window.print(); }");

        out.println("</script>");
        out.println("</head>");
        out.println("<body>");
     
        // Navbar
        
        out.println("<div class='navbar bg-primary text-white p-2 d-flex flex-wrap gap-2'>");
        out.println("<a href='index.html'>Home</a>");
        out.println("<button class='btn btn-outline-light d-md-none' data-bs-toggle='offcanvas' data-bs-target='#filterOffcanvas'>Filters</button>");
        out.println("<button onclick='downloadCSV()'>Download CSV</button>");
        out.println("<a href='DownloadCsvServlet'><button>&#x21E9; .csv download</button></a>  ");
        out.println("<button onclick='downloadPDF()'>Download PDF</button>");
        out.println("</div>");

        // Mobile Offcanvas Filters
        out.println("<div class='offcanvas offcanvas-start' tabindex='-1' id='filterOffcanvas'>");
        out.println("  <div class='offcanvas-header'>");
        out.println("    <h5 class='offcanvas-title'>Filters</h5>");
        out.println("    <button type='button' class='btn-close' data-bs-dismiss='offcanvas'></button>");
        out.println("  </div>");
        out.println("  <div class='offcanvas-body'>");
        out.println("    <div id='filterPanelMobile'>Loading...</div>");
        out.println("  </div>");
        out.println("</div>");

        // Layout
        //this --- out.println("<div id='layout' style='display:flex;'>");
        out.println("<div class='container-fluid'>\r\n"
        		+ "  <div class='row'>");
        
        // Desktop Filters
        out.println("<div class='col-lg-3 d-none d-lg-block bg-light p-3' id='filtersDesktop'>");
        out.println("<h5>Filters</h5>");
        out.println("<div id='filterPanel'>Loading...</div>");
        out.println("</div>");

        // Table
//  this---      out.println("<div style='flex:1;padding:10px;'>");
        out.println("<div class='col-12 col-lg-9 p-3'>");
        out.println("<div class='table-responsive'>");
        out.println("<table class='table table-bordered table-hover'>");
        out.println("<thead><tr>");
        out.println("<th>Select</th><th>ID</th><th>Country</th><th>Course</th><th>University</th><th>Type</th><th>Application</th>");
        out.println("<th>Degree</th><th>Language</th><th>Stream</th><th>Semester</th><th>Deadline</th><th>Requirement</th><th>Fee</th><th>Link</th>");
        out.println("</tr></thead>");
        out.println("<tbody id='tableBody'></tbody>");
        out.println("</table>");
        out.println("</div>");

        // Pagination
        out.println("<div class='d-flex justify-content-between align-items-center'>");
        out.println("<button class='btn btn-secondary' onclick='prevPage()'>Prev</button>");
        out.println("<span id='pageInfo'></span>");
        out.println("<button class='btn btn-secondary' onclick='nextPage()'>Next</button>");
        out.println("</div>");
               

        out.println("</div>");
        out.println("</div>");
        
        out.println("</body>");
        out.println("<footer>\r\n"
        		+ "<div class=\"data\">\r\n"
        		+ "2025 PFECS Dashboard</div>\r\n"
        		+ "<div class=\"data-contact\">\r\n"
        		+ "Contact : +$9 157 3010 1262 .<br> Mail : \r\n"
        		+ "<a href=\"showRecord.html\">harald.pecher@hpconsulting.de</a></div>\r\n"
        		+ "</footer>");
        out.println("</html>");
    }
}



//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.sql.PreparedStatement;
//
//@WebServlet("/ShowRecordServlet")
//public class ShowRecordServlet extends HttpServlet {
//	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
//	{
//		PrintWriter out=response.getWriter();
//		response.setContentType("text/html");
//		out.println("<!DOCTYPE html>");
//		out.println("<html>");
//		out.println("<head>");
//		out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
//		out.println("<script>\r\n"
//		+ "let selectedCountry = \"\";\r\n"
//		+ "let selectedCourse = \"\";\r\n"
//		+ "\r\n"
//		+ "function loadCountries(){\r\n"
//		+ "    fetch(\"CountryServlet\")\r\n"
//		+ "    .then(res => res.text())\r\n"
//		+ "    .then(data => {\r\n"
//		+ "        document.getElementById(\"outputPanel\").innerHTML = data;\r\n"
//		+ "    });\r\n"
//		+ "}\r\n"
//		+ "\r\n"
//		+ "function selectCountry(country){\r\n"
//		+ "    selectedCountry = country;\r\n"
//		+ "    selectedCourse = \"\";\r\n"
//		+ "    loadCourses();\r\n"
//		+ "}\r\n"
//		+ "\r\n"
//		+ "function loadCourses(){\r\n"
//		+ "    if(selectedCountry === \"\"){\r\n"
//		+ "        alert(\"Please select country first\");\r\n"
//		+ "        return;\r\n"
//		+ "    }\r\n"
//		+ "    fetch(\"CourseServlet?country=\" + selectedCountry)\r\n"
//		+ "    .then(res => res.text())\r\n"
//		+ "    .then(data => {\r\n"
//		+ "        document.getElementById(\"outputPanel\").innerHTML = data;\r\n"
//		+ "    });\r\n"
//		+ "}\r\n"
//		+ "\r\n"
//		+ "function selectCourse(course){\r\n"
//		+ "    selectedCourse = course;\r\n"
//		+ "    loadUniversities();\r\n"
//		+ "}\r\n"
//		+ "\r\n"
//		+ "function loadUniversities(){\r\n"
//		+ "    if(selectedCourse === \"\"){\r\n"
//		+ "        alert(\"Please select course first\");\r\n"
//		+ "        return;\r\n"
//		+ "    }\r\n"
//		+ "    fetch(\"UniversityServlet?country=\" + selectedCountry + \"&course=\" + selectedCourse)\r\n"
//		+ "    .then(res => res.text())\r\n"
//		+ "    .then(data => {\r\n"
//		+ "        document.getElementById(\"outputPanel\").innerHTML = data;\r\n"
//		+ "    });\r\n"
//		+ "}\r\n" //new
//		+"function loadFilter(type){\r\n"
//		+ "    fetch(\"FilterServlet?type=\" + type)\r\n"
//		+ "    .then(res => res.text())\r\n"
//		+ "    .then(data => {\r\n"
//		+ "        document.getElementById(\"outputPanel\").innerHTML = data;\r\n"
//		+ "    });\r\n"
//		+ "}\r\n"
//		+"function filterTable(column, value){\r\n"
//		+ "    fetch(\"FilterServlet?filterColumn=\" + column + \"&filterValue=\" + value)\r\n"
//		+ "    .then(res => res.text())\r\n"
//		+ "    .then(data => {\r\n"
//		+ "        document.getElementById(\"outputPanel\").innerHTML = data;\r\n"
//		+ "    });\r\n"
//		+ "}\r\n"
//		+ "</script>");
//		out.println("<meta charset='UTF-8'>");
//		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
//		out.println("<title>PFECS</title>");
//		out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
//		out.println("<link rel='stylesheet' href='show.css'>");
//		out.println("<link rel='stylesheet' href='" 
//			    + request.getContextPath() 
//			    + "/show.css'>");
//        
//		out.println("</head>");
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pfecsdata","root","root");
//			
//			Statement pst=con.createStatement();
//			ResultSet res=pst.executeQuery("select * from educationdetails");
//			
//			out.println("<body>");
//			//out.println("<body class='bg-light'>");
//			out.println("<div class=\"navbar\">"
//					+ "  <a href=\"index.html\">Home</a>  "
//					+ "<a href='DownloadCsvServlet'><button>&#x21E9; .csv download</button></a>  "
//					+"    <form method=post action='SearchRecordServlet'>"+"<input type=text name=keyword placeholder=\"type here\" >"+ "<input type=submit value='Search'> "
//					+ "</div><br>");
//			out.println("<div class=\"navbar2\">\r\n"
//					//+ "<a onclick='loadCountries()'>Country</a>"
//					+"<button onclick='loadFilter(\"country\")'>Country</button>" //new
//					+"<button onclick='loadFilter(\"course\")'>Course</button>"    //new
//
//					+"<a onclick=\"loadCountries()\">Country</a>\r\n"
//					 + "    <a onclick=\"loadCourses()\">Course</a>\r\n"
//					 + "    <a onclick=\"loadUniversities()\">University</a>"
////					+ "  <a href=\"CountryServlet\">Country</a>\r\n"					 
////					+ "  <a href=\"CourseServlet\">Course</a>\r\n"
////					+ "  <a href=\"UniversityServlet\">University</a>\r\n"
//					+ "  <a href=\"TypeServlet\">Type</a>\r\n"
//					+ "  <a href=\"ApplicationServlet\">Application</a>\r\n"
//					+ "  <a href=\"DegreeServlet\">Degree</a>\r\n"
//					+"  <a href=\"LanguageServlet\">Language</a>"
//					+"  <a href=\"StreamServlet\">Stream</a>"
//					+"  <a href=\"SemesterServlet\">Semester</a>"
//					+ "  <a href=\"DeadlineServlet\">Deadline</a>\r\n"
//					+"  <a href=\"RequirementServlet\">Requirement</a>"
//					+"  <a href=\"FeeServlet\">Fee</a>"
//					+"  <a href=\"LinkServlet\">Link</a>"
//					+ "</div>");
//			
//			
////			out.println("<label>Choose Country:</label><br>");
////			PreparedStatement psCountry =
////			        con.prepareStatement("SELECT DISTINCT Country FROM educationdetails");
////			ResultSet rsCountry = psCountry.executeQuery();
////			while (rsCountry.next()) {
////			    String country = rsCountry.getString("Country");
////			    out.println(
////			        "<div class='form-check'>"
////			      + "<input class='form-check-input' type='checkbox' "
////			      + "name='Country' id='Country_" + country + "' "
////			      + "value='" + country + "' required "
////			      + "onclick=\"showOther('Country','otherCountry')\">"
////			      + "<label class='form-check-label' for='Country_" + country + "'>"
////			      + country
////			      + "</label>"
////			      + "</div>"
////			    );
////			}
//			
////			out.println("<label>Choose Course:</label><br>");
////			PreparedStatement psCourse =
////			        con.prepareStatement("SELECT DISTINCT Course FROM educationdetails");
////			ResultSet rsCourse = psCourse.executeQuery();
////			while (rsCourse.next()) {
////			    String course = rsCourse.getString("Course");
////			    out.println(
////			        "<div class='form-check'>"
////			      + "<input class='form-check-input' type='checkbox' "
////			      + "name='Country' id='Country_" + course + "' "
////			      + "value='" + course + "' required "
////			      + "onclick=\"showOther('Country','otherCountry')\">"
////			      + "<label class='form-check-label' for='Country_" + course + "'>"
////			      + course
////			      + "</label>"
////			      + "</div>"
////			    );
////			}
//			
//			//out.println("<div>");
//
//			
//			out.println( "  <div class=\"table-responsive\">\r\n");
//					//+ "    <table class=\"table table-bordered table-hover align-right\">");
//			out.println("<div id='outputPanel' class='newtag'></div>");
//			out.println("<div class='newtag'><table border=1 >");
//			out.println("<tr><th>ID</th><th>Country</th><th>Course</th><th>University</th><th>Type</th><th>Application</th>"
//					+ "<th>Degree</th><th>Language</th><th>Stream</th><th>Semester</th><th>Deadline</th><th>Requirement</th>"
//					+ "<th>Fee</th><th>Link</th></tr>");
//			while(res.next())
//			{
//				   out.println("<tr><td>"+res.getString(1)+"</td>");	
//				   out.println("<td>"+res.getString(2)+"</td>");	
//				   out.println("<td>"+res.getString(3)+"</td>");	
//				   out.println("<td>"+res.getString(4)+"</td>");	
//				   out.println("<td>"+res.getString(5)+"</td>");	
//				   out.println("<td>"+res.getString(6)+"</td>");	
//				   out.println("<td>"+res.getString(7)+"</td>");	
//				   out.println("<td>"+res.getString(8)+"</td>");	
//				   out.println("<td>"+res.getString(9)+"</td>");	
//				   out.println("<td>"+res.getString(10)+"</td>");	
//				   out.println("<td>"+res.getString(11)+"</td>");	
//				   out.println("<td>"+res.getString(12)+"</td>");
//				   out.println("<td>"+res.getString(13)+"</td>");	
//				   out.println("<td>"+res.getString(14)+"</td></tr>");
//			}
//			out.println("</table>");
//			out.println("</div>");
//			out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
//			out.println("</body>");
//		}
//		catch(ClassNotFoundException ce) {
//			out.println("Class not found exception");
//		}
//		catch(SQLException se) {
//			out.println("SQL Exception");
//		}
//		out.println("</html>");
//	}
//
//
//}
