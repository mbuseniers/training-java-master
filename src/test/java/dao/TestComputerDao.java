package dao;


import org.junit.Test;
import exceptions.DAOException;

public class TestComputerDao {

    @Test(expected=NullPointerException.class)
	public void testInsertComputerNull() throws DAOException {
		//DAOComputer daoC = DAOComputer.getInstance();
		//daoC.addComputer(null);
	}

    @Test(expected=NullPointerException.class)
	public void testUpdateComputerNull() throws DAOException {
		//DAOComputer daoC = DAOComputer.getInstance();
		//daoC.updateComputer(1,null);
	}
    
}
