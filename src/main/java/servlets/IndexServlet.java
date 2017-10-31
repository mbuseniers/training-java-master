package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Bonjour</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<H1>Bonjour</H1>");
		out.println("</BODY>");
		out.println("</HTML>");
		
		//this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );
		this.getServletContext().getRequestDispatcher( "/views/dashboard.jsp" ).forward( request, response );
	}

}
