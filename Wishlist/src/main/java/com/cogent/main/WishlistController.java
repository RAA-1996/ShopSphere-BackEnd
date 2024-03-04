package com.cogent.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
	
	@Autowired
	private WishlistService wishlistService;
	
	@GetMapping("/{userId}")
	public List<WishlistEntity> getAllWishlistContents(@PathVariable int userId)
	{
		System.out.println(userId);
		return wishlistService.fetchAllWishlistContentsByUserId(userId);
	}
	
	@PostMapping("/{userId}/add")
	public WishlistEntity addProductToWishlist(@RequestHeader("Authorization") String authHeader, @PathVariable int userId, @RequestParam int productId)
	{	
		return wishlistService.putProductInWishlist(userId, productId, authHeader);
	}
	
	@DeleteMapping("/{userId}/remove/{productId}")
	public String removeProductFromWishlist(@PathVariable int userId, @PathVariable int productId)
	{
		return wishlistService.deleteProductFromWishlist(userId, productId);
	}
	
}
