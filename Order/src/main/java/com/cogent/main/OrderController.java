package com.cogent.main;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/{userId}")
	public List<OrderEntity> getAllOrderContents(@PathVariable int userId, @RequestHeader("Authorization") String header)
	{
		return orderService.fetchAllOrderContentsByUserId(userId, header);
	}
	
	@PostMapping("/create/{userId}")
	public OrderDao createOrder(@PathVariable int userId, @RequestHeader("Authorization") String authHeader)
	{
		return orderService.createNewOrder(userId, authHeader);
	}
	
//	@GetMapping("/detail/{orderId}")
//	public DetailedInfo detailOrder(@PathVariable int orderId)
//	{
//		return orderService.detailOfOrder(orderId);
//	}
	
}
