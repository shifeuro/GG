package ca.qc.cgodin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendLoginForm(response, false);
	}
	
	private void sendLoginForm(HttpServletResponse response, 
		    boolean withErrorMessage)
		    throws ServletException, IOException {
		    
		    response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    out.println("<HTML>");
		    out.println("<HEAD>");
		    out.println("<TITLE>Login</TITLE>");
		    out.println("</HEAD>");
		    out.println("<BODY>");
		    out.println("<CENTER>");
		    System.out.println("got connection");
		    if (withErrorMessage)
		      out.println("Login failed. Please try again.<BR>");

		    out.println("<BR>");
		    out.println("<BR><H2>Login Page</H2>");
		    out.println("<BR>");
		    out.println("<BR>Please enter your user name and password.");
		    out.println("<BR>");
		    out.println("<BR><FORM METHOD=POST>");
		    out.println("<TABLE>");
		    out.println("<TR>");
		    out.println("<TD>User Name:</TD>");
		    out.println("<TD><INPUT TYPE=TEXT NAME=userName></TD>");
		    out.println("</TR>");
		    out.println("<TR>");
		    out.println("<TD>Password:</TD>");
		    out.println("<TD><INPUT TYPE=PASSWORD NAME=password></TD>");
		    out.println("</TR>");
		    out.println("<TR>");
		    out.println("<TD ALIGN=RIGHT COLSPAN=2>");
		    out.println("<INPUT TYPE=SUBMIT VALUE=Login></TD>");
		    out.println("</TR>");
		    out.println("</TABLE>");
		    out.println("</FORM>");
		    out.println("<br><br><a href=\"RegistrationServlet\">SignUp</a>");
		    
		    out.println("</CENTER>");
		    out.println("</BODY>");
		    out.println("</HTML>");
		  }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
	    String password = request.getParameter("password");
	    PrintWriter out = response.getWriter();
	    if (login(request, response, userName, password)) {
	      RequestDispatcher rd =
	        request.getRequestDispatcher("WelcomeServlet");
	      rd.forward(request, response);
	    }
	    else {
	      sendLoginForm(response, true);
	    }
	}
	
	boolean login(HttpServletRequest request, HttpServletResponse response, String userName, String password)throws ServletException, IOException {
	    try {
	    	PrintWriter out = response.getWriter();
	    	// This will load the MySQL driver, each DB has its own driver
	        Class.forName("com.mysql.jdbc.Driver");
	        //out.println("<br>TestLogin");
	        // Setup the connection with the DB
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cgodin?" + "user=root&password=cgodin2013");
	        //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cgodin?" + "user=root&password=cgodin2013");
	        //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cgodin", "root", "cgodin2013");

	      Statement s = con.createStatement();
	      String sql = "SELECT UserName FROM Users" +
	        " WHERE UserName='" + userName + "'" +
	        " AND Password='" + password + "'";
	      ResultSet rs = s.executeQuery(sql);
	      if (rs.next()) {
	        rs.close();
	        s.close();
	        con.close();
	        return true;
	      }
	      rs.close();
	      s.close();
	      con.close();
	    }
	    catch (ClassNotFoundException e) {
	      System.out.println(e.toString());
	    }
	    catch (SQLException e) {
	      System.out.println(e.toString());
	    }
	    catch (Exception e) {
	      System.out.println(e.toString());
	    }
	    return false;
	  }

}
