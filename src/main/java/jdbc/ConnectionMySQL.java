package jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:spring.properties")
public class ConnectionMySQL {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionMySQL.class);

	@Value("${spring.datasource.driver-class-name}")
	private String driverClass;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.url}")
	private String url;

	@Autowired
	private HikariDataSource ds;

	@Bean
	public HikariDataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setPassword(password);
		config.setUsername(username);
		config.setJdbcUrl(url);
		config.setDriverClassName(driverClass);
		ds = new HikariDataSource(config);
		return ds;
	}

	public Connection getConnection() {

		try {
			return ds.getConnection();
		} catch (SQLException e) {
			LOGGER.error("SQL Exception GET DATABASE CONNECTION");
			System.exit(-1);
			return null;
		}
	}

	@Bean
	public JdbcTemplate jdbcTemplate(HikariDataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}
}
