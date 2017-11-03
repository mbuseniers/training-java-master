package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOComputer;
import model.Computer;







public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private DAOComputer dao =  DAOComputer.getInstance();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		int numeroPage;
		int nombreComputers = dao.getNumberComputers();
		int nombreComputersByPage = 50;
		
		if(request.getParameter("page") != null) {
			numeroPage = Integer.valueOf(request.getParameter( "page"));
			if(numeroPage < 0 ) {
				numeroPage=0;
			}
			
			if(numeroPage > (nombreComputers/nombreComputersByPage)+1) {
				numeroPage = nombreComputers/nombreComputersByPage;
			}
		}else {
			numeroPage = 0;
		}
		
		
		ArrayList<Computer> listeComputers;
		
		try {

			listeComputers = dao.getComputersByLimitAndOffset(nombreComputersByPage,numeroPage);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			listeComputers = null;
			e.printStackTrace();
		}
		

		
		request.setAttribute( "nombreComputers", nombreComputers );
		
		request.setAttribute("liste", listeComputers);
		request.setAttribute("page", numeroPage);
		request.setAttribute("nombreComputers", nombreComputers);
		
		this.getServletContext().getRequestDispatcher( "/views/dashboard.jsp" ).forward( request, response );
	}

}
