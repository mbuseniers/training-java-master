package dao;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import exceptions.DAOException;

public class TestComputerDao {

	@Autowired
	ComputerRepository daoC;
	
    @Test(expected=NullPointerException.class)
	public void testInsertComputerNull() throws DAOException {
		daoC.save(null);
	}

    @Test(expected=NullPointerException.class)
	public void testUpdateComputerNull() throws DAOException {
		daoC.save(null);
	}
    
}
