package services;

import java.util.ArrayList;

import dao.DAOCompany;
import model.Company;

public class CompanyService {

	private static CompanyService cs;
	DAOCompany da;

	private CompanyService() {
		da = DAOCompany.getInstance();
	}

	public static CompanyService getInstance() {
		if (cs == null) {
			cs = new CompanyService();
		}
		return cs;
	}

	public ArrayList<Company> getCompanies() {
		return da.getCompanies();
	}

}
