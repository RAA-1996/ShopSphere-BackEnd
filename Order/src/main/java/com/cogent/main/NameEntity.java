package com.cogent.main;

import jakarta.persistence.Column;
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
public class NameEntity {			//used to acquire our first and last names from UserEntity
	
	@Column(nullable = false)
	private String fname;
	@Column(nullable = false)
	private String lname;
}
