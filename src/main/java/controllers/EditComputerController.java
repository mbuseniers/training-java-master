package controllers;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServlet;

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
@RequestMapping("/editcomputer")
public class EditComputerController extends HttpServlet {

	@Autowired
	private ComputerService computerService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ValidatorService validatorService;

	public void init() {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		context.getAutowireCapableBeanFactory().autowireBean(this);
	}

	@GetMapping
	public ModelAndView doGet(@RequestParam Map<String, String> parameters) {
		ModelAndView modelAndView = new ModelAndView("/views/editComputer.jsp");
		ArrayList<Company> listeCompanies;

		listeCompanies = companyService.getCompanies();
		modelAndView.addObject("listeCompanies", listeCompanies);

		String id = parameters.get("id");
		String name = parameters.get("name");
		String introduced = parameters.get("introduced");
		String discontinued = parameters.get("discontinued");
		String companyId = parameters.get("company");

		if (introduced == null) {
			introduced = "";
		}

		if (discontinued == null) {
			discontinued = "";
		}

		modelAndView.addObject("id", id);
		modelAndView.addObject("name", name);
		modelAndView.addObject("introduced", introduced);
		modelAndView.addObject("discontinued", discontinued);
		modelAndView.addObject("companyId", companyId);
		return modelAndView;
	}

	@PostMapping
	public ModelAndView doPost(@RequestParam Map<String, String> parameters) {
		ModelAndView modelAndView = new ModelAndView("/views/editComputer.jsp");
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
		} else {
			modelAndView.addObject("name", parameters.get("computerName"));
		}

		if (!isCompanyOk) {
			modelAndView.addObject("messageErrorCompany", "La company n'a pas été trouvée");
		} else {
			modelAndView.addObject("companyId", Integer.valueOf(parameters.get("companyId")));
		}

		if (!isIntroducedOk) {
			modelAndView.addObject("messageErrorIntroduced", "Erreur de syntax");
		} else {
			modelAndView.addObject("introduced", parameters.get("introduced"));
		}

		if (!isDiscontinuedOk) {
			modelAndView.addObject("messageErrorDiscontinued", "Erreur de syntax");
		} else {
			modelAndView.addObject("introduced", parameters.get("discontinued"));
		}

		if (isNameOk && isIntroducedOk && isDiscontinuedOk && isCompanyOk) {

			boolean isEditOk = false;
			isEditOk = computerService.editComputer(Integer.valueOf(parameters.get("computerId")),
					parameters.get("computerName"), parameters.get("introduced"),
					parameters.get("discontinued"), Integer.valueOf(parameters.get("companyId")));

			if (isEditOk) {
				modelAndView.addObject("messageEdit", "Edit Ok");
			} else {
				modelAndView.addObject("messageEdit", "Probleme lors de l'edit");
			}

		}

		modelAndView.addObject("id", Integer.valueOf(parameters.get("companyId")));
		return modelAndView;
	}

}
