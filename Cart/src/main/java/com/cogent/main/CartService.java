package com.cogent.main;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserClientInCart userClientInCart;
	
	@Autowired
	private ProductClientInCart productClientInCart;
	
	public List<CartEntity> fetchAllCartContentsByUserId(int userId) 
	{
		List<CartEntity> cartEntityList = cartRepository.findAllByUserId(userId).orElseThrow(() -> new RuntimeException("Failed to Fetch Cart!"));
		return cartEntityList;
	}

	public CartEntity putProductInCart(int userId, int productId, String header) 
	{	
		ProductDao product = productClientInCart.getProductById(productId);
		UserDao user = userClientInCart.getOneUser(userId, header);
		
		if((user.getRole().equals("USER") || user.getRole().equals("ADMIN")) && product.getTitle()!=null)
		{
			CartEntity cartEntity = CartEntity.builder()
					.userId(userId)
					.productId(productId)
					.build();
			return cartRepository.save(cartEntity);
		}
		else 
		{
			return null;
		}
	}

	public String deleteProductFromCart(int userId, int productId) 
	{
		cartRepository.deleteById(userId, productId);
		return "Product Deleted in Cart!";
	}

//	public CartEntity changeProductAmount(int userId) 
//	{
//		return null;
//	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------
	//FeignClient for Order:
	public CartEntity fetchCartByUserId(int userId)
	{
		return cartRepository.findById(userId).orElseThrow(() -> new RuntimeException("Failed to Fetch Cart!"));
	}
	
}
