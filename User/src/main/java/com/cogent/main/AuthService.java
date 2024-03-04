package com.cogent.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	public String saveUser(UserEntity userEntity)
	{
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));			//encodes the password
		userRepository.save(userEntity);				//saves the User input into the database
		
		String gJwtToken = jwtService.generateToken(userEntity.getEmail());				//generates a token based on the User's email (calls the generateToken in jwtService)
		TokenEntity tokenEntity = TokenEntity.builder()
				.expired(false)
				.revoked(false)
				.token(gJwtToken)
				.tokenType(TokenType.BEARER)
				.userEntity(userEntity)
				.build();			//sets all the token variables in this token associated with the user, so that we have a valid token
		tokenRepository.save(tokenEntity);			//saves the token inputs into the database
		
		return "User Registration Done!";
	}
	
	public String generateToken(String userName)			//used by AuthController
	{
		return jwtService.generateToken(userName);
	}
	
//	public void validateToken(String token)
//	{
//		jwtService.validateToken(token);
//	}
	
	public UserDao fetchOneUser(int userId, String authorization) 
	{
		if(jwtService.validateAdminToken(authorization))
		{
			UserEntity oneUser = userRepository.findById(userId).get();
			
			return UserDao.builder()
						.username(oneUser.getUsername())
						.email(oneUser.getEmail())
						.name(oneUser.getName())
						.address(oneUser.getAddress())
						.mobile(oneUser.getMobile())
						.role(oneUser.getRole())
						.build();
		}
		return null;
	}
	
	public AuthResponseDao fetchAUser(int userId, String authorization) 
	{
		jwtService.validateAdminToken(authorization);
		UserEntity oneUser = userRepository.findById(userId).get();
		
		return AuthResponseDao.builder()
					.role(oneUser.getRole().toString())
					.build();
	}

}
