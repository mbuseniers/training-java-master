package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import interfaceProjet.Main;
import jdbc.ConnectionMySQL;
import mappers.CompanyMapper;
import model.Company;

public class DAOCompany {

	
	Connection conn;
	static DAOCompany DA = new DAOCompany();
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	private String sqlGetCompanies = "SELECT * FROM company";
	

	private DAOCompany() {
	}
	
	public static DAOCompany getInstance()
	{
		return DA;
	}
	
	public ArrayList<Company> getCompanies()
	{
	    LOGGER.info("GetCompanies DAO");
		conn = ConnectionMySQL.getConnection();
		CompanyMapper cm = CompanyMapper.getInstance();
		ResultSet rs=null;
		ArrayList<Company> listCompanies = new ArrayList<>();
		
		PreparedStatement preparedStatement=null;
		try {
			preparedStatement = conn.prepareStatement(sqlGetCompanies);
			rs = preparedStatement.executeQuery();
	    
			while(rs.next()) {
				listCompanies.add(cm.mappToCompany(rs.getInt(1), rs.getString(2)));
				
			}
			
		    rs.close();
		    preparedStatement.close();
			
		    return listCompanies;
	
		} catch (SQLException e) {
		    LOGGER.info("GetCompanies DAO Exception");
			return null;
		}finally {
			try {
				rs.close();
				preparedStatement.close();
				conn.close();
			} catch (SQLException e) {
			    LOGGER.info("SQL exception fermetures connection getCompanies");
			}
			
		}
	}
}
