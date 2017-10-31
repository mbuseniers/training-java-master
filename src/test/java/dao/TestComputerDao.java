package dao;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class TestComputerDao {

    @Test(expected=NullPointerException.class)
	public void testInsertComputerNull() {
		DAOComputer daoC = DAOComputer.getInstance();
		daoC.addComputer(null);
	}

    @Test(expected=NullPointerException.class)
	public void testUpdateComputerNull() {
		DAOComputer daoC = DAOComputer.getInstance();
		daoC.updateComputer(1,null);
	}
    
}
