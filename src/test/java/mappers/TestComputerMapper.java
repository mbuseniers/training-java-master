package mappers;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import model.Company;
import model.Computer;

public class TestComputerMapper {

	@Test
	public void testMappToComputer() {
		ComputerMapper cm = ComputerMapper.getInstance();
		assertEquals( (new Computer(1,"MonPc", LocalDate.now(), LocalDate.now(), 10).toString() ),
				  cm.mappToComputer(1,"MonPc", LocalDate.now(), LocalDate.now(), 10).toString() );
	}

}