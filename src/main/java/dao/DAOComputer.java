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

import jdbc.ConnectionMySQL;
import mappers.ComputerMapper;
import model.Computer;

public class DAOComputer {

	Connection conn;
	static DAOComputer DA = new DAOComputer();
	private static final Logger LOGGER = LoggerFactory.getLogger(DAOComputer.class);

	private String sqlGetComputers = "SELECT * FROM computer";
	private String sqlGetComputersLimitOffset = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?";
	private String sqlGetComputersByName = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE '%' ? '%'";
	private String sqlGetComputersByCompanyName = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE '%' ? '%'";
	private String sqlGetNumberComputers = "SELECT COUNT(*) FROM computer";
	private String sqlInsertComputer = "INSERT INTO computer" + "(name, introduced, discontinued, company_id) VALUES"
			+ "(?,?,?,?)";

	private String sqlUpdateComputer = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? ,  company_id = ? "
			+ " WHERE id = ?";

	private String sqlDeleteComputer = "DELETE FROM computer WHERE id = ?";
	private String sqlDeleteAllComputerByCompanyId = "DELETE FROM computer WHERE company_id = ? ";
	
	private DAOComputer() {
	}

	public static DAOComputer getInstance() {
		return DA;
	}

	public int getNumberComputers() {
		LOGGER.info("GetNumberComputer DAO");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection conn = ConnectionMySQL.getConnection();

		try {
			preparedStatement = conn.prepareStatement(sqlGetNumberComputers);
			rs = preparedStatement.executeQuery();

			rs.next();
			int number = rs.getInt(1);

			return number;

		} catch (SQLException e) {
			LOGGER.info("SQL exception get number computers");
			return 0;
		} finally {

			try {
				preparedStatement.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				LOGGER.info("SQL exception fermetures get number computers");
			}
		}

	}

	public ArrayList<Computer> getComputers() {
		LOGGER.info("GetComputer DAO");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ComputerMapper ca = ComputerMapper.getInstance();
		Connection conn = ConnectionMySQL.getConnection();

		try {
			preparedStatement = conn.prepareStatement(sqlGetComputers);
			rs = preparedStatement.executeQuery();

			ArrayList<Computer> listeComputers = new ArrayList<>();

			while (rs.next()) {
				LocalDate dateInc;
				LocalDate dateDis;

				if (rs.getDate(3) == null)
					dateInc = null;
				else
					dateInc = rs.getDate(3).toLocalDate();
				if (rs.getDate(4) == null)
					dateDis = null;
				else
					dateDis = rs.getDate(4).toLocalDate();

				listeComputers.add(ca.mappToComputer(rs.getInt(1), rs.getString(2), dateInc, dateDis, rs.getInt(5),
						rs.getString(7)));
			}

			return listeComputers;

		} catch (SQLException e) {
			LOGGER.info("sqlexception dao get computers");
			return null;
		} finally {
			try {
				rs.close();
				preparedStatement.close();
				conn.close();
			} catch (SQLException e) {
				LOGGER.info("sqlexception fermetures dao get computers");

			}

		}

	}

	public ArrayList<Computer> getComputersByLimitAndOffset(int limit, int offset) {
		LOGGER.info("GetComputer Limite Offset DAO");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ComputerMapper ca = ComputerMapper.getInstance();
		Connection conn = ConnectionMySQL.getConnection();

		try {
			preparedStatement = conn.prepareStatement(sqlGetComputersLimitOffset);
			preparedStatement.setInt(1, limit);
			preparedStatement.setInt(2, offset);

			rs = preparedStatement.executeQuery();

			ArrayList<Computer> listeComputers = new ArrayList<>();

			while (rs.next()) {
				LocalDate dateInc;
				LocalDate dateDis;

				if (rs.getDate(3) == null)
					dateInc = null;
				else
					dateInc = rs.getDate(3).toLocalDate();
				if (rs.getDate(4) == null)
					dateDis = null;
				else
					dateDis = rs.getDate(4).toLocalDate();

				listeComputers.add(ca.mappToComputer(rs.getInt(1), rs.getString(2), dateInc, dateDis, rs.getInt(5),
						rs.getString(7)));
			}

			return listeComputers;

		} catch (SQLException e) {
			LOGGER.info("sqlexception dao get computers by limit offset");
			return null;
		} finally {
			try {
				rs.close();
				preparedStatement.close();
				conn.close();
			} catch (SQLException e) {
				LOGGER.info("sqlexception fermetures dao get computers by limit offset");

			}

		}
	}

	public ArrayList<Computer> getComputersByName(String name) {
		LOGGER.info("GetComputer get by name DAO");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ComputerMapper ca = ComputerMapper.getInstance();
		Connection conn = ConnectionMySQL.getConnection();

		try {
			preparedStatement = conn.prepareStatement(sqlGetComputersByName);
			preparedStatement.setString(1, name);

			rs = preparedStatement.executeQuery();

			ArrayList<Computer> listeComputers = new ArrayList<>();

			while (rs.next()) {
				LocalDate date_inc;
				LocalDate date_dis;

				if (rs.getDate(3) == null)
					date_inc = null;
				else
					date_inc = rs.getDate(3).toLocalDate();
				if (rs.getDate(4) == null)
					date_dis = null;
				else
					date_dis = rs.getDate(4).toLocalDate();

				listeComputers.add(ca.mappToComputer(rs.getInt(1), rs.getString(2), date_inc, date_dis, rs.getInt(5),
						rs.getString(7)));
			}

			return listeComputers;

		} catch (SQLException e) {
			LOGGER.info("sqlexception dao get computers by name");
			return null;
		} finally {
			try {
				rs.close();
				preparedStatement.close();
				conn.close();
			} catch (SQLException e) {
				LOGGER.info("sqlexception fermetures dao get computers by name");

			}

		}
	}

	public int addComputer(Computer computer) {
		LOGGER.info("AddComputer DAO");
		Connection conn = ConnectionMySQL.getConnection();

		try {
			PreparedStatement statement = conn.prepareStatement(sqlInsertComputer);

			statement.setString(1, computer.getName());

			if (computer.getDate_introduced() == null) {
				statement.setDate(2, null);
			} else {
				statement.setDate(2, Date.valueOf(computer.getDate_introduced()));

			}

			if (computer.getDate_discontinued() == null) {
				statement.setDate(3, null);

			} else {
				statement.setDate(3, Date.valueOf(computer.getDate_discontinued()));
			}
			statement.setInt(4, computer.getCompany().getId());

			return statement.executeUpdate();

		} catch (SQLException e) {

			LOGGER.info("SQLException addComputer Dao");
			return -1;

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.info("sqlexception fermetures dao add computer");

			}
		}
	}

	public int updateComputer(int id, Computer computer) {
		LOGGER.info("UpdateComputer DAO");
		Connection conn = ConnectionMySQL.getConnection();

		try {

			PreparedStatement statement = conn.prepareStatement(sqlUpdateComputer);

			statement.setString(1, computer.getName());

			if (computer.getDate_introduced() == null) {
				statement.setDate(2, null);
			} else {
				statement.setDate(2, Date.valueOf(computer.getDate_introduced()));

			}

			if (computer.getDate_discontinued() == null) {
				statement.setDate(3, null);

			} else {
				statement.setDate(3, Date.valueOf(computer.getDate_discontinued()));
			}

			statement.setInt(4, computer.getCompany().getId());
			statement.setInt(5, id);

			return statement.executeUpdate();

		} catch (SQLException e) {

			LOGGER.info("SQLException updateComputer Dao");
			return -1;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.info("sqlexception dao update computer");

			}
		}
	}

	public boolean deleteComputer(int id) {
		LOGGER.info("DeleteComputer DAO id -> " + id);
		Connection conn = ConnectionMySQL.getConnection();
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sqlDeleteComputer);
			statement.setInt(1, id);

			int result = statement.executeUpdate();
			LOGGER.info("result -> " + result);
			return result == 1;

		} catch (SQLException e) {

			LOGGER.info("SQLException deleteComputer Dao");
			return false;
		} finally {
			try {
				conn.close();
				statement.close();
			} catch (SQLException e) {
				LOGGER.info("sqlexception dao delete computers");

			}
		}

	}

	public ArrayList<Computer> getComputersByCompanyName(String name) {
		LOGGER.info("GetComputer get by company name DAO");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ComputerMapper ca = ComputerMapper.getInstance();
		Connection conn = ConnectionMySQL.getConnection();

		try {
			preparedStatement = conn.prepareStatement(sqlGetComputersByCompanyName);
			preparedStatement.setString(1, name);

			rs = preparedStatement.executeQuery();

			ArrayList<Computer> listeComputers = new ArrayList<>();

			while (rs.next()) {
				LocalDate date_inc;
				LocalDate date_dis;

				if (rs.getDate(3) == null)
					date_inc = null;
				else
					date_inc = rs.getDate(3).toLocalDate();
				if (rs.getDate(4) == null)
					date_dis = null;
				else
					date_dis = rs.getDate(4).toLocalDate();

				listeComputers.add(ca.mappToComputer(rs.getInt(1), rs.getString(2), date_inc, date_dis, rs.getInt(5),
						rs.getString(7)));
			}

			return listeComputers;

		} catch (SQLException e) {
			LOGGER.info("sqlexception dao get computers by company name");
			return null;
		} finally {
			try {
				rs.close();
				preparedStatement.close();
				conn.close();
			} catch (SQLException e) {
				LOGGER.info("sqlexception fermetures dao get computers by name");

			}

		}
	}
	
	public boolean deleteAllComputersByCompanyId(int companyId, Connection c) {
		LOGGER.info("Delete all computers by id company DAO");

		PreparedStatement preparedStatement = null;
		//Connection conn = ConnectionMySQL.getConnection();

		try {
			preparedStatement = c.prepareStatement(sqlDeleteAllComputerByCompanyId);
			preparedStatement.setInt(1, companyId);

			int result = preparedStatement.executeUpdate();

			return result >= 0;


		} catch (SQLException e) {
			LOGGER.info("sqlexception dao get computers by company name");
			return false;
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				LOGGER.info("sqlexception fermetures dao get computers by name");

			}

		}
	}

}
