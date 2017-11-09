package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionMySQL {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionMySQL.class);
	private static HikariConfig config = new HikariConfig("/hikari.properties");
	private static HikariDataSource ds = new HikariDataSource(config);
	
	public static Connection getConnection() {
		
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			LOGGER.error("SQL Exception GET DATABASE CONNECTION");
			System.exit(-1);
			return null;
		}
	}
}
