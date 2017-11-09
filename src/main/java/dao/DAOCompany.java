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
	private String sqlCheckIdCompany = "SELECT * FROM company WHERE ? = company.id";
	private String sqlDeleteCompanyById = "DELETE FROM company WHERE id = ? ";

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
	
	public boolean checkIdCompany(int id) {
		LOGGER.info("check Id company DAO");
		conn = ConnectionMySQL.getConnection();
		ResultSet rs=null;
		
		PreparedStatement preparedStatement=null;
		try {
			preparedStatement = conn.prepareStatement(sqlCheckIdCompany);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			rs.next();
			try {
				rs.getInt(1);
				return true;
			}catch(NullPointerException e) {
				return false;
			}
				
		} catch (SQLException e) {
		    LOGGER.info("Check ID Company DAO Exception");
			return false;
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
	
	public boolean deleteCompanyById(int id) {
		LOGGER.info("delete company DAO");
		conn = ConnectionMySQL.getConnection();
		DAOComputer daoComputer = DAOComputer.getInstance();

		
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			LOGGER.info("sql exception set auto commit");
		}
		
		boolean deleteComputersOk = daoComputer.deleteAllComputersByCompanyId(id, conn);
		
		if(deleteComputersOk) {
			
			PreparedStatement preparedStatement=null;
			try {
				preparedStatement = conn.prepareStatement(sqlDeleteCompanyById);
				preparedStatement.setInt(1, id);
				int result =  preparedStatement.executeUpdate();
				conn.setAutoCommit(true);
				return result==1;

					
			} catch (SQLException e) {
			    LOGGER.info("Check ID Company DAO Exception");
			    try {
					conn.rollback();
				} catch (SQLException e1) {
					LOGGER.info("sql exception rollback delete company");
				}
				return false;
			}finally {
				try {
					preparedStatement.close();
					conn.close();
				} catch (SQLException e) {
				    LOGGER.info("SQL exception fermetures connection getCompanies");
				}
				
			}
			
			
			
		}else {
			try {
				conn.rollback();
			} catch (SQLException e) {
				LOGGER.info("sql exception rollback deletecomputers");

			}
			return false;
		}
	
	}
	
	
}
