package com.cogent.main;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/{userId}")
	public List<CartEntity> getCartsByUserId(@PathVariable int userId)
	{
		System.out.println(userId);
		return cartService.fetchAllCartContentsByUserId(userId);
	}
	
	@PostMapping("/{userId}/add")
	public CartEntity addProductToCart(@RequestHeader("Authorization") String authHeader, @PathVariable int userId, @RequestParam int productId)
	{	
		return cartService.putProductInCart(userId, productId, authHeader);
	}
	
	@DeleteMapping("/{userId}/remove/{productId}")
	public String removeProductFromCart(@PathVariable int userId, @PathVariable int productId)
	{
		return cartService.deleteProductFromCart(userId, productId);
	}
	
//	@PutMapping("{userId}/update")
//	public CartEntity updateQuantity(@PathVariable int userId)
//	{
//		return cartService.changeProductAmount(userId);
//	}
	
	//FeignClient
	@GetMapping("/single/{userId}")
	public CartEntity getSingleCart(@PathVariable int userId)
	{
		return cartService.fetchCartByUserId(userId);
	}
	
}
