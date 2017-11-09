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

import exceptions.DAOException;
import jdbc.ConnectionMySQL;
import mappers.ComputerMapper;
import model.Computer;

public class DAOComputer {

	Connection conn;
	static DAOComputer DA = new DAOComputer();
	private static final Logger LOGGER = LoggerFactory.getLogger(DAOComputer.class);

	private final String sqlGetComputersLimitOffset = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?";
	private final String sqlGetComputersByName = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE '%' ? '%'";
	private final String sqlGetComputersByCompanyName = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE '%' ? '%'";
	private final String sqlGetNumberComputers = "SELECT COUNT(*) FROM computer";
	private final String sqlInsertComputer = "INSERT INTO computer" + "(name, introduced, discontinued, company_id) VALUES(?,?,?,?)";
	private final String sqlUpdateComputer = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? ,  company_id = ? WHERE id = ?";
	private final String sqlDeleteComputer = "DELETE FROM computer WHERE id = ?";
	private final String sqlDeleteAllComputerByCompanyId = "DELETE FROM computer WHERE company_id = ? ";
	
	private DAOComputer() {
	}

	public static DAOComputer getInstance() {
		return DA;
	}

	public int getNumberComputers() throws DAOException {
		LOGGER.info("GetNumberComputer DAO");
		try (Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement preparedStatement=conn.prepareStatement(sqlGetNumberComputers);
			ResultSet rs=preparedStatement.executeQuery()){

			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			LOGGER.error("SQL exception get number computers");
			throw new DAOException("SQL exception get number computers");
		}
	}

	public ArrayList<Computer> getComputersByLimitAndOffset(int limit, int offset) throws DAOException {
		LOGGER.info("GetComputer Limite Offset DAO");
		ComputerMapper ca = ComputerMapper.getInstance();
		ArrayList<Computer> listComputers = new ArrayList<>();
		
		try(Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement preparedStatement=doPreparedStatement(conn, sqlGetComputersLimitOffset, limit, offset);
			ResultSet rs=preparedStatement.executeQuery()){

			while (rs.next()) {
				LocalDate dateInc = checkDateIsNull(rs.getDate(3));
				LocalDate dateDis = checkDateIsNull(rs.getDate(4));

				listComputers.add(ca.mappToComputer(rs.getInt(1), rs.getString(2), dateInc, dateDis, rs.getInt(5),
						rs.getString(7)));
			}
		} catch (SQLException e) {
			LOGGER.error("sqlexception dao get computers by limit offset");
			throw new DAOException("sqlexception dao get computers by limit offset");

		}
		return listComputers;
	}

	public ArrayList<Computer> getComputersByName(String name) throws DAOException {
		LOGGER.info("GetComputer get by name DAO");
		ArrayList<Computer> listComputers = new ArrayList<>();
		ComputerMapper ca = ComputerMapper.getInstance();

		try(Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement preparedStatement=doPreparedStatement(conn, sqlGetComputersByName,name);
			ResultSet rs=preparedStatement.executeQuery()){

			while (rs.next()) {
				LocalDate date_inc = checkDateIsNull(rs.getDate(3));
				LocalDate date_dis = checkDateIsNull(rs.getDate(4));

				listComputers.add(ca.mappToComputer(rs.getInt(1), rs.getString(2), date_inc, date_dis, rs.getInt(5),
						rs.getString(7)));
			}
		} catch (SQLException e) {
			LOGGER.error("sqlexception dao get computers by name");
			throw new DAOException("sqlexception dao get computers by name");

		} 
		return listComputers;
	}

	public int addComputer(Computer computer) throws DAOException {
		LOGGER.info("AddComputer DAO");

		try (Connection conn = ConnectionMySQL.getConnection();
				PreparedStatement preparedStatement=doPreparedStatement(conn, sqlInsertComputer,computer.getName(),
						checkLocalDateIsNull(computer.getDateIntroduced()),
						checkLocalDateIsNull(computer.getDateDiscontinued()),
								computer.getCompany().getId())){
			
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("SQLException addComputer Dao");
			throw new DAOException("SQLException addComputer Dao");

		}
	}

	public int updateComputer(int id, Computer computer) throws DAOException {
		LOGGER.info("UpdateComputer DAO");
		try (Connection conn = ConnectionMySQL.getConnection();
				PreparedStatement preparedStatement=doPreparedStatement(conn, sqlUpdateComputer,computer.getName(),
						checkLocalDateIsNull(computer.getDateIntroduced()),
						checkLocalDateIsNull(computer.getDateDiscontinued()),
								computer.getCompany().getId(),id)){

			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("SQLException updateComputer Dao");
			throw new DAOException("SQLException updateComputer Dao");
		}
	}

	public boolean deleteComputer(int id) throws DAOException {
		LOGGER.info("DeleteComputer DAO id -> " + id);
		try(Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement preparedStatement=doPreparedStatement(conn, sqlDeleteComputer, id)) {
			
			int result = preparedStatement.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
			LOGGER.error("SQLException deleteComputer Dao");
			throw new DAOException("SQLException updateComputer Dao");
		}
	}

	public ArrayList<Computer> getComputersByCompanyName(String name) throws DAOException {
		LOGGER.info("GetComputer get by company name DAO");
		ArrayList<Computer> listComputers = new ArrayList<>();
		ComputerMapper ca = ComputerMapper.getInstance();

		try(Connection conn = ConnectionMySQL.getConnection();
			PreparedStatement preparedStatement=doPreparedStatement(conn, sqlGetComputersByCompanyName,name);
			ResultSet rs=preparedStatement.executeQuery()){

			while (rs.next()) {
				LocalDate date_inc = checkDateIsNull(rs.getDate(3));
				LocalDate date_dis = checkDateIsNull(rs.getDate(4));

				listComputers.add(ca.mappToComputer(rs.getInt(1), rs.getString(2), date_inc, date_dis, rs.getInt(5),
						rs.getString(7)));
			}
		} catch (SQLException e) {
			LOGGER.error("sqlexception dao get computers by name");
			throw new DAOException("sqlexception dao get computers by name");
		} 
		return listComputers;
	}
	
	public boolean deleteAllComputersByCompanyId(int companyId, Connection c) throws DAOException {
		LOGGER.info("Delete all computers by id company DAO");
		try (PreparedStatement preparedStatement=doPreparedStatement(conn, sqlDeleteAllComputerByCompanyId,companyId)){
			int result = preparedStatement.executeUpdate();
			return result >= 0;
		} catch (SQLException e) {
			LOGGER.error("sqlexception dao get computers by company name");
			throw new DAOException("sqlexception dao get computers by company name");
		}
	}
	
	public LocalDate checkDateIsNull(Date date) {
		if (date == null)
			return null;
		else
			return date.toLocalDate();
	}
	
	public Date checkLocalDateIsNull(LocalDate localDate) {
		if (localDate == null) {
			return null;
		} else {
			return Date.valueOf(localDate);

		}
	}
	
	public static PreparedStatement doPreparedStatement(Connection conn,String sql,  int param1) throws SQLException {
		PreparedStatement ps= conn.prepareStatement(sql);
		ps.setInt(1, param1);
		return ps;
	}
	
	public static PreparedStatement doPreparedStatement(Connection conn,String sql,  int param1, int param2) throws SQLException {
		PreparedStatement ps= conn.prepareStatement(sql);
		ps.setInt(1, param1);
		ps.setInt(2, param2);
		return ps;
	}
	
	public static PreparedStatement doPreparedStatement(Connection conn,String sql, String param1) throws SQLException {
		PreparedStatement ps= conn.prepareStatement(sql);
		ps.setString(1, param1);
		return ps;
	}
	
	public static PreparedStatement doPreparedStatement(Connection conn,String sql, String param1, Date param2, Date param3, int param4) throws SQLException {
		PreparedStatement ps= conn.prepareStatement(sql);
		ps.setString(1, param1);
		ps.setDate(2, param2);
		ps.setDate(3, param3);
		ps.setInt(4, param4);
		return ps;
	}
	
	public static PreparedStatement doPreparedStatement(Connection conn,String sql, String param1, Date param2, Date param3, int param4, int param5) throws SQLException {
		PreparedStatement ps= conn.prepareStatement(sql);
		ps.setString(1, param1);
		ps.setDate(2, param2);
		ps.setDate(3, param3);
		ps.setInt(4, param4);
		ps.setInt(5, param5);
		return ps;
	}

}
