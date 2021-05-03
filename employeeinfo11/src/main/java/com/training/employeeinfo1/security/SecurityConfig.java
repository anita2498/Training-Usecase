package com.training.employeeinfo1.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${edp.app-security.user.secret}")
	private String userSecret;
	@Value("${edp.app-security.user.userName}")
	private String userUsername;
	@Value("${edp.app-security.admin.secret}")
	private String adminSecret;
	@Value("${edp.app-security.admin.userName}")
	private String adminUsername;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser(userUsername).password(encoder.encode(userSecret)).roles("USER").and()
				.withUser(adminUsername).password(encoder.encode(adminSecret)).roles("USER", "ADMIN");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/actuator/health", "/actuator/info", "actuator/prometheus");
	}

	/**
	 * Establishes basic authentication for all actuator endpoints that weren't
	 * ignored in the web security configuration
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers("/actuator/*").hasRole("USER").antMatchers("/employees")
				.hasAnyRole("USER", "ADMIN").antMatchers("/employees/**").hasRole("ADMIN").anyRequest().authenticated()
				.and().csrf().disable().headers().frameOptions().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}