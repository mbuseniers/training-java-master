package mappers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import model.Company;

public class TestCompanyMapper {

	@Autowired
	CompanyMapper cm;
	/*
	@Test
	public void testMappToCompany() {
		assertEquals((new Company(1,"Apple").toString()),cm.mappToCompany(1,"Apple").toString() );
	}*/

}
