package mappers;

import java.time.LocalDate;

import model.Company;
import model.Computer;

public class ComputerMapper {

	private static ComputerMapper cm ;
	
	private ComputerMapper() {
	}
	
	public static ComputerMapper getInstance() {
		if (cm == null)
		{ 	cm = new ComputerMapper();	
		}
		return cm;
	}
	
	
	public Computer mappToComputer(int id, String name, LocalDate dateInc, LocalDate dateDis, int id_company, String company_name) {
		
		return new Computer(id,name,dateInc,dateDis,new Company(id_company, company_name));
	}
}
