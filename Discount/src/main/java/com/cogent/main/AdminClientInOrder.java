package com.cogent.main;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="admin-service",url="localhost:8041/admin/")
public interface AdminClientInOrder {
	
	@GetMapping("/validateAdmin")
	public void validateTheAdmin(@RequestHeader("Authorization") String authorization);
	
}
