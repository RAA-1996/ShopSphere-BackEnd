package com.cogent.main;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {			//used for database operations with User
	
	Optional<UserEntity> findByEmail(String email);			//finds the User based on the email they input
	
}
