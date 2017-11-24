package org.service;

import java.util.ArrayList;
import java.util.Map;

import org.core.dto.ComputerDTO;
import org.core.mappers.ComputerMapper;
import org.core.model.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;



@Component
public class Page {

	@Autowired
	private ComputerService computerService;

	@Autowired
	private ComputerMapper computerMapper;

	public void doPagination(ModelMap model, Map<String, String> parameters) {
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

		ArrayList<Computer> listeComputers = computerService.getComputersByLimitAndOffset(nombreComputersByPage,
				numeroPage * nombreComputersByPage);

		ArrayList<ComputerDTO> listeComputersDTO = computerMapper.ComputersToComputersDTO(listeComputers);

		model.addAttribute("liste", listeComputersDTO);
		model.addAttribute("page", numeroPage);
		model.addAttribute("size", nombreComputersByPage);
		model.addAttribute("intervalMin", intervalPageMin);
		model.addAttribute("intervalMax", intervalPageMax);
		model.addAttribute("nombreComputers", nombreComputers);
	}

}
