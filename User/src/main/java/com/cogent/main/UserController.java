package com.cogent.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")			//for the User!
public class UserController {
	
	@Autowired
	private AuthService authService;
	
	//FeignClient...
	@GetMapping("/users/{userId}")
	public UserDao getOneUser(@PathVariable int userId, @RequestHeader("Authorization") String authorization)
	{
		System.out.println(authorization);
		return authService.fetchOneUser(userId, authorization);
	}
	
}
