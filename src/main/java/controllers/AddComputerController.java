package controllers;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import configuration.SpringConfiguration;
import model.Company;
import services.CompanyService;
import services.ComputerService;
import services.ValidatorService;

@Controller
@RequestMapping("/addcomputer")
public class AddComputerController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ComputerService computerService;
	@Autowired
	private ValidatorService validatorService;

	public void init() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		context.getAutowireCapableBeanFactory().autowireBean(this);
	}

	@GetMapping
	public ModelAndView doGet(@RequestParam Map<String, String> parameters) {
		ModelAndView modelAndView = new ModelAndView("/views/addComputer.jsp");
		ArrayList<Company> listeCompanies;
		listeCompanies = companyService.getCompanies();
		modelAndView.addObject("listeCompanies", listeCompanies);
		return modelAndView;
	}
	
	@PostMapping
	public ModelAndView doPost(@RequestParam Map<String, String> parameters) {
		ModelAndView modelAndView = new ModelAndView("/views/addComputer.jsp");
		ArrayList<Company> listeCompanies;
		listeCompanies = companyService.getCompanies();
		modelAndView.addObject("listeCompanies", listeCompanies);

		boolean isNameOk = validatorService.checkName(parameters.get("computerName"));
		boolean isIntroducedOk = validatorService.checkDateFromString(parameters.get("introduced"));
		boolean isDiscontinuedOk = validatorService.checkDateFromString(parameters.get("discontinued"));
		boolean isCompanyOk = validatorService.checkCompany(Integer.valueOf(parameters.get("companyId")),
				listeCompanies.size());

		if (!isNameOk) {
			modelAndView.addObject("messageErrorName", "le Nom ne peut etre vide");
		}

		if (!isCompanyOk) {
			modelAndView.addObject("messageErrorCompany", "La company n'a pas été trouvée");
		}

		if (!isIntroducedOk) {
			modelAndView.addObject("messageErrorIntroduced", "Erreur de syntax");
		}

		if (!isDiscontinuedOk) {
			modelAndView.addObject("messageErrorIntroduced", "Erreur de syntax");
		}

		if (isNameOk && isIntroducedOk && isDiscontinuedOk && isCompanyOk) {
			boolean isAddOk = false;
			isAddOk = computerService.addComputer(parameters.get("computerName"),
					parameters.get("introduced"), parameters.get("discontinued"),
					Integer.valueOf(parameters.get("companyId")));

			if (isAddOk) {
				modelAndView.addObject("messageAjout", "Ajout OK");

			} else {
				modelAndView.addObject("messageAjout", "Problème lors de l'ajout");
			}
		}

		return modelAndView;
		//this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);

	}

}
