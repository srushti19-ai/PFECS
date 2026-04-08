import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DownloadExcelServlet")
public class DownloadExcelServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws IOException {

        InputStream is = getServletContext()
                .getResourceAsStream("/pdf/brochure.pdf");

        if (is == null) {
            response.getWriter().println("PDF not found");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=brochure.pdf");

        OutputStream os = response.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        
        PrintWriter out = response.getWriter();

//        try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/pfecsdata","root","root");
//			
//			Statement pst=con.createStatement();
//			ResultSet res=pst.executeQuery("select * from educationdetails");
//			
//			while(res.next())
//			{
//				   out.println(res.getString(1)+","+
//				   res.getString(2)+","+
//				   res.getString(3)+","+
//				   res.getString(4)+","+
//				   res.getString(5)+","+
//				   res.getString(6)+","+
//				   res.getString(7)+","+
//				   res.getString(8)+","+
//				   res.getString(9)+","+	
//				   res.getString(10)+","+
//				   res.getString(11)+","+
//				   res.getString(12)+","+
//				   res.getString(13)+","+	
//				   res.getString(14));
//			}
//		}
//		catch(ClassNotFoundException ce) {
//			out.println("Class not found exception");
//		}
//		catch(SQLException se) {
//			out.println("SQL Exception");
//		}

        
        is.close();
        os.flush();
        os.close();
    }
    
}

