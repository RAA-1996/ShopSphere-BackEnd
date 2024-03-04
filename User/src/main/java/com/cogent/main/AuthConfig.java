package com.cogent.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class AuthConfig {
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	@Autowired
	private LogoutService logoutService;
	
	@Bean
	public UserDetailsService userDetailsService()
	{
		return new UserService();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
	{
		http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(requests -> requests
				.requestMatchers("/auth/register", "/auth/login", "/auth/validate", "/user/**")
				.permitAll()
				.anyRequest()
				.authenticated())
		.sessionManagement(management -> management
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
		.logout(logout -> logout
				.logoutUrl("/auth/logout")					//the logout endpoint that we use to expire & revoke tokens of a user that logs out of system
				.addLogoutHandler(logoutService)
				.logoutSuccessHandler((requests, response, authenticate) -> SecurityContextHolder
						.clearContext()));
		
		return http.build();			//
	}
	
	@Bean
	public PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder();			//used to encode the password
	}
	
	@Bean
	public AuthenticationProvider provider() 
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(encoder());
		return provider;			//
	}
	
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception 
	{
		return config.getAuthenticationManager();			//
	}
	
}
