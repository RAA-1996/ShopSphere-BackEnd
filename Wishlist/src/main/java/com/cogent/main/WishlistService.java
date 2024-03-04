package com.cogent.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
	
	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private UserClientInWishlist userClientInWishlist;
	
	@Autowired
	private ProductClientInWishlist productClientInWishlist;
	
	public List<WishlistEntity> fetchAllWishlistContentsByUserId(int userId) 
	{	
		List<WishlistEntity> wishlistEntity = wishlistRepository.findAllByUserId(userId).orElseThrow(() -> new RuntimeException("Failed to Fetch Wishlist!"));
		
		return wishlistEntity;
	}
	
	public WishlistEntity putProductInWishlist(int userId, int productId, String header) 
	{	
		ProductDao product = productClientInWishlist.getProductById(productId);
		UserDao user = userClientInWishlist.getOneUser(userId, header);
		
		if((user.getRole().equals("USER") || user.getRole().equals("ADMIN")) && product.getTitle()!=null)
		{
			WishlistEntity wishlistEntity = WishlistEntity.builder()
					.userId(userId)
					.productId(productId)
					.build();
			return wishlistRepository.save(wishlistEntity);
		}
		else 
		{
			return null;
		}
	}

	public String deleteProductFromWishlist(int userId, int productId) 
	{
		wishlistRepository.deleteById(userId, productId);
		return "Product Deleted in Wishlist!";
	}
	
}
