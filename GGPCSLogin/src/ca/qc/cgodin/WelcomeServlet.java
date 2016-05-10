package ca.qc.cgodin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println("<HTML>");
	    out.println("<HEAD>");
	    out.println("<TITLE>Welcome</TITLE>");
	    out.println("</HEAD>");
	    out.println("<BODY>");
	    out.println("<P>Welcome to cgodin Web Students WebSite.</P>");
	    out.println("<br/><br/><a href=\"DataViewerServlet\">Display all users!</a>");
	    out.println("<br/><br/><a href=\"SearchServlet\">Search User!</a>");
	    out.println("</BODY>");
	    out.println("</HTML>");
	}

}
