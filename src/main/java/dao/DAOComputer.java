package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Statement;

import interfaceProjet.Main;
import jdbc.ConnectionMySQL;
import mappers.ComputerMapper;
import model.Computer;
import utils.Page;

public class DAOComputer {
	
	Connection conn;
	static DAOComputer DA = new DAOComputer();
	private static final Logger LOGGER = LoggerFactory.getLogger(DAOComputer.class);

	
	private String sqlGetComputers = "SELECT * FROM computer";
	private String sqlInsertComputer = "INSERT INTO computer"
			+ "(name, introduced, discontinued, company_id) VALUES"
			+ "(?,?,?,?)";
	
	private String sqlUpdateComputer = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? ,  company_id = ? "
            + " WHERE id = ?";
	
	private String sqlDeleteComputer = "DELETE FROM computer WHERE id = ?";
	
	
	private DAOComputer() {
		conn = ConnectionMySQL.getInstance();
	}
	
	public static DAOComputer getInstance()
	{
		return DA;
	}
	
	

	public ArrayList<Computer> getComputers() throws SQLException 
	{
		
	    LOGGER.info("GetComputer DAO");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ComputerMapper ca = ComputerMapper.getInstance();
		
		try {
			preparedStatement = conn.prepareStatement(sqlGetComputers);
		    rs = preparedStatement.executeQuery();
	    
		    ArrayList<Computer> listeComputers = new ArrayList<>();
		    
		    while(rs.next())
			{
			    	LocalDate date_inc;
			    	LocalDate date_dis;
			    	
			    	if(rs.getDate(3) == null) date_inc = null;
			    	else date_inc = rs.getDate(3).toLocalDate();
			    	if(rs.getDate(4) == null) date_dis = null;
			    	else date_dis = rs.getDate(4).toLocalDate();

			    	
		    		listeComputers.add( ca.mappToComputer(rs.getInt(1),
		    											  rs.getString(2),
		    											  date_inc,
		    											  date_dis,
		    											  rs.getInt(5)) );
					
		    		/*int id = rs.getInt(1); 
			    	String nom = rs.getString(2); 
			    	Date date_inc = rs.getDate(3);
			    	Date date_dis = rs.getDate(4);
			    	int id_company = rs.getInt(5);
					*/		
			}
		    
		    return listeComputers;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			rs.close();
		    preparedStatement.close();
		}
		
		    	
	}
	
	public int addComputer(Computer computer) 
	{	
	    LOGGER.info("AddComputer DAO");
		
		try {
			//dbConnection = getDBConnection();
			PreparedStatement statement = conn.prepareStatement(sqlInsertComputer);

			statement.setString(1, computer.getName());
			statement.setDate(2, Date.valueOf(computer.getDate_introduced()));
			statement.setDate(3, Date.valueOf(computer.getDate_discontinued()));
			statement.setInt(4, computer.getId_company());
			
			// execute insert SQL stetement
			return statement.executeUpdate();


		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return -1;

		}
	}	

	
	public int updateComputer(int id,Computer computer)
	{
	    LOGGER.info("UpdateComputer DAO");

		try {
		
			PreparedStatement statement = conn.prepareStatement(sqlUpdateComputer);
	
			statement.setString(1, computer.getName());
			statement.setDate(2, Date.valueOf(computer.getDate_introduced()));
			statement.setDate(3, Date.valueOf(computer.getDate_discontinued()));
			statement.setInt(4, computer.getId_company());
			statement.setInt(5, id);

			// execute insert SQL stetement
			return statement.executeUpdate();
		
		}catch (SQLException e) {

			System.out.println(e.getMessage());
			return -1;

		}
	}
	
	public int deleteComputer(int id)
	{
	    LOGGER.info("DeleteComputer DAO");

		
		try {
			PreparedStatement statement = conn.prepareStatement(sqlDeleteComputer);
	
			statement.setInt(1, id);

			// execute insert SQL stetement
			return statement.executeUpdate();
		
		}catch (SQLException e) {

			System.out.println(e.getMessage());
			return -1;

		}
		
		
	}
	
	

}
