package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.DAOException;
import interfaceProjet.Main;
import jdbc.ConnectionMySQL;
import mappers.CompanyMapper;
import model.Company;

public class DAOCompany {

	Connection conn;
	static DAOCompany DA = new DAOCompany();
	private static final Logger LOGGER = LoggerFactory.getLogger(DAOCompany.class);

	private String sqlGetCompanies = "SELECT * FROM company";
	private String sqlCheckIdCompany = "SELECT * FROM company WHERE ? = company.id";
	private String sqlDeleteCompanyById = "DELETE FROM company WHERE id = ? ";

	private DAOCompany() {
	}

	public static DAOCompany getInstance() {
		return DA;
	}

	public ArrayList<Company> getCompanies() {
		LOGGER.info("GetCompanies DAO");
		CompanyMapper cm = CompanyMapper.getInstance();
		ArrayList<Company> listCompanies = new ArrayList<>();

		try (Connection conn = ConnectionMySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlGetCompanies);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				listCompanies.add(cm.mappToCompany(rs.getInt(1), rs.getString(2)));

			}
		} catch (SQLException e) {
			LOGGER.error("GetCompanies DAO Exception");
			return null;
		}
		return listCompanies;
	}

	public boolean checkIdCompany(int id) {
		LOGGER.info("check Id company DAO");
		try (Connection conn = ConnectionMySQL.getConnection();
				PreparedStatement ps = DAOComputer.doPreparedStatement(conn, sqlCheckIdCompany, id);
				ResultSet rs = ps.executeQuery()) {

			rs.next();
			try {
				rs.getInt(1);
				return true;
			} catch (NullPointerException e) {
				return false;
			}
		} catch (SQLException e) {
			LOGGER.error("Check ID Company DAO Exception");
			return false;
		}
	}

	public boolean deleteCompanyById(int id){
		LOGGER.info("delete company DAO");
		DAOComputer daoComputer = DAOComputer.getInstance();

		try (Connection conn = ConnectionMySQL.getConnection()) {
			conn.setAutoCommit(false);
			boolean deleteComputersOk;
			try {
				deleteComputersOk = daoComputer.deleteAllComputersByCompanyId(id, conn);
			} catch (DAOException e1) {
				e1.printStackTrace();
				return false;
			}
			
			if (deleteComputersOk) {

				try (PreparedStatement ps = DAOComputer.doPreparedStatement(conn, sqlDeleteCompanyById, id)){
					int result = ps.executeUpdate();
					conn.setAutoCommit(true);
					return result == 1;
				} catch (SQLException e) {
					LOGGER.error("Check ID Company DAO Exception");
					conn.rollback();
					return false;
				} 
			} else {
				conn.rollback();
				return false;
			}

		}catch (SQLException e) {
			LOGGER.error("sql exception deletecomputers");
			return false;
		}

	}
}
