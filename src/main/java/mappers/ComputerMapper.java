package mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import dto.ComputerDTO;

@Component
public class ComputerMapper {
	
	public ArrayList<ComputerDTO> mappToListComputerDTO(ResultSet rs){
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
	
	public ComputerDTO mappToComputerDTO(ResultSet rs) {
		ComputerDTO cpDTO=null;
		try {
			cpDTO = new ComputerDTO(rs.getInt(1), rs.getString(2), convertDateToString(rs.getDate(3)), convertDateToString(rs.getDate(4)), rs.getInt(5),rs.getString(7));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cpDTO;
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
