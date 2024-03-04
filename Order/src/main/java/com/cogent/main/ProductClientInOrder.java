package com.cogent.main;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="product-service", url="localhost:8042/product/")
public interface ProductClientInOrder {
	
	@GetMapping("/fetch/{productId}")
	public ProductEntity getProductById(@PathVariable("productId") int productId);
	
	@GetMapping("/cartfetch/{userId}")
	public List<ProductEntity> getProductByUserId(@PathVariable int userId);
	
}
