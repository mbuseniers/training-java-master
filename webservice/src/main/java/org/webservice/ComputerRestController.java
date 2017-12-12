package org.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.core.model.Computer;
import org.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComputerRestController {

	@Autowired
	private ComputerService computerService;

	@GetMapping("/computers")
	public List<Computer> getComputers() {
		return computerService.getComputers();
	}

	
	@GetMapping("/computers/{id}")
	public ResponseEntity<Computer> getComputerById(@PathVariable("id") int id) {

		Optional<Computer> computer = computerService.getComputersById(id);
		if (computer.isPresent()) {
			return new ResponseEntity<Computer>(computer.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Computer>(HttpStatus.NOT_FOUND);

	}

	@PostMapping(value = "/addComputer")
	public ResponseEntity<Computer> createComputer(@RequestBody Computer computer) {

		computer = computerService.addComputer(computer);
		if (computer == null) {
			return ResponseEntity.unprocessableEntity().build();
		}
		return new ResponseEntity<Computer>(computer, HttpStatus.OK);
	}

	@DeleteMapping("/deleteComputer/{id}")
	public ResponseEntity<Computer> deleteComputer(@PathVariable int id) {

		if (computerService.getComputersById(id).isPresent()) {
			computerService.deleteComputerById(id);
			return new ResponseEntity<Computer>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Computer>(HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping("/editComputer/{id}")
	public ResponseEntity<Computer> editComputer(@PathVariable int id, @RequestBody Computer computer) {
		
		if (computerService.getComputersById(id).isPresent()) {
			computer.setId(id);
			Computer EditedComputer = computerService.editComputer(computer);
			return new ResponseEntity<Computer>(EditedComputer, HttpStatus.OK);
		} else {
			return new ResponseEntity<Computer>(HttpStatus.NOT_FOUND);

		}
	}

}
