package com.cogent.main;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="cart-service", url="localhost:8043/cart/")
public interface CartClientInOrder {
	
	@GetMapping("/{userId}")
	public List<CartDao> getCartsByUserId(@PathVariable("userId") int userId, @RequestHeader("Authorization") String header);
	
	@DeleteMapping("/{userId}/remove/{productId}")
	public String removeProductFromCart(@PathVariable int userId, @PathVariable int productId);
	
	@GetMapping("/single/{userId}")
	public CartDao getSingleCart(@PathVariable int userId);
	
}
