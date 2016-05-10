package ca.qc.cgodin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DataViewerServlet
 */
@WebServlet("/DataViewerServlet")
public class DataViewerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**Load JDBC driver*/
	  public void init() {
	    try {
	    	Class.forName("com.mysql.jdbc.Driver");
	      System.out.println("JDBC driver loaded");
	    }
	    catch (ClassNotFoundException e) {
	      System.out.println(e.toString());
	    }
	  }
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

	    PrintWriter out = response.getWriter();
	    out.println("<HTML>");
	    out.println("<HEAD>");
	    out.println("<TITLE>Display All Users</TITLE>");
	    out.println("</HEAD>");
	    out.println("<BODY>");
	    out.println("<CENTER>");
	    out.println("<BR><H2>Displaying All Users</H2>");
	    out.println("<BR>");
	    out.println("<BR>");
	    out.println("<TABLE>");
	    out.println("<TR>");
	    out.println("<TH>First Name</TH>");
	    out.println("<TH>Last Name</TH>");
	    out.println("<TH>User Name</TH>");
	    out.println("<TH>Password</TH>");
	    out.println("</TR>");

	    String sql = "SELECT FirstName, LastName, UserName, Password" +
	      " FROM Users";
	    try {
	      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cgodin?" + "user=root&password=cgodin2013");
	      System.out.println("got connection");

	      Statement s = con.createStatement();
	      ResultSet rs = s.executeQuery(sql);

	      while (rs.next()) {
	        out.println("<TR>");
	        out.println("<TD>" + StringUtil.encodeHtmlTag(rs.getString(1)) + "</TD>");
	        out.println("<TD>" + StringUtil.encodeHtmlTag(rs.getString(2)) + "</TD>");
	        out.println("<TD>" + StringUtil.encodeHtmlTag(rs.getString(3)) + "</TD>");
	        out.println("<TD>" + StringUtil.encodeHtmlTag(rs.getString(4)) + "</TD>");
	        out.println("</TR>");
	      }
	      rs.close();
	      s.close();
	      con.close();
	    }
	    catch (SQLException e) {
	    }
	    catch (Exception e) {
	    }
	    out.println("</TABLE>");
	    out.println("</CENTER>");
	    out.println("</BODY>");
	    out.println("</HTML>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
