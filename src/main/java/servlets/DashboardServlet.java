package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import configuration.SpringConfiguration;
import dto.ComputerDTO;
import services.ComputerService;


@Controller
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ComputerService computerService; 
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);
	
	public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		LOGGER.info("doGet servlet dashboard");

		doPagination(request, response);

		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("doPOst servlet dashboard");
		ArrayList<ComputerDTO> listeComputers = null;
		int page = 0, size = 50, intervalMin = 0, intervalMax = 0;
		int nombreComputers = 0;
		String actionType = request.getParameter("actionType");

		if (actionType.equals("DELETE")) {

			boolean isDeleteOk = false;
				isDeleteOk = computerService.deleteComputer(request.getParameter("selection"));
			
			if (isDeleteOk) {
				request.setAttribute("messageDelete", "Supression OK");
			} else {
				request.setAttribute("messageDelete", "Probleme de suppression");
			}

			intervalMin=0;
			intervalMax=2;
			nombreComputers = computerService.getNumberComputers();
			listeComputers = computerService.getComputersByLimitAndOffset(size, page);

		} else if (actionType.equals("SEARCH")) {
			LOGGER.info("doPOst SEARCH");
			String search = request.getParameter("search");
			if (!search.equals("")) {

				if (request.getParameter("searchBy").equals("Filter by name")) {
					LOGGER.info("SEARCH by name");
					listeComputers = computerService.getComputersByName(search);
				} else if (request.getParameter("searchBy").equals("Filter by company")) {
					LOGGER.info("SEARCH by company");
					listeComputers = computerService.getComputersByCompanyName(search);
				}
				
				size=100;
				intervalMin=0;
				intervalMax=0;
				
				nombreComputers = listeComputers.size();

			} else {
				request.setAttribute("messageErreurSearch", "La recherche ne peut être nulle");
				listeComputers = null;
			}

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

		ArrayList<ComputerDTO> listeComputers;

		listeComputers = computerService.getComputersByLimitAndOffset(nombreComputersByPage,
				numeroPage * nombreComputersByPage);

		request.setAttribute("liste", listeComputers);
		request.setAttribute("page", numeroPage);
		request.setAttribute("size", nombreComputersByPage);
		request.setAttribute("intervalMin", intervalPageMin);
		request.setAttribute("intervalMax", intervalPageMax);
		request.setAttribute("nombreComputers", nombreComputers);
	}

}
