package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOCompany;
import dao.DAOComputer;
import model.Company;
import model.Computer;
import services.ComputerService;

public class AddComputerServlet extends HttpServlet{

	private DAOComputer dao =  DAOComputer.getInstance();
	private DAOCompany daoCompany = DAOCompany.getInstance();
	private ComputerService computerService = ComputerService.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("do GET add computer");
		
		ArrayList<Company> listeCompanies;
		
		listeCompanies = daoCompany.getCompanies();
		request.setAttribute("listeCompanies", listeCompanies);

		
		this.getServletContext().getRequestDispatcher( "/views/addComputer.jsp" ).forward( request, response );

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String message_ajout = computerService.addComputer(request.getParameter("computerName"), 
														   request.getParameter("introduced"), 
														   request.getParameter("discontinued"), 
														   Integer.valueOf(request.getParameter("companyId")));
		
		
		request.setAttribute("messageAjout", message_ajout);


		this.getServletContext().getRequestDispatcher( "/views/addComputerResponse.jsp" ).forward( request, response );

	}

	
}
