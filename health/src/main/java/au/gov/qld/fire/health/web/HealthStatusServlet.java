package au.gov.qld.fire.health.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Valeri SHIBAEV (mailto:mail@apollosoft.net.net)
 */
public class HealthStatusServlet extends HttpServlet
{

	/** serialVersionUID */
	private static final long serialVersionUID = 8172494159100644728L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	{
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();		
	    out.println("<html>");
	    out.println("<body>");
	    out.println("JMS Health Status - OK!");
	    out.println("</body>");
	    out.println("</html>");
	}

}