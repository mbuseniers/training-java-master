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
import services.ComputerService;

public class EditComputerServlet extends HttpServlet {

	private DAOComputer dao = DAOComputer.getInstance();
	private DAOCompany daoCompany = DAOCompany.getInstance();
	private ComputerService computerService = ComputerService.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<Company> listeCompanies;

		listeCompanies = daoCompany.getCompanies();
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
		listeCompanies = daoCompany.getCompanies();
		request.setAttribute("listeCompanies", listeCompanies);

		int computerId = Integer.valueOf(request.getParameter("computerId"));
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		int companyId = Integer.valueOf(request.getParameter("companyId"));

		boolean isNameOk = !name.equals("");
		boolean isIntroducedOk = false, isDiscontinuedOk = false;
		boolean isCompanyOk = companyId > 0 && companyId <= listeCompanies.size();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		if (!introduced.equals("")) {
			try {
				LocalDate.parse(introduced, formatter);
				isIntroducedOk = true;
				request.setAttribute("introduced", introduced);
			} catch (DateTimeParseException e) {
				request.setAttribute("messageErrorIntroduced", "Syntaxe incorrecte");
			}
		} else {
			isIntroducedOk = true;
		}

		if (!discontinued.equals("")) {
			try {
				LocalDate.parse(discontinued, formatter);
				isDiscontinuedOk = true;
				request.setAttribute("discontinued", discontinued);
			} catch (DateTimeParseException e) {
				request.setAttribute("messageErrorDiscontinued", "Syntaxe incorrecte");

			}
		} else {
			isDiscontinuedOk = true;
		}

		if (!isNameOk) {
			request.setAttribute("messageErrorName", "le Nom ne peut etre vide");
		}else {
			request.setAttribute("name", name);
		}

		if (!isCompanyOk) {
			request.setAttribute("messageErrorCompany", "La company n'a pas été trouvée");
		}else {
			request.setAttribute("companyId", companyId);
		}

		if (isNameOk && isIntroducedOk && isDiscontinuedOk && isCompanyOk) {

			boolean isEditOk = computerService.editComputer(computerId, name, introduced, discontinued, companyId);

			if (isEditOk) {
				request.setAttribute("messageEdit", "Edit Ok");
			} else {
				request.setAttribute("messageEdit", "Probleme lors de l'edit");
			}

		}

		request.setAttribute("id", computerId);

		this.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);

	}

}
