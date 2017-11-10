package mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import dto.ComputerDTO;

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
	
	
	public ComputerDTO mappToComputerDTO(int id, String name, String dateInc, String dateDis, int id_company, String company_name) {
		
		return new ComputerDTO(id,name,dateInc,dateDis,id_company, company_name);
	}
	
	public ArrayList<ComputerDTO> mappToComputerDTO(ResultSet rs){
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();
		
		try {
			while (rs.next()) {
				listComputers.add(new ComputerDTO(rs.getInt(1), rs.getString(2), convertDateToString(rs.getDate(3)), convertDateToString(rs.getDate(4)), rs.getInt(5),rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listComputers;
	}
	
	public String convertDateToString(Date date) {
		if (date == null) {
			return "";
		} else {
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(date);
		}
	}
}
