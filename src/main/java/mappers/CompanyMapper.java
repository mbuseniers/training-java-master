package mappers;

import model.Company;

public class CompanyMapper {

	private static CompanyMapper cm ;
	
	private CompanyMapper() {
		
	}
	
	public static CompanyMapper getInstance() {
		if (cm == null) { 	
			cm = new CompanyMapper();	
		}
		return cm;
	} 
	
	public Company mappToCompany(int id, String name) {
		return new Company(id,name);
	}

}
