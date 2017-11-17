package dao;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import exceptions.DAOException;

public class TestComputerDao {

	@Autowired
	DAOComputer daoC;
	
    @Test(expected=NullPointerException.class)
	public void testInsertComputerNull() throws DAOException {
		daoC.addComputer(null);
	}

    @Test(expected=NullPointerException.class)
	public void testUpdateComputerNull() throws DAOException {
		daoC.updateComputer(1,null);
	}
    
}
