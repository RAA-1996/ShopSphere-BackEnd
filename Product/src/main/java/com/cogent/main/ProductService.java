package com.cogent.main;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private AdminClientInOrder adminClientInOrder;

	public List<ProductDao> fetchAllProducts() 
	{
		List<ProductEntity> allProducts = productRepository.findAll();
		
		return allProducts.stream()
				.map((products) -> ProductDao.builder()
						.title(products.getTitle())
						.price(products.getPrice())
						.description(products.getDescription())
						.category(products.getCategory())
						.image(products.getImage())
						.build())
					.collect(Collectors.toList());
	}

	public ProductDao insertProduct(ProductDao productDao, String authorization)	//ADMIN ONLY
	{
		adminClientInOrder.validateTheAdmin(authorization);
		
		ProductEntity productEntity = ProductEntity.builder()
				.title(productDao.getTitle())
				.price(productDao.getPrice())
				.description(productDao.getDescription())
				.category(productDao.getCategory())
				.image(productDao.getImage())
				.build();
		productEntity = productRepository.save(productEntity);
		
		return ProductDao.builder()
				.title(productEntity.getTitle())
				.price(productEntity.getPrice())
				.description(productEntity.getDescription())
				.category(productEntity.getCategory())
				.image(productEntity.getImage())
				.build();
	}

	public ProductDao fetchProductById(int productId) 
	{
		ProductEntity productEntity = productRepository.findById(productId).orElse(new ProductEntity());
		
		return ProductDao.builder()
				.title(productEntity.getTitle())
				.price(productEntity.getPrice())
				.description(productEntity.getDescription())
				.category(productEntity.getCategory())
				.image(productEntity.getImage())
				.build();
	}

	public ProductDao updateProductById(ProductDao productDao, int productId, String authorization)	//ADMIN ONLY
	{	
		adminClientInOrder.validateTheAdmin(authorization);
		
		Optional<ProductEntity> updatedProductEntity = productRepository.findById(productId);
		System.out.println(updatedProductEntity);//TEST
		if(updatedProductEntity.isPresent())
		{
			updatedProductEntity.get().setTitle(productDao.getTitle());
			updatedProductEntity.get().setPrice(productDao.getPrice());
			updatedProductEntity.get().setDescription(productDao.getDescription());
			updatedProductEntity.get().setCategory(productDao.getCategory());
			updatedProductEntity.get().setImage(productDao.getImage());
			
			ProductEntity productEntity = productRepository.save(updatedProductEntity.get());
			return ProductDao.builder()
					.title(productEntity.getTitle())
					.price(productEntity.getPrice())
					.description(productEntity.getDescription())
					.category(productEntity.getDescription())
					.image(productEntity.getImage())
					.build();
		}
		else
		{
			System.out.println("Invalid productId...");
			return new ProductDao();
		}
	}
	
	public String deleteProductById(int productId, String authorization)	//ADMIN ONLY
	{
		adminClientInOrder.validateTheAdmin(authorization);
		
		Optional<ProductEntity> productEntity = productRepository.findById(productId);
		if(productEntity.isPresent())
		{
			productRepository.deleteById(productEntity.get().getProductId());
			return "Product Deleted!";
		}
		else
		{
			return "Failed to Delete Product!";
		}
	}

	public List<ProductDao> findByCategory(String category) 
	{
		List<ProductEntity> productEntity = productRepository.findByCategory(category);
		
		return productEntity.stream()
				.map((products) -> ProductDao.builder()
						.title(products.getTitle())
						.price(products.getPrice())
						.description(products.getDescription())
						.category(products.getCategory())
						.image(products.getImage())
						.build())
					.collect(Collectors.toList());
	}
	
	/* 
	 *Bulk Upload Code... 
	 */
	
}
