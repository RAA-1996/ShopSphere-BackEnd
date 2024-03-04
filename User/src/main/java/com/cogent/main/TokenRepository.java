package com.cogent.main;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {			//used for database operations with token
	
	@Query(value = 
			"from TokenEntity te inner join UserEntity ue on te.userEntity.id = ue.id" 
			+ " where ue.id = :userId and (te.expired = false or te.revoked = false)"
			)
	List<TokenEntity> findAllValidTokenByUserEntity(int userId);			//used to acquire all the valid tokens associated with a specific user, using @Query

	Optional<TokenEntity> findByToken(String jwtToken);			//
	
}
