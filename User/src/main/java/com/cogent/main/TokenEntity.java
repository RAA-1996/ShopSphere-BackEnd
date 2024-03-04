package com.cogent.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TokenEntity {			//token variables used to keep track of token status
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String token;
	private boolean expired;
	private boolean revoked;
	
	@Enumerated(EnumType.STRING)
	private TokenType tokenType;			//all set to BEARER 
	
	@ManyToOne
	@JoinColumn(name = "user_entity_id")
	@JsonIgnore
	private UserEntity userEntity;			//used to connect the User to the Token
}
