package com.cogent.main;

import java.util.List;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDao {
	
	private int userId;
	private String username;
	private String email;
	@Embedded
	private NameEntity name;
	@Embedded
	private AddressEntity address;
	private String mobile;
	private String role;
	
	private List<ProductDao> productList;
	
}
