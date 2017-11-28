/*package org.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{


	
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService).jdbcAuthentication();
        //.dataSource(datasource)
          //                       .usersByUsernameQuery(USERS_BY_USERNAME_QUERY)
            //                     .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
    }
	
	@Bean
    public DigestAuthenticationEntryPoint digestEntryPoint() {
        DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
        digestAuthenticationEntryPoint.setKey("mykey");
        digestAuthenticationEntryPoint.setRealmName("Digest WF Realm");
        return digestAuthenticationEntryPoint;
    }

    @Bean
    public DigestAuthenticationFilter digestAuthenticationFilter(
            DigestAuthenticationEntryPoint digestAuthenticationEntryPoint) throws Exception {
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
        digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
        return digestAuthenticationFilter;
    }
	
	@Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(digestEntryPoint()).and()
            .authorizeRequests().anyRequest().hasAuthority("admin").and()
            .addFilter(digestAuthenticationFilter(digestEntryPoint()));
    }
}*/
