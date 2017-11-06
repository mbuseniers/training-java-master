package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import utils.Utils;

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
		
		ArrayList<Company> listeCompanies;
		listeCompanies = daoCompany.getCompanies();
		request.setAttribute("listeCompanies", listeCompanies);
		
		boolean isNameOk = !request.getParameter("computerName").equals("");
		boolean isIntroducedOk =false, isDiscontinuedOk = false;
		
		int companyId = Integer.valueOf(request.getParameter("companyId"));
		boolean isCompanyOk =  companyId > 0 && companyId <= listeCompanies.size() ;
		
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		if(!introduced.equals("")) {
			try {
				LocalDate.parse(introduced, formatter);
				isIntroducedOk = true;
			} catch (DateTimeParseException e) {
				request.setAttribute("messageErrorIntroduced", "Syntaxe incorrecte");
			}
		}else {
			isIntroducedOk = true;
		}

		if(!discontinued.equals("")) {
			try {
				LocalDate.parse(discontinued, formatter);
				isDiscontinuedOk = true;
			} catch (DateTimeParseException e) {
				request.setAttribute("messageErrorDiscontinued", "Syntaxe incorrecte");

			}
		}else {
			isDiscontinuedOk = true;
		}
		
		if(!isNameOk) {
			request.setAttribute("messageErrorName", "le Nom ne peut etre vide" );
		}
		
		if(!isCompanyOk) {
			request.setAttribute("messageErrorCompany", "La company n'a pas été trouvée");
		}
		
		if(isNameOk && isIntroducedOk && isDiscontinuedOk && isCompanyOk) {
			boolean isAddOk = computerService.addComputer(request.getParameter("computerName"), 
					   request.getParameter("introduced"), 
					   request.getParameter("discontinued"), 
					   companyId);
			if(isAddOk) {
			request.setAttribute("messageAjout", "Ajout OK");
			
			}else {
			request.setAttribute("messageAjout", "Problème lors de l'ajout");
			}
		}
		


		
		this.getServletContext().getRequestDispatcher( "/views/addComputer.jsp" ).forward( request, response );

	}

	
}
