package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dao.DAOComputer;

public class ConnectionMySQL {

	private static final Logger LOGGER = LoggerFactory.getLogger(DAOComputer.class);
	private static HikariConfig config = new HikariConfig("/hikari.properties");
	private static HikariDataSource ds = new HikariDataSource(config);
	
	public static Connection getConnection() {
		
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			LOGGER.info("SQL Exception ");
			return null;
		}
	}
}
