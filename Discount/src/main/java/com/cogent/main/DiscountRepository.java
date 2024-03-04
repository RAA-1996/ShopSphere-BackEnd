package com.cogent.main;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Integer>{
	
	Optional<DiscountEntity> getByDiscountCode(String discountCode);
	
}
