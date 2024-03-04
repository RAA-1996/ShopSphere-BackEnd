package com.cogent.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import com.google.common.net.HttpHeaders;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
	
	public static class Config {
	}

	public AuthFilter() 
	{
		super(Config.class);
	}
	
	@Autowired
	private RouteValidator routeValidator;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Override
	public GatewayFilter apply(Config config) {
		
		return ((exchange, chain) -> {
			
			if(routeValidator.isSecured.test(exchange.getRequest()))
			{
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				{
					throw new RuntimeException("Header NOT Found!");
				}
				String jwtToken = "";
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if(authHeader != null && authHeader.startsWith("Bearer "))
				{
					jwtToken = authHeader.substring(7);
				}
				jwtUtil.validateToken(jwtToken);
			}
			return chain.filter(exchange);
		});
	}
	
}
