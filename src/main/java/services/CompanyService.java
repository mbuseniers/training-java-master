package services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import dao.DAOCompany;
import model.Company;

@Service("companyService")
@Scope("singleton")
public class CompanyService {

	@Resource(name="daoCompany")
	private DAOCompany daoCompany;

	public CompanyService(DAOCompany daoCompany) {
		super();
		this.daoCompany = daoCompany;
	}
	

	public ArrayList<Company> getCompanies() {
		return daoCompany.getCompanies();
	}

	public boolean checkIdCompany(int id) {
		return daoCompany.checkIdCompany(id);
	}

	public boolean deleteCompanyById(int id) {
		return daoCompany.deleteCompanyById(id);
	}

}
