package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class TestComputerMapper {

	@Test(expected=NullPointerException.class)
	public void testMappToComputerNull() {
		ComputerMapper cm = ComputerMapper.getInstance();
		cm.mappToComputerDTO(null);
	}


}
