package com.cogent.main;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, Integer>{
	
	Optional<List<WishlistEntity>> findAllByUserId(int userId);

    @Modifying
    @Transactional
	@Query(value="DELETE FROM WishlistEntity WHERE userId= :userId AND productId= :productId")
	void deleteById(int userId, int productId);
	
}
