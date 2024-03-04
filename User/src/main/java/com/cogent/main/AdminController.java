package com.cogent.main;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/users")
	public List<UserDao> getAllUsers()
	{
		return adminService.fetchAllUsers();
	}
	
	@PutMapping("/update/{userId}")
	public UserEntity updateUser (@RequestBody UserDao userDao, @PathVariable int userId)
	{
		return adminService.updateUserById(userDao, userId);
	}
	
	@DeleteMapping("/delete/{userId}")
	public String deleteUser (@PathVariable int userId)
	{
		return adminService.deleteUserById(userId);
	}
	
	
	//FeignClient for Order:
	@GetMapping("/users/{userId}")
	public UserDao getOneUser(@PathVariable int userId, @RequestHeader("Authorization") String authorization)
	{
		System.out.println(authorization);
		return adminService.fetchOneUser(userId, authorization);
	}
	
	@GetMapping("/validateAdmin")
	public void validateTheAdmin(@RequestHeader("Authorization") String authorization)
	{
		adminService.validatingAdmin(authorization);
	}
}
