package com.cogent.main;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
//	@GetMapping("/fetch")
//	public List<ProductDao> getAllProducts()
//	{
//		return productService.fetchAllProducts();
//	}
	
	@GetMapping("/fetch")
	public List<ProductEntity> getAllProducts()
	{
		return productService.fetchAllProducts();
	}
	
	@PostMapping("/insert")	//ADMIN ONLY
	public ProductDao addProduct(@RequestBody ProductDao productDao, @RequestHeader("Authorization") String authorization)
	{
		return productService.insertProduct(productDao, authorization);
	}
	
	@GetMapping("/fetch/{productId}")
	public ProductDao getProductById(@PathVariable("productId") int productId)
	{
		return productService.fetchProductById(productId);
	}
	
	@PutMapping("/update/{productId}")	//ADMIN ONLY
	public ProductDao updateProduct(@RequestBody ProductDao productDao, @PathVariable("productId") int productId, @RequestHeader("Authorization") String authorization)
	{
		return productService.updateProductById(productDao, productId, authorization);
	}
	
	@DeleteMapping("/delete/{productId}")	//ADMIN ONLY
	public String deleteProduct(@PathVariable("productId") int productId, @RequestHeader("Authorization") String authorization)
	{
		return productService.deleteProductById(productId, authorization);
	}
	
	@GetMapping("/fetch/category/{category}")
	public List<ProductDao> getProductByCategory(@PathVariable String category)
	{
		List<ProductDao> productCategories = productService.findByCategory(category);
		return productCategories;
	}
	
//	@PostMapping("/upload")
//	public ProductDAO bukUpload()
//	{
//		
//	}
	
}
