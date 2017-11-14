package mappers;

import org.springframework.stereotype.Component;

import model.Company;

@Component
public class CompanyMapper {

	public Company mappToCompany(int id, String name) {
		return new Company(id,name);
	}

}
