package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import configuration.SpringConfiguration;
import model.Company;
import services.CompanyService;
import services.ComputerService;
import services.ValidatorService;

public class EditComputerServlet extends HttpServlet {

	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	private ValidatorService validatorService = ValidatorService.getInstance();

	public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<Company> listeCompanies;

		listeCompanies = companyService.getCompanies();
		request.setAttribute("listeCompanies", listeCompanies);

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("company");

		if (introduced == null) {
			introduced = "";
		}

		if (discontinued == null) {
			discontinued = "";
		}

		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("introduced", introduced);
		request.setAttribute("discontinued", discontinued);
		request.setAttribute("companyId", companyId);

		this.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);

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
		} else {
			request.setAttribute("name", request.getParameter("computerName"));
		}

		if (!isCompanyOk) {
			request.setAttribute("messageErrorCompany", "La company n'a pas été trouvée");
		} else {
			request.setAttribute("companyId", Integer.valueOf(request.getParameter("companyId")));
		}

		if (!isIntroducedOk) {
			request.setAttribute("messageErrorIntroduced", "Erreur de syntax");
		} else {
			request.setAttribute("introduced", request.getParameter("introduced"));
		}

		if (!isDiscontinuedOk) {
			request.setAttribute("messageErrorDiscontinued", "Erreur de syntax");
		} else {
			request.setAttribute("introduced", request.getParameter("discontinued"));
		}

		if (isNameOk && isIntroducedOk && isDiscontinuedOk && isCompanyOk) {

			boolean isEditOk = false;
			isEditOk = computerService.editComputer(Integer.valueOf(request.getParameter("computerId")),
					request.getParameter("computerName"), request.getParameter("introduced"),
					request.getParameter("discontinued"), Integer.valueOf(request.getParameter("companyId")));

			if (isEditOk) {
				request.setAttribute("messageEdit", "Edit Ok");
			} else {
				request.setAttribute("messageEdit", "Probleme lors de l'edit");
			}

		}

		request.setAttribute("id", Integer.valueOf(request.getParameter("companyId")));

		this.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);

	}

}
