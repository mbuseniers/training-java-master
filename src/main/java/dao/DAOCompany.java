package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import exceptions.DAOException;
import jdbc.ConnectionMySQL;
import mappers.CompanyMapper;
import model.Company;

@Repository("daoCompany")
@Scope("singleton")
public class DAOCompany {

	@Resource(name = "daoComputer")
	private DAOComputer daoComputer;
	
	private CompanyMapper cm;

	private static final Logger LOGGER = LoggerFactory.getLogger(DAOCompany.class);

	private String sqlGetCompanies = "SELECT * FROM company";
	private String sqlCheckIdCompany = "SELECT * FROM company WHERE ? = company.id";
	private String sqlDeleteCompanyById = "DELETE FROM company WHERE id = ? ";

	public DAOCompany(DAOComputer daoComputer, CompanyMapper cm) {
		this.daoComputer = daoComputer;
		this.cm = cm;
	}

	public ArrayList<Company> getCompanies() {
		LOGGER.info("GetCompanies DAO");
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

	public boolean deleteCompanyById(int id) {
		LOGGER.info("delete company DAO");

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

				try (PreparedStatement ps = DAOComputer.doPreparedStatement(conn, sqlDeleteCompanyById, id)) {
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

		} catch (SQLException e) {
			LOGGER.error("sql exception deletecomputers");
			return false;
		}

	}
}
