package com.cogent.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutService implements LogoutHandler {
	
	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		final String authHeader = request.getHeader("Authorization");
		final String jwtToken;
		if(authHeader == null || !authHeader.startsWith("Bearer "))
		{
			return;
		}
		jwtToken = authHeader.substring(7);
		
		TokenEntity storedToken = tokenRepository.findByToken(jwtToken).orElse(null);
		if (storedToken != null)
		{
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
			System.out.println("Logout Complete!");
		}
		
	}
	
}
