package org.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.core.dto.ComputerDTO;
import org.core.model.Company;
import org.core.model.Computer;
import org.mappers.ComputerMapper;
import org.service.CompanyService;
import org.service.ComputerService;
import org.service.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Page page;

	@Autowired
	private ComputerService computerService;

	@Autowired
	private ComputerMapper computerMapper;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ComputerValidator computerValidator;

	@Autowired
	private MessageSource messageSource;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	
	@ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {
        return "404";
    }
	
	@RequestMapping(value = "/404", method = RequestMethod.GET)
    public String viewEdit() {
        return "404";
    }
	
	@GetMapping("/dashboard")
	protected String getDashboard(@RequestParam Map<String, String> parameters, ModelMap model) {
		LOGGER.info("doGet servlet dashboard");
		model.addAttribute("messageAction", parameters.get("messageAction"));
		return page.doPagination(model, parameters);
	}

	@PostMapping("/dashboard")
	public String searchComputers(@RequestParam("searchBy") String searchBy, @RequestParam("search") String search,
			ModelMap model) {
		LOGGER.info("doPost servlet dashboard");
		LOGGER.info(searchBy);
		LOGGER.info("Resultat ? " +searchBy.equals("&#61705;"));
		LOGGER.info("Resultat ? " +searchBy.equals("&#61874;"));
		LOGGER.info("Resultat ? " +searchBy.equals(" &#61705;"));
		LOGGER.info("Resultat ? " +searchBy.equals("&#61705; "));

		ArrayList<ComputerDTO> listComputersDTO;
		ArrayList<Computer> listComputers = null;
		int page = 0, size, intervalMin, intervalMax;
		int nombreComputers;
		if (!search.equals("")) {

			if (searchBy.equals("&#61705;")) {
				LOGGER.info("SEARCH by name");
				listComputers = computerService.getComputersByName(search);
			} else if (searchBy.equals("&#61874;")) {
				LOGGER.info("SEARCH by company");
				listComputers = computerService.getComputersByCompanyName(search);
			}else {

				model.addAttribute("messageAction","message.searchError");
				return "redirect:/dashboard";
			}
			
			
			listComputersDTO = computerMapper.ComputersToComputersDTO(listComputers);

			size = 100;
			intervalMin = 0;
			intervalMax = 0;
			nombreComputers = listComputersDTO.size();
		} else {

			model.addAttribute("messageAction","message.searchKO");
			return "redirect:/dashboard";
		}
		model.addAttribute("liste", listComputersDTO);
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
		computerService.deleteComputer(selection);
		LOGGER.info("redirect dashboard DELETE OK");
		
		model.addAttribute("messageAction","message.deleteComputerOK");
		return "redirect:/dashboard";

	}

	@GetMapping("/addcomputer")
	public String getPageAddComputer(ModelMap model) {
		Map<Long, String> mapCompanies = companyService.getMapCompanies();
		model.addAttribute("mapCompanies", mapCompanies);
		model.addAttribute("computerDTO", new ComputerDTO());
		return "addcomputer";
	}

	@PostMapping("/addcomputer")
	public String sendFormAddComputer(@Valid ComputerDTO computerDTO, BindingResult bindingResult, ModelMap model) {
		Map<Long, String> mapCompanies = companyService.getMapCompanies();
		model.addAttribute("mapCompanies", mapCompanies);
		computerValidator.validate(computerDTO, bindingResult);

		if (bindingResult.hasErrors()) {
			LOGGER.info("erreur dans le formulaire");
			return "addcomputer";
		} else {
			LOGGER.info("pas d'erreur dans le formulaire");
			computerService.addComputer(computerDTO.getName(), computerDTO.getDateIntroduced(),
					computerDTO.getDateDiscontinued(), computerDTO.getCompanyId());
			LOGGER.info("redirect dashboard ADD OK");

			model.addAttribute("messageAction","message.addComputerOK");
			return "redirect:dashboard";
		}

	}

	@GetMapping("/editcomputer")
	public String getPageEditComputer(ModelMap model, @RequestParam(value = "id", required = false) String id) {

		Map<Long, String> mapCompanies = companyService.getMapCompanies();
		model.addAttribute("mapCompanies", mapCompanies);

		Optional<Computer> optionalComputer;

		try {
			optionalComputer = computerService.getComputersById(Integer.parseInt(id));
		}catch(NumberFormatException e) {	
			LOGGER.info("redirect dashboard FORMAT EXCEPTION");
			model.addAttribute("messageAction","message.editComputerKO");
			return "redirect:/dashboard";
		}
		
		if (optionalComputer.isPresent()) {
			Computer computer = optionalComputer.get();
			ComputerDTO computerDto = computerMapper.ComputerToComputerDTO(computer);
			model.addAttribute("computerDTO", computerDto);
			return "editcomputer";
		}else {
			LOGGER.info("redirect dashboard COMPUTER NOT FOUND");
			model.addAttribute("messageAction","message.editComputerKO");
			return "redirect:/dashboard";
		}
	}

	@PostMapping("/editcomputer")
	public String doPost(@Valid ComputerDTO computerDTO, BindingResult bindingResult, ModelMap model) {
		Map<Long, String> mapCompanies = companyService.getMapCompanies();
		model.addAttribute("mapCompanies", mapCompanies);
		computerValidator.validate(computerDTO, bindingResult);

		if (bindingResult.hasErrors()) {
			LOGGER.info("erreur dans le formulaire");
			return "editcomputer";
		} else {
			LOGGER.info("pas d'erreur dans le formulaire");
			computerService.editComputer(computerDTO.getId(), computerDTO.getName(),
					computerDTO.getDateIntroduced(), computerDTO.getDateDiscontinued(),
					computerDTO.getCompanyId());
			LOGGER.info("redirect dashboard EDIT OK");
			model.addAttribute("messageAction","message.editComputerOK");
			return "redirect:/dashboard";
		}
	}

  @GetMapping("/deleteCompany")
	protected ModelAndView getDelete(){
		ModelAndView modelAndView = new ModelAndView("/deleteCompany");		
		List<Company> listCompanies= companyService.getCompanies();
		modelAndView.addObject( "listcompanies", listCompanies);

		return modelAndView;
	}

	
	@PostMapping("/deleteCompany")
	protected ModelAndView postDelete( @RequestParam Map<String, String> parameters, ModelMap model){
		ModelAndView modelAndView = new ModelAndView("/deleteCompany");
		String confirmation = parameters.get("confirmation");
		if(confirmation==null) {
		    Long companyId = Long.valueOf(parameters.get("companyId"));
			List<ComputerDTO> computersToFind = computerMapper.ComputersToComputersDTO(computerService.getComputersByCompanyId(companyId));
			modelAndView.addObject("listComputer", computersToFind );
			List<Company> listCompanies= companyService.getCompanies();
					
			modelAndView.addObject( "listcompanies", listCompanies);
			modelAndView.addObject("companyId", companyId);
			return modelAndView;
			
		}else if(confirmation.equals("Delete!")) {
			Long companyId = Long.valueOf(parameters.get("companyId"));
			computerService.deleteComputerAndCompany(companyId);
			model.addAttribute("messageAction","message.deleteCompanyOK");
			return new ModelAndView("redirect:/dashboard");
		}
		return modelAndView;
	}	
	

}
