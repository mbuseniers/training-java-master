package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"services",
				"dao", 
				"controllers",
				"mappers",
				"jdbc"})
public class SpringConfiguration {
	
}
