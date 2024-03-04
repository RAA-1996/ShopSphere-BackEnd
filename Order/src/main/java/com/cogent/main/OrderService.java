package com.cogent.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductClientInOrder productClientInOrder;
	
	@Autowired
	private UserClientInOrder userClientInOrder;
	
	@Autowired
	private CartClientInOrder cartClientInOrder;
	
	public List<OrderEntity> fetchAllOrderContentsByUserId(int userId, String header) 
	{	
//		cartClientInOrder.getCartsByUserId(userId, header);
		List<OrderEntity> orderEntityList = orderRepository.findAllByUserId(userId).orElseThrow(() -> new RuntimeException("Failed to Fetch Order!"));
		return orderEntityList;
	}
	
	public OrderDao createNewOrder(int userId, String authHeader) 
	{
		UserDao user = userClientInOrder.getOneUser(userId, authHeader);
		
		List<CartDao> cartList = cartClientInOrder.getCartsByUserId(userId, authHeader);
		List<ProductEntity> productList = new ArrayList<>();
		for(CartDao cart:cartList)
		{
			ProductEntity product = productClientInOrder.getProductById(cart.getProductId());
			productList.add(product);
			//to Remove products from Cart
			cartClientInOrder.removeProductFromCart(userId, cart.getProductId());
		}
//		System.out.println(cartList);
//		System.out.println(productList);
		
		OrderEntity orderEntity = OrderEntity.builder()
				.userId(userId)
				.username(user.getUsername())
				.email(user.getEmail())
				.name(user.getName())
				.address(user.getAddress())
				.mobile(user.getMobile())
				.role(user.getRole())
				.productList(productList)
				.build();
		OrderEntity insertedOrderEntity = orderRepository.save(orderEntity);
		
		List<ProductDao> productDao = productList.stream().map((product) -> ProductDao.builder()
				.title(product.getTitle())
				.price(product.getPrice())
				.description(product.getDescription())
				.category(product.getCategory())
				.image(product.getImage())
				.build())
				.collect(Collectors.toList());
		
		return OrderDao.builder()
				.userId(insertedOrderEntity.getUserId())
				.username(insertedOrderEntity.getUsername())
				.email(insertedOrderEntity.getEmail())
				.name(insertedOrderEntity.getName())
				.address(insertedOrderEntity.getAddress())
				.mobile(insertedOrderEntity.getMobile())
				.role(insertedOrderEntity.getRole())
				.productList(productDao)
				.build();
	}

//	public DetailedInfo detailOfOrder(int orderId) 
//	{
//		OrderEntity orderEntity = orderRepository.findById(orderId).get();
//		System.out.println(orderEntity);
//		
////		int userId = detailedOrderEntity.getUserId();
//		int productId = orderEntity.getProductId();
//		
//		System.out.println(productId);
//		
//		Product product = productClientInOrder.getProductById(productId);
//		
////		if(product.getTitle()!=null) 
////		{}
//		
//		return DetailedInfo.builder()
//				.userId(orderEntity.getUserId())
//				.productId(orderEntity.getProductId())
//				.address(orderEntity.getAddress())
//				.cardNumber(orderEntity.getCardNumber())
//				.cardExpire(orderEntity.getCardExpire())
//				.cardSecurity(orderEntity.getCardSecurity())
//				.productTitle(product.getTitle())
//				.productPrice(product.getPrice())
//				.productDescription(product.getDescription())
//				.productCategory(product.getCategory())
//				.productImage(product.getImage())
//				.build();
//	}
	
//	public DetailedInfo detailOfOrder(int orderId) 
//	{
//		OrderEntity orderEntity = orderRepository.findById(orderId).get();
//		System.out.println(orderEntity);
//		
//		List<Cart> cartList = orderEntity.getCartList();
//		
//		int cartSize = cartList.size();
//		int[] productIdArray = new int[cartSize];
//		
//		int i =0;
//		for (Cart cart:cartList)
//		{
//		    productIdArray[i] = cart.getProductId();
//		    i++;    
//			System.out.println(cart);
//		}
//		
//		List<Product> productList = new ArrayList<>();;
//		for(int x:productIdArray)
//		{
//			System.out.println(x);
//			Product product = productClientInOrder.getProductById(x);
//			productList.add(product);
//		}
//		
//		return DetailedInfo.builder()
//				.userId(orderEntity.getUserId())
//				.productList(productList)
//				.address(orderEntity.getAddress())
//				.cardNumber(orderEntity.getCardNumber())
//				.cardExpire(orderEntity.getCardExpire())
//				.cardSecurity(orderEntity.getCardSecurity())
//				.build();
//		return null;
//	}
	
}
