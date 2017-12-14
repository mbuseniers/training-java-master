package org.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	public Map<Integer, String> getMapCompanies() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();

		List<Company> listCompanies = companyRepository.findAll();
		
		for (Company company : listCompanies) {
			map.put(company.getId(), company.getName());
		}
		
		return map;
	}

	public boolean checkIdCompany(long id) {
		return companyRepository.findById(id).isPresent();
	}

	 @Transactional(rollbackOn=Exception.class)
	 public boolean deleteCompanyById(long id) throws SQLException {
		computerRepository.deleteComputersByCompanyId(id);
		companyRepository.deleteById(id);
		return true;
	}

}
