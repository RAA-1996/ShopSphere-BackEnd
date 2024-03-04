package com.cogent.main;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="product-service", url="localhost:8042/product/")
public interface ProductClientInCart {
	
//	@GetMapping("/{productId}")
//	Product fetchProductByProductId(@PathVariable("productId") int productId);
	
	@GetMapping("/fetch/{productId}")
	public ProductDao getProductById(@PathVariable("productId") int productId);
	
}
