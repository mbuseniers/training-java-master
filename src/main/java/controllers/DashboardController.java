package controllers;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dto.ComputerDTO;
import services.ComputerService;

@Controller
@EnableWebMvc
@RequestMapping("/dashboard")
public class DashboardController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ComputerService computerService;
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@GetMapping
	protected ModelAndView doGet(@RequestParam Map<String, String> parameters) {
		LOGGER.info("doGet servlet dashboard");
		ModelAndView modelAndView = new ModelAndView("/views/dashboard.jsp");
		doPagination(modelAndView, parameters);
		return modelAndView;
	}

	@PostMapping
	public ModelAndView doPost(@RequestParam Map<String, String> parameters) {
		LOGGER.info("doPOst servlet dashboard");
		ModelAndView modelAndView = new ModelAndView("/views/dashboard.jsp");
		ArrayList<ComputerDTO> listeComputers = null;
		int page = 0, size = 50, intervalMin = 0, intervalMax = 0;
		int nombreComputers = 0;
		String actionType = parameters.get("actionType");

		if (actionType.equals("DELETE")) {

			boolean isDeleteOk = false;
			isDeleteOk = computerService.deleteComputer(parameters.get("selection"));

			if (isDeleteOk) {
				modelAndView.addObject("messageDelete", "Supression OK");
			} else {
				modelAndView.addObject("messageDelete", "Probleme de suppression");
			}

			intervalMin = 0;
			intervalMax = 2;
			nombreComputers = computerService.getNumberComputers();
			listeComputers = computerService.getComputersByLimitAndOffset(size, page);

		} else if (actionType.equals("SEARCH")) {
			LOGGER.info("doPOst SEARCH");
			String search = parameters.get("search");
			if (!search.equals("")) {

				if (parameters.get("searchBy").equals("Filter by name")) {
					LOGGER.info("SEARCH by name");
					listeComputers = computerService.getComputersByName(search);
				} else if (parameters.get("searchBy").equals("Filter by company")) {
					LOGGER.info("SEARCH by company");
					listeComputers = computerService.getComputersByCompanyName(search);
				}

				size = 100;
				intervalMin = 0;
				intervalMax = 0;

				nombreComputers = listeComputers.size();

			} else {
				modelAndView.addObject("messageErreurSearch", "La recherche ne peut être nulle");
				listeComputers = null;
			}

		}

		modelAndView.addObject("liste", listeComputers);
		modelAndView.addObject("page", page);
		modelAndView.addObject("size", size);
		modelAndView.addObject("intervalMin", intervalMin);
		modelAndView.addObject("intervalMax", intervalMax);
		modelAndView.addObject("nombreComputers", nombreComputers);

		return modelAndView;

	}

	public void doPagination(ModelAndView modelAndView, Map<String, String> parameters) {
		int numeroPage = 0, nombreComputersByPage = 0, intervalPageMin = 0, intervalPageMax = 2, nombrePageMax = 0;
		int nombreComputers = computerService.getNumberComputers();

		if (parameters.get("changeSize") == null && parameters.get("changePage") == null) {
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
		} else if (parameters.get("changeSize") != null) {
			nombreComputersByPage = Integer.valueOf(parameters.get("size"));
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

		} else if (parameters.get("changePage") != null) {
			numeroPage = Integer.valueOf(parameters.get("page"));
			nombreComputersByPage = Integer.valueOf(parameters.get("size"));

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

		modelAndView.addObject("liste", listeComputers);
		modelAndView.addObject("page", numeroPage);
		modelAndView.addObject("size", nombreComputersByPage);
		modelAndView.addObject("intervalMin", intervalPageMin);
		modelAndView.addObject("intervalMax", intervalPageMax);
		modelAndView.addObject("nombreComputers", nombreComputers);
	}

}
