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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import dto.ComputerDTO;
import exceptions.DAOException;
import jdbc.ConnectionMySQL;
import mappers.ComputerMapper;
import model.Computer;

@Repository("daoComputer")
@Scope("singleton")
public class DAOComputer {

	private static final Logger LOGGER = LoggerFactory.getLogger(DAOComputer.class);
	
	private ComputerMapper ca;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String sqlGetComputersLimitOffset = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?";
	private final String sqlGetComputersByName = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE '%' ? '%'";
	private final String sqlGetComputersByCompanyName = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company.name LIKE '%' ? '%'";
	private final String sqlGetNumberComputers = "SELECT COUNT(*) FROM computer";
	private final String sqlInsertComputer = "INSERT INTO computer"
			+ "(name, introduced, discontinued, company_id) VALUES(?,?,?,?)";
	private final String sqlUpdateComputer = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? ,  company_id = ? WHERE id = ?";
	private final String sqlDeleteComputer = "DELETE FROM computer WHERE id = ?";
	private final String sqlDeleteAllComputerByCompanyId = "DELETE FROM computer WHERE company_id = ? ";

	public DAOComputer(ComputerMapper ca) {
		this.ca = ca;
	}

	public int getNumberComputers() throws DAOException {
		LOGGER.info("GetNumberComputer DAO");
		return jdbcTemplate.queryForObject(sqlGetNumberComputers, Integer.class);
	}

	public ArrayList<ComputerDTO> getComputersByLimitAndOffset(int limit, int offset) throws DAOException {
		LOGGER.info("GetComputer Limite Offset DAO");
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();
		listComputers = (ArrayList<ComputerDTO>) jdbcTemplate.query(sqlGetComputersLimitOffset,
				new Object[] { limit, offset }, new RowMapper<ComputerDTO>() {
					public ComputerDTO mapRow(ResultSet rs, int arg1) throws SQLException {
						return ca.mappToComputerDTO(rs);
					}
				});
		return listComputers;
	}

	public ArrayList<ComputerDTO> getComputersByName(String name) throws DAOException {
		LOGGER.info("GetComputer get by name DAO");
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();
		listComputers = (ArrayList<ComputerDTO>) jdbcTemplate.query(sqlGetComputersByName,
				new Object[] {name}, new RowMapper<ComputerDTO>() {
					public ComputerDTO mapRow(ResultSet rs, int arg1) throws SQLException {
						return ca.mappToComputerDTO(rs);
					}
				});
		return listComputers;
	}

	public int addComputer(Computer computer) throws DAOException {
		LOGGER.info("AddComputer DAO");
		return jdbcTemplate.update(sqlInsertComputer, computer.getId(), computer.getDateIntroduced(), computer.getDateDiscontinued(), computer.getCompany().getId());
	}

	public int updateComputer(int id, Computer computer) throws DAOException {
		LOGGER.info("UpdateComputer DAO");
		return jdbcTemplate.update(sqlUpdateComputer, computer.getName(),checkLocalDateIsNull(computer.getDateIntroduced()),
				checkLocalDateIsNull(computer.getDateDiscontinued()), computer.getCompany().getId(), id);
	}

	public boolean deleteComputer(int id) throws DAOException {
		LOGGER.info("DeleteComputer DAO id -> " + id);
		return jdbcTemplate.update(sqlDeleteComputer, id) == 1;
	}

	public ArrayList<ComputerDTO> getComputersByCompanyName(String name) throws DAOException {
		LOGGER.info("GetComputer get by company name DAO");
		
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();
		listComputers = (ArrayList<ComputerDTO>) jdbcTemplate.query(sqlGetComputersByCompanyName,
				new Object[] {name}, new RowMapper<ComputerDTO>() {
					public ComputerDTO mapRow(ResultSet rs, int arg1) throws SQLException {
						return ca.mappToComputerDTO(rs);
					}
				});
		return listComputers;
	}

	public boolean deleteAllComputersByCompanyId(int companyId) throws DAOException {
		LOGGER.info("Delete all computers by id company DAO");
		return jdbcTemplate.update(sqlDeleteAllComputerByCompanyId, companyId) == 1;
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

}
