package com.cogent.main;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final String secret = "gQ7opnLAA3KvHX7JvkCniaBlkbAU1Qfvzdacp+sfXEFo3n6/pFBNxHFvqyr6BpWm";

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private UserRepository userRepository;

	public String validateToken(String jwtToken)			//checks if the token is still valid
	{
		String userEmail = extractUserEmail(jwtToken);	
		if (userEmail != null) 
		{
			UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

			boolean valid = tokenRepository.findByToken(jwtToken).map(t -> !t.isExpired() && !t.isRevoked())
					.orElse(false);

			// validateToken is in JWTUtil (in Gateway), but I created a new 1 in JwtService
			if (isTokenValid(jwtToken, userDetails) && valid) 
			{
				return "Valid Token!";
//				return userDetails.getUsername().toString();
			} 
			else 
			{
				return "Invalid Token!";
//				return null;
			}
		}
		else
		{
		return "If you are getting this it is NOT good...";
		}
	}
	
	public boolean validateAdminToken(String jwtToken)
	{
		System.out.println("pre-token:"+jwtToken);
		
		String token = jwtToken.split(" ")[1];
		jwtToken = token;
		
		try
		{
			Jwts.parser()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(jwtToken);
			if(Jwts.parser()
					.setSigningKey(getSignKey())
					.build()
					.parseClaimsJws(jwtToken)
					.getPayload().getExpiration()
					.before(new Date(System.currentTimeMillis())))
			userDetailsService.loadUserByUsername(token);
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
//			return false;

//			return 			
		

	}
	
//	public boolean validateToken(String header, int userId)
//	{
//		String jwtToken = header.substring(7);
//		String userEmail = extractUserEmail(jwtToken);	
//		if (userEmail != null) 
//		{
//			UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//
//			boolean valid = tokenRepository.findByToken(jwtToken).map(t -> !t.isExpired() && !t.isRevoked())
//					.orElse(false);
//
//			if (isTokenValid(jwtToken, userDetails) && valid) 
//			{
//				UserEntity userEntity = userRepository.findById(userId).get();
////				return userDetails.getUsername().toString();
//				return userEntity.getEmail().equalsIgnoreCase(userEmail);
//			} 
//			else 
//			{
//				return false;
//			}
//		}
//		else
//		{
//		return false;
//		}
//	}

	private boolean isTokenValid(String jwtToken, UserDetails userDetails) {		//used on line 48
		String userEmail = userDetails.getUsername();
		return (userEmail.equals(extractUserEmail(jwtToken)) && !isTokenExpired(jwtToken));
	}

	public String generateToken(String username) 
	{
		return createToken(new HashMap<String, Object>(), username);			//calls the createToken method in the same jwtService file
	}

	private String createToken(Map<String, Object> claims, String username) 			//generic JWTtoken builder method
	{
		return Jwts.builder().claims(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)).signWith(getSignKey()).compact();
	}

	private Key getSignKey() 
	{
		byte[] keys = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keys);
	}

	// ------------------------------------------------------------ADDED from JwtService in SchoolManagementSystem
	public boolean isTokenValid(String jwtToken, UserDetails userDetails, String userRole) {
		String userEmail = userDetails.getUsername();
		UserEntity userEntity = userRepository.findByEmail(userEmail).get();
		System.out.println(userEntity.getRole());//TEST
		System.out.println(userEntity.getRole().toString().equalsIgnoreCase(userRole));//TEST
		return (userEmail.equals(extractUserEmail(jwtToken)) && !isTokenExpired(jwtToken) && userEntity.getRole().toString().equalsIgnoreCase(userRole));
	}
	private boolean isTokenExpired(String jwtToken) {
		return extractExpiration(jwtToken).before(new Date());
	}
	public Date extractExpiration(String jwtToken) {
		return extractClaims(jwtToken, Claims::getExpiration);
	}
	public String extractUserEmail(String jwtToken) {
		return extractClaims(jwtToken, Claims::getSubject);
	}
	public <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(jwtToken);
		return claimsResolver.apply(claims);
	}
	private Claims extractAllClaims(String jwtToken) {
		return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(jwtToken).getPayload();
	}
	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	// ------------------------------------------------------------------------------------------------------------

}
