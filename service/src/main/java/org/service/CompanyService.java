package org.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.core.model.Company;
import org.persistence.CompanyRepository;
import org.persistence.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

@Service("companyService")
@EnableJpaRepositories(basePackages = "org.persistence")
@Scope("singleton")
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ComputerRepository computerRepository;
	
	public ArrayList<Company> getCompanies() {
		return (ArrayList<Company>) companyRepository.findAll();
	}

	public boolean checkIdCompany(long id) {
		return companyRepository.findById(id).isPresent();
	}

	 @Transactional(rollbackOn=Exception.class)
	 public boolean deleteCompanyById(long id) {
		computerRepository.deleteComputersByCompanyId(id);
		companyRepository.deleteById(id);
		return true;		
	}

}
