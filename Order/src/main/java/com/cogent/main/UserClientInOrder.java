package com.cogent.main;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="user-service", url="localhost:8041/user/")
public interface UserClientInOrder {
	
	@GetMapping("/users/{userId}")
	public UserDao getOneUser(@PathVariable int userId, @RequestHeader("Authorization") String authorization);
	
}
