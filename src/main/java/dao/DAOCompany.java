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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
	@Autowired
	private ConnectionMySQL connectionMySQL;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PlatformTransactionManager ptm;
	
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

		listCompanies = (ArrayList<Company>) jdbcTemplate.query(sqlGetCompanies,
				new RowMapper<Company>() {
					public Company mapRow(ResultSet rs, int arg1) throws SQLException {
						return cm.mappToCompany(rs);
					}
				});
		
		return listCompanies;
	}

	public boolean checkIdCompany(int id) {
		LOGGER.info("check Id company DAO");
		return jdbcTemplate.update(sqlCheckIdCompany, id) == 1 ;
	}

	public boolean deleteCompanyById(int id) {
		LOGGER.info("delete company DAO");

		TransactionStatus ts = ptm.getTransaction(new DefaultTransactionDefinition());
		boolean deleteComputersOk=false;
		
		try {
			deleteComputersOk = daoComputer.deleteAllComputersByCompanyId(id);
			jdbcTemplate.update(sqlDeleteCompanyById, id);
			ptm.commit(ts);
			deleteComputersOk=true;
		}catch(Exception e) {
			LOGGER.error("delete company DAO EXCEPTION");
			ptm.rollback(ts);
		}
		return deleteComputersOk;

	}
}
