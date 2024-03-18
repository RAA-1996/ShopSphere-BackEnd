package com.cogent.main;

import java.util.List;
import java.util.Optional;
//import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

//	public List<UserDao> fetchAllUsers() 
//	{	
//		List<UserEntity> allUsers = userRepository.findAll();
//		
//		return allUsers.stream()
//				.map((users) -> UserDao.builder()
//						.username(users.getUsername())
//						.email(users.getEmail())
//						.name(users.getName())
//						.address(users.getAddress())
//						.mobile(users.getMobile())
//						.role(users.getRole())
//						.build())
//					.collect(Collectors.toList());
//	}
	
	public List<UserEntity> fetchAllUsers() 
	{	
		return userRepository.findAll();
	}

//	public UserDao fetchUserById(int userId) 
//	{	
//		UserEntity userEntity = userRepository.findById(userId).orElse(null);
//		return UserDao.builder()
//				.username(userEntity.getUsername())
//				.email(userEntity.getEmail())
//				.name(userEntity.getName())
//				.address(userEntity.getAddress())
//				.role(userEntity.getRole())
//				.build();
//	}

	public UserEntity updateUserById(UserDao userDao, int userId) 
	{
		UserEntity userEntity = UserEntity.builder()
		.userId(userId)
		.username(userDao.getUsername())
		.email(userDao.getEmail())
		.password(passwordEncoder.encode(userDao.getPassword()))
		.name(userDao.getName())
		.address(userDao.getAddress())
		.mobile(userDao.getMobile())
		.role(userDao.getRole())
		.build();
		return userRepository.save(userEntity);
	}

	public String deleteUserById(int userId) 
	{	
		Optional<UserEntity> userEntity = userRepository.findById(userId);
		if(userEntity.isPresent())
		{
			userRepository.delete(userEntity.get());
			return "User Deleted!";
		}
		else
		{
			return "Failed to Delete User!";
		}
	}

	public UserDao fetchOneUser(int userId, String authorization) 
	{
		jwtService.validateAdminToken(authorization);
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
	
	public void validatingAdmin(String authorization)
	{
		jwtService.validateAdminToken(authorization);
	}
	
}
