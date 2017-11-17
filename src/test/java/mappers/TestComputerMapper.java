package mappers;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestComputerMapper {

	@Autowired
	ComputerMapper cm;
	
	@Test(expected=NullPointerException.class)
	public void testMappToComputerNull() {
		cm.mappToComputerDTO(null);
	}


}
