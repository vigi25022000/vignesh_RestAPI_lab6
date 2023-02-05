package com.greatlearning.studentmgmt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.greatlearning.studentmgmt.service.UserDetailsImpl;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsImpl();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		var authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(getUserDetailsService());
		authProvider.setPasswordEncoder(getPasswordEncoder());
		return authProvider;
	}

	@Override
	public void configure(AuthenticationManagerBuilder authManager) {
		authManager.authenticationProvider(getAuthenticationProvider());
	}

	@Override
	public void configure(HttpSecurity auth) throws Exception {
		auth.authorizeHttpRequests()
				.antMatchers("/student/list", "/student/showFormForAdd", "/student/save", "/student/403", "/")
				.hasAnyAuthority("USER", "ADMIN")
				.antMatchers("/student/showFormForUpdate", "/student/delete")
				.hasAuthority("ADMIN")
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginProcessingUrl("/login")
				.successForwardUrl("/student/list")
				.permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/login")
				.permitAll()
				.and()
				.exceptionHandling()
				.accessDeniedPage("/student/403")
				.and()
				.cors()
				.and()
				.csrf()
				.disable();
	}

}
