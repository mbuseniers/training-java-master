package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Company;
import services.CompanyService;
import services.ComputerService;
import services.ValidatorService;

public class AddComputerServlet extends HttpServlet {

	private CompanyService companyService = CompanyService.getInstance();
	private ComputerService computerService = ComputerService.getInstance();
	private ValidatorService validatorService = ValidatorService.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<Company> listeCompanies;

		listeCompanies = companyService.getCompanies();
		request.setAttribute("listeCompanies", listeCompanies);

		this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<Company> listeCompanies;
		listeCompanies = companyService.getCompanies();
		request.setAttribute("listeCompanies", listeCompanies);

		boolean isNameOk = validatorService.checkName(request.getParameter("computerName"));
		boolean isIntroducedOk = validatorService.checkDateFromString(request.getParameter("introduced"));
		boolean isDiscontinuedOk = validatorService.checkDateFromString(request.getParameter("discontinued"));
		boolean isCompanyOk = validatorService.checkCompany(Integer.valueOf(request.getParameter("companyId")),
				listeCompanies.size());

		if (!isNameOk) {
			request.setAttribute("messageErrorName", "le Nom ne peut etre vide");
		}

		if (!isCompanyOk) {
			request.setAttribute("messageErrorCompany", "La company n'a pas été trouvée");
		}

		if (!isIntroducedOk) {
			request.setAttribute("messageErrorIntroduced", "Erreur de syntax");
		}

		if (!isDiscontinuedOk) {
			request.setAttribute("messageErrorIntroduced", "Erreur de syntax");
		}

		if (isNameOk && isIntroducedOk && isDiscontinuedOk && isCompanyOk) {
			boolean isAddOk = false;
			isAddOk = computerService.addComputer(request.getParameter("computerName"),
					request.getParameter("introduced"), request.getParameter("discontinued"),
					Integer.valueOf(request.getParameter("companyId")));

			if (isAddOk) {
				request.setAttribute("messageAjout", "Ajout OK");

			} else {
				request.setAttribute("messageAjout", "Problème lors de l'ajout");
			}
		}

		this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);

	}

}
