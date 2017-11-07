package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOComputer;
import model.Computer;
import services.ComputerService;
import services.ValidatorService;

public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ComputerService computerService = ComputerService.getInstance();
	private ValidatorService validatorService = ValidatorService.getInstance();

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		doPagination(request, response);
		
		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean isDeleteOk = computerService.deleteComputer(request.getParameter("selection"));
		int page=0, size=50, intervalMin=0, intervalMax=4;
		
		if (isDeleteOk) {
			request.setAttribute("messageDelete", "Supression OK");
		} else {
			request.setAttribute("messageDelete", "Probleme de suppression");
		}

		int nombreComputers = computerService.getNumberComputers();
		ArrayList<Computer> listeComputers;

		try {

			listeComputers = computerService.getComputersByLimitAndOffset(size, page);
		} catch (SQLException e) {
			listeComputers = null;
		}

		request.setAttribute("liste", listeComputers);
		request.setAttribute("page", page);
		request.setAttribute("size", size);
		request.setAttribute("intervalMin", intervalMin);
		request.setAttribute("intervalMax", intervalMax);
		request.setAttribute("nombreComputers", nombreComputers);

		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);

	}

	public void doPagination(HttpServletRequest request, HttpServletResponse response) {
		int numeroPage = 0, nombreComputersByPage = 0, intervalPageMin = 0, intervalPageMax = 2, nombrePageMax = 0;
		int nombreComputers = computerService.getNumberComputers();

		if (request.getParameter("changeSize") == null && request.getParameter("changePage") == null) {
			numeroPage = 0;
			nombreComputersByPage = 50;

			nombrePageMax = nombreComputers / nombreComputersByPage;
			if (nombreComputers % nombreComputersByPage > 0) {
				nombrePageMax++;
			}

			if (nombrePageMax < intervalPageMax) {
				intervalPageMax = nombrePageMax;
			}
			intervalPageMin = 0;
		} else if (request.getParameter("changeSize") != null) {
			nombreComputersByPage = Integer.valueOf(request.getParameter("size"));
			numeroPage = 0;

			if (nombreComputersByPage < 0 || nombreComputersByPage > 100) {
				nombreComputersByPage = 50;
			}

			nombrePageMax = nombreComputers / nombreComputersByPage;
			if (nombreComputers % nombreComputersByPage > 0) {
				nombrePageMax++;
			}

			intervalPageMin = 0;
			if (nombrePageMax < intervalPageMax) {
				intervalPageMax = nombrePageMax;
			}

		} else if (request.getParameter("changePage") != null) {
			numeroPage = Integer.valueOf(request.getParameter("page"));
			nombreComputersByPage = Integer.valueOf(request.getParameter("size"));

			if (nombreComputersByPage < 0 || nombreComputersByPage > 100) {
				nombreComputersByPage = 50;
			}

			nombrePageMax = nombreComputers / nombreComputersByPage;
			if (nombreComputers % nombreComputersByPage > 0) {
				nombrePageMax++;
			}

			if (numeroPage < 0) {
				numeroPage = 0;
			} else if (numeroPage >= nombrePageMax) {
				numeroPage = nombrePageMax - 1;
			}

			if (numeroPage == 0 || numeroPage == 1) {
				intervalPageMin = 0;
			} else {
				intervalPageMin = numeroPage - 2;
			}

			if (numeroPage == nombrePageMax || numeroPage == nombrePageMax - 1 || numeroPage == nombrePageMax - 2) {
				intervalPageMax = nombrePageMax - 1;
			} else {
				intervalPageMax = numeroPage + 2;
			}
		}

		ArrayList<Computer> listeComputers;

		try {

			listeComputers = computerService.getComputersByLimitAndOffset(nombreComputersByPage,
					numeroPage * nombreComputersByPage);
		} catch (SQLException e) {
			listeComputers = null;
		}

		request.setAttribute("liste", listeComputers);
		request.setAttribute("page", numeroPage);
		request.setAttribute("size", nombreComputersByPage);
		request.setAttribute("intervalMin", intervalPageMin);
		request.setAttribute("intervalMax", intervalPageMax);
		request.setAttribute("nombreComputers", nombreComputers);
	}

}
