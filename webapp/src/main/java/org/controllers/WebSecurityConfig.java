package org.controllers;

import org.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@ComponentScan({ "org.service" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private UserService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		LOGGER.info("config secu auth");
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("toto");

        http.csrf()

        // Droits de l'utilisateur
        .and().authorizeRequests()
        .antMatchers("/dashboard", "/css/**", "/js/**", "/fonts/**")
        .authenticated()

        // Droits du modérateur
        .antMatchers("/addcomputer",
                                "/editcomputer",
                                "/deletecomputers")
        .hasAuthority("moderator")

        // Droits de l'administrateur
        .antMatchers("/deleteCompany")
        .hasAuthority("admin")

        .and()
        .formLogin()

        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

        .and()
        .httpBasic().authenticationEntryPoint(entryPoint);
    }

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() {
		LOGGER.info("bean detail service");
		return userService;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		LOGGER.info("password encoder");
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
}
