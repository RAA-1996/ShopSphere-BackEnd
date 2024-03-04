package com.cogent.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {
	
	private String username;
	private String email;
	private String password;
//	private NameEntity name;
//	private AddressEntity address;
	private String mobile;
	private String role;
	
}
