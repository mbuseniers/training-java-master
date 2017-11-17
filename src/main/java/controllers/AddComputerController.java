package controllers;

import java.util.ArrayList;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dto.ComputerDTO;
import model.Company;
import services.CompanyService;
import services.ComputerService;
import services.ValidatorService;

@Controller
@EnableWebMvc
@RequestMapping("/addcomputer")
public class AddComputerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerController.class);

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ComputerService computerService;
	@Autowired
	private ValidatorService validatorService;

	@GetMapping
	public ModelAndView doGet(@RequestParam Map<String, String> parameters) {
		ModelAndView modelAndView = new ModelAndView("/views/addComputer.jsp");
		ArrayList<Company> listeCompanies;
		listeCompanies = companyService.getCompanies();
		modelAndView.addObject("listeCompanies", listeCompanies);
		modelAndView.getModelMap().put("computerDTO", new ComputerDTO());
		return modelAndView;
	}
	
	@PostMapping
	public ModelAndView doPost(@RequestParam Map<String, String> parameters,@Valid ComputerDTO computerDTO, BindingResult bindingResult, Model model) {
		ModelAndView modelAndView = new ModelAndView("/views/addComputer.jsp");
		ArrayList<Company> listeCompanies;
		listeCompanies = companyService.getCompanies();
		modelAndView.addObject("listeCompanies", listeCompanies);

		boolean isNameOk = validatorService.checkName(parameters.get("computerName"));
		boolean isIntroducedOk = validatorService.checkDateFromString(parameters.get("introduced"));
		boolean isDiscontinuedOk = validatorService.checkDateFromString(parameters.get("discontinued"));
		boolean isCompanyOk = validatorService.checkCompany(Integer.valueOf(parameters.get("companyId")),listeCompanies.size());

		
		if (bindingResult.hasErrors()) {
			LOGGER.info("erreur dans le formulaire");
            return new ModelAndView("/views/addComputer.jsp");
        }
		LOGGER.info("after is error");

		
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
			isAddOk = computerService.addComputer(parameters.get("name"),
					parameters.get("dateIntroduced"), parameters.get("dateDiscontinued"),
					Integer.valueOf(parameters.get("companyId")));

			if (isAddOk) {
				modelAndView.addObject("messageAjout", "Ajout OK");

			} else {
				modelAndView.addObject("messageAjout", "Problème lors de l'ajout");
			}
		}
		return modelAndView;
	}

}
