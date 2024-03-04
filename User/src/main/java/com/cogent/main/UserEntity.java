package com.cogent.main;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {			//user input variables!
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String username;
	private String email;
	private String password;
	@Embedded
	private NameEntity name;
	@Embedded
	private AddressEntity address;
	private String mobile;
	
	@Enumerated(EnumType.STRING)
	private Role role;			//used to specify the role of the user (either USER or ADMIN)
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userEntity")
	private List<TokenEntity> tokenEntities;			//used to connect tokens to a specific user
	
}
