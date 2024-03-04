package com.cogent.main;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AddressEntity {			//used to acquire our address from UserEntity
	
	private String city;
	private String street;
	private String houseNumber;
	private String zipcode;
	
}
