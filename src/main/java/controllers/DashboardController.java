package controllers;

import java.util.ArrayList;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dto.ComputerDTO;
import model.Company;
import services.CompanyService;
import services.ComputerService;
import services.Page;

@Controller
@EnableWebMvc
public class DashboardController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Page page;

	@Autowired
	private ComputerService computerService;

	@Autowired
	private CompanyService companyService;
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@GetMapping("/dashboard")
	protected String getDashboard(@RequestParam Map<String, String> parameters, ModelMap model) {
		LOGGER.info("doGet servlet dashboard");
		page.doPagination(model, parameters);
		return "dashboard";
	}

	@PostMapping("/dashboard")
	public String searchComputers(@RequestParam("searchBy") String searchBy, @RequestParam("search") String search,
			ModelMap model) {
		LOGGER.info("doPost servlet dashboard");
		ArrayList<ComputerDTO> listeComputers = null;
		int page = 0, size = 50, intervalMin = 0, intervalMax = 0;
		int nombreComputers = 0;
		if (!search.equals("")) {

			if (searchBy.equals("Filter by name")) {
				LOGGER.info("SEARCH by name");
				listeComputers = computerService.getComputersByName(search);
			} else if (searchBy.equals("Filter by company")) {
				LOGGER.info("SEARCH by company");
				listeComputers = computerService.getComputersByCompanyName(search);
			}
			size = 100;
			intervalMin = 0;
			intervalMax = 0;
			nombreComputers = listeComputers.size();
		} else {
			model.addAttribute("messageErreurSearch", "La recherche ne peut être nulle");
			return "redirect:/dashboard";
		}
		model.addAttribute("liste", listeComputers);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("intervalMin", intervalMin);
		model.addAttribute("intervalMax", intervalMax);
		model.addAttribute("nombreComputers", nombreComputers);
		return "dashboard";
	}

	@PostMapping("/deletecomputers")
	public String deleteComputers(@RequestParam("selection") String selection, ModelMap model) {
		LOGGER.info("DELETE CPT");
		boolean isDeleteOk = false;
		isDeleteOk = computerService.deleteComputer(selection);
		return "redirect:/dashboard";

	}

	@GetMapping("/addcomputer")
	public String getPageAddComputer(ModelMap model) {
		ArrayList<Company> listCompanies;
		listCompanies = companyService.getCompanies();
		model.addAttribute("listeCompanies", listCompanies);
		model.addAttribute("computerDTO", new ComputerDTO());
		return "addcomputer";
	}

	@PostMapping("/addcomputer")
	public String sendFormAddComputer(@Valid ComputerDTO computerDTO, BindingResult bindingResult, ModelMap model) {
		ArrayList<Company> listCompanies;
		listCompanies = companyService.getCompanies();
		model.addAttribute("listeCompanies", listCompanies);

		LOGGER.info(computerDTO.getName());
		LOGGER.info(computerDTO.getDateIntroduced());
		LOGGER.info(computerDTO.getDateDiscontinued());

		if (bindingResult.hasErrors()) {
			LOGGER.info("erreur dans le formulaire");
			return "addcomputer";
		} else {
			LOGGER.info("pas d'erreur dans le formulaire");
			computerService.addComputer(computerDTO.getName(), computerDTO.getDateIntroduced(),
					computerDTO.getDateDiscontinued(), computerDTO.getCompanyId());
			return "redirect:dashboard";
		}

	}

	@GetMapping("/editcomputer")
	public String getPageEditComputer(ModelMap model, @RequestParam("id") String id,
			@RequestParam("name") String computerName, @RequestParam("introduced") String introduced,
			@RequestParam("discontinued") String discontinued, @RequestParam("company") String company) {

		ArrayList<Company> listeCompanies;

		listeCompanies = companyService.getCompanies();
		model.addAttribute("listeCompanies", listeCompanies);
		introduced = introduced == null ? "" : introduced;
		discontinued = introduced == null ? "" : discontinued;

		model.addAttribute("id", id);
		model.addAttribute("name", computerName);
		model.addAttribute("introduced", introduced);
		model.addAttribute("discontinued", discontinued);
		model.addAttribute("companyId", company);
		model.addAttribute("computerDTO", new ComputerDTO());
		return "editcomputer";
	}

	@PostMapping("/editcomputer")
	public String doPost(@Valid ComputerDTO computerDTO, BindingResult bindingResult, ModelMap model) {
		ArrayList<Company> listeCompanies;
		listeCompanies = companyService.getCompanies();
		model.addAttribute("listeCompanies", listeCompanies);

		if (bindingResult.hasErrors()) {
			LOGGER.info("erreur dans le formulaire");
			return "editcomputer";
		} else {
			LOGGER.info("pas d'erreur dans le formulaire");
			LOGGER.info(Integer.toString(computerDTO.getCompanyId()));

			computerService.editComputer(Integer.valueOf(computerDTO.getId()), computerDTO.getName(),
					computerDTO.getDateIntroduced(), computerDTO.getDateDiscontinued(),
					Integer.valueOf(computerDTO.getCompanyId()));
			return "redirect:/dashboard";
		}

	}

}
