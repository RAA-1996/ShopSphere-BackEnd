package com.cogent.main;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
	
	@Query(value = "FROM ProductEntity WHERE category LIKE %:category%")
	List<ProductEntity> findByCategory(String category);
	
}
