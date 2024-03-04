package com.cogent.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User NOT Found..."));
		
		return UserDao.builder()
				.username(userEntity.getEmail())
				.password(userEntity.getPassword())
				.build();
	}

	//FeignClient for Cart---------------------------------------
//	public UserDao findUserByUserId(int userId) 
//	{
//		UserEntity userEntity = userRepository.findById(userId).orElse(new UserEntity());
//		
//		return UserDao.builder()
//				.username(userEntity.getUsername())
//				.email(userEntity.getEmail())
//				.password(userEntity.getPassword())
//				.name(userEntity.getName())
//				.address(userEntity.getAddress())
//				.role(userEntity.getRole())
//				.build();
//	}
	
}
