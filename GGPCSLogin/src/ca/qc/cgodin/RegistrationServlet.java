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
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String firstName = "";
	  private String lastName = "";
	  private String userName = "";
	  private String password = "";

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
		sendPageHeader(response);
	    sendRegistrationForm(request, response, false);
	    sendPageFooter(response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendPageHeader(response);

	    firstName = request.getParameter("firstName");
	    lastName = request.getParameter("lastName");
	    userName = request.getParameter("userName");
	    password = request.getParameter("password");

	    boolean error = false;
	    String message = null;
	    try {
	      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cgodin?" + "user=root&password=cgodin2013");
	      System.out.println("got connection");

	      Statement s = con.createStatement();

	      String sql = "SELECT UserName FROM Users" +
	        " WHERE userName='" + StringUtil.fixSqlFieldValue(userName) + "'";
	      ResultSet rs = s.executeQuery(sql);
	      if (rs.next()) {
	        rs.close();
	        message = "The user name <B>" + StringUtil.encodeHtmlTag(userName) +
	          "</B> has been taken. Please select another name.";
	        error = true;
	      }
	      else {
	        rs.close();
	        sql = "INSERT INTO Users" +
	          " (FirstName, LastName, UserName, Password)" +
	          " VALUES" +
	          " ('" +  StringUtil.fixSqlFieldValue(firstName) + "'," +
	          " '" +  StringUtil.fixSqlFieldValue(lastName) + "'," +
	          " '" +  StringUtil.fixSqlFieldValue(userName) + "'," +
	          " '" +  StringUtil.fixSqlFieldValue(password) + "')";
	        int i = s.executeUpdate(sql);
	        if (i==1) {
	          message = "Successfully added one user.";
	        }
	      }
	      s.close();
	      con.close();
	    }
	    catch (SQLException e) {
	      message = "Error." + e.toString();
	      error = true;
	    }
	    catch (Exception e) {
	      message = "Error." + e.toString();
	      error = true;
	    }
	    if (message!=null) {
	      PrintWriter out = response.getWriter();
	      out.println("<B>" + message + "</B><BR>");
	      out.println("<HR><BR>");
	    }
	    if (error==true)
	      sendRegistrationForm(request, response, true);
	    else
	      sendRegistrationForm(request, response, false);
	    sendPageFooter(response);
	}

	
	/**
	   * Send the HTML page header, including the title
	   * and the <BODY> tag
	   */
	  private void sendPageHeader(HttpServletResponse response)
	    throws ServletException, IOException {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println("<HTML>");
	    out.println("<HEAD>");
	    out.println("<TITLE>Registration Page</TITLE>");
	    out.println("</HEAD>");
	    out.println("<BODY>");
	    out.println("<CENTER>");
	  }

	  /**
	   * Send the HTML page footer, i.e. the </BODY>
	   * and the </HTML>
	   */
	  private void sendPageFooter(HttpServletResponse response)
	    throws ServletException, IOException {
	    PrintWriter out = response.getWriter();
	    out.println("</CENTER>");
	    out.println("</BODY>");
	    out.println("</HTML>");
	  }

	  /**Send the form where the user can type in
	   * the details for a new user
	   */
	  private void sendRegistrationForm(HttpServletRequest request,
	    HttpServletResponse response, boolean displayPreviousValues)
	    throws ServletException, IOException {

	    PrintWriter out = response.getWriter();
	    out.println("<BR><H2>Registration Page</H2>");
	    out.println("<BR>Please enter the user details.");
	    out.println("<BR>");
	    out.println("<BR><FORM METHOD=POST>");
	    out.println("<TABLE>");
	    out.println("<TR>");
	    out.println("<TD>First Name</TD>");
	    out.print("<TD><INPUT TYPE=TEXT Name=firstName");

	    if (displayPreviousValues)
	      out.print(" VALUE=\"" + StringUtil.encodeHtmlTag(firstName) + "\"");

	    out.println("></TD>");
	    out.println("</TR>");
	    out.println("<TR>");
	    out.println("<TD>Last Name</TD>");
	    out.print("<TD><INPUT TYPE=TEXT Name=lastName");

	    if (displayPreviousValues)
	      out.print(" VALUE=\"" + StringUtil.encodeHtmlTag(lastName) + "\"");

	    out.println("></TD>");
	    out.println("</TR>");
	    out.println("<TR>");
	    out.println("<TD>User Name</TD>");
	    out.print("<TD><INPUT TYPE=TEXT Name=userName");

	    if (displayPreviousValues)
	      out.print(" VALUE=\"" + StringUtil.encodeHtmlTag(userName) + "\"");
	    out.println("></TD>");
	    out.println("</TR>");
	    out.println("<TR>");
	    out.println("<TD>Password</TD>");
	    out.print("<TD><INPUT TYPE=PASSWORD Name=password");

	    if (displayPreviousValues)
	      out.print(" VALUE=\"" + StringUtil.encodeHtmlTag(password) + "\"");

	    out.println("></TD>");
	    out.println("</TR>");
	    out.println("<TR>");
	    out.println("<TD><INPUT TYPE=RESET></TD>");
	    out.println("<TD><INPUT TYPE=SUBMIT></TD>");
	    out.println("</TR>");
	    out.println("</TABLE>");
	    out.println("</FORM>");
	    out.println("<BR>");
	    out.println("<BR>");
	  }
}
