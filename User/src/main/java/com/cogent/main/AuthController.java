package com.cogent.main;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")			//for the User!
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
//	@Autowired
//	private LogoutService logoutService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/register")
	public String addUser(@RequestBody UserEntity userEntity)
	{
		return authService.saveUser(userEntity);			//creates a new User
	}
	
	@PostMapping("/login")
	public LoginResponseDao generateToken(@RequestBody AuthRequest authRequest)
	{
		UsernamePasswordAuthenticationToken uInput = new UsernamePasswordAuthenticationToken(
				authRequest.getEmail(), 
				authRequest.getPassword());			//requests the email and password from the user and puts it into uInput
		
		try 
		{
			Authentication auth = authenticationManager.authenticate(uInput);

			if (auth.isAuthenticated()) {
				System.out.println("Authenticated...");
				Optional<UserEntity> userEntity = userRepository.findByEmail(authRequest.getEmail()); // finds the User by the email they input

				List<TokenEntity> tokenEntities = tokenRepository
						.findAllValidTokenByUserEntity(userEntity.get().getUserId()); // used to get all valid tokens for a user (query in token repository that needs our id)
				if (!tokenEntities.isEmpty()) {
					tokenEntities.stream().forEach(te -> {
						te.setRevoked(true);
						te.setExpired(true);
					}); // invalidates all old tokens for that user
				}
				tokenRepository.saveAll(tokenEntities); // saves all the updates we made on tokens into database

				String newToken = authService.generateToken(authRequest.getEmail());
				TokenEntity tokenEntity = TokenEntity.builder().expired(false).revoked(false).token(newToken)
						.tokenType(TokenType.BEARER).userEntity(userEntity.orElse(null)).build(); // creates new token for User
				tokenRepository.save(tokenEntity); // saves token into database

				return LoginResponseDao.builder()
						.token(newToken)
						.role(userEntity.get().getRole().toString())
						.build(); // gives us the generated token that the User who just logged on created
			}
		}
		catch(Exception e)
		{
//			return "Invalid Login Credentials...";
			return null;
		}
//		return "DANGER DANGER!!";
		return null;
	}
	
	//Logout endpoint is normally located in AuthConfig!
//	@PostMapping("/logout")
//	public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//	{
//		logoutService.logout(request, response, authentication);
//		return "Logout complete!";
//	}
	
	//FeignClient...
	@GetMapping("/users/{userId}")
	public UserDao getOneUser(@PathVariable int userId, @RequestHeader("Authorization") String authorization)
	{
		System.out.println(authorization);
		return authService.fetchOneUser(userId, authorization);
	}
	
	@GetMapping("/getAuth/{userId}")
	public AuthResponseDao getAUser(@PathVariable int userId, @RequestHeader("Authorization") String authorization)
	{
		System.out.println(authorization);
		return authService.fetchAUser(userId, authorization);
	}
	
	
	//---------------------------------------------------------------------for the programmer!
	@GetMapping("/validate")
	public String validToken(@RequestHeader("Authorization") String header)
	{
		String token = header.substring(7);
		
		System.out.println(token);
		
		return jwtService.validateToken(token);			//used by the programmer to check if a token is still valid or not
	}
	
	
	//FeignClient Endpoint:
//	@GetMapping("/fetchuser/{userId}")
//	public UserDao getUserByUserId(@PathVariable("userId") int userId)
//	{
//		return userService.findUserByUserId(userId);
//	}
	
}
