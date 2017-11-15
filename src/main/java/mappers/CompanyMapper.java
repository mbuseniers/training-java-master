package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import model.Company;

@Component
public class CompanyMapper {

	public Company mappToCompany(int id, String name) {
		return new Company(id,name);
	}

	public Company mappToCompany(ResultSet rs) {
		Company company=null;
		try {
			rs.next();
			company = new Company(rs.getInt(1),rs.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
}
