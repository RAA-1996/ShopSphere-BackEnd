package com.cogent.main;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
	
	@Autowired
	private DiscountRepository discountRepository;
	
	@Autowired
	private AdminClientInOrder adminClientInOrder;

	public List<DiscountDao> fetchAllDiscounts(String authorization)	//ADMIN ONLY
	{
		adminClientInOrder.validateTheAdmin(authorization);
		
		List<DiscountEntity> discountEntityList = discountRepository.findAll();
		
		return discountEntityList.stream()
				.map(discounts -> DiscountDao.builder()
						.discountName(discounts.getDiscountName())
						.discountCode(discounts.getDiscountCode())
						.reductionPercent(discounts.getReductionPercent())
						.build())
					.collect(Collectors.toList());
	}

	public DiscountDao insertDiscount(DiscountDao discountDao, String authorization)	//ADMIN ONLY
	{
		adminClientInOrder.validateTheAdmin(authorization);
		
		DiscountEntity discountEntity = DiscountEntity.builder()
				.discountName(discountDao.getDiscountName())
				.discountCode(discountDao.getDiscountCode())
				.reductionPercent(discountDao.getReductionPercent())
				.build();
		DiscountEntity newDiscountEntity = discountRepository.save(discountEntity);
		
		return DiscountDao.builder()
				.discountName(newDiscountEntity.getDiscountName())
				.discountCode(newDiscountEntity.getDiscountCode())
				.reductionPercent(newDiscountEntity.getReductionPercent())
				.build();
	}

	public DiscountDao alterDiscount(DiscountDao discountDao, int discountId, String authorization)	//ADMIN ONLY
	{
		adminClientInOrder.validateTheAdmin(authorization);
		
		Optional<DiscountEntity> discountEntity = discountRepository.findById(discountId);

		if(discountEntity.isPresent())
		{
			discountEntity.get().setDiscountName(discountDao.getDiscountName());
			discountEntity.get().setDiscountCode(discountDao.getDiscountCode());
			discountEntity.get().setReductionPercent(discountDao.getReductionPercent());
			
			DiscountEntity updatedDiscountEntity = discountRepository.save(discountEntity.get());
			return DiscountDao.builder()
					.discountName(updatedDiscountEntity.getDiscountName())
					.discountCode(updatedDiscountEntity.getDiscountCode())
					.reductionPercent(updatedDiscountEntity.getReductionPercent())
					.build();
		}
		else
		{
			System.out.println("Invalid discountId...");
			return new DiscountDao();
		}
	}

	public String removeDiscount(int discountId, String authorization)	//ADMIN ONLY
	{
		adminClientInOrder.validateTheAdmin(authorization);
		
		Optional<DiscountEntity> discountEntity = discountRepository.findById(discountId);
		if(discountEntity.isPresent())
		{
			discountRepository.deleteById(discountEntity.get().getDiscountId());
			return "Discount Deleted!";
		}
		else
		{
			return "Failed to Delete Discount!";
		}
	}

	public DiscountDao fetchDiscountByCode(String discountCode) 
	{
		DiscountEntity discountEntity = discountRepository.getByDiscountCode(discountCode).orElseThrow(() -> new RuntimeException("Failed to get Discount!"));
		
		return DiscountDao.builder()
				.discountName(discountEntity.getDiscountName())
				.discountCode(discountEntity.getDiscountCode())
				.reductionPercent(discountEntity.getReductionPercent())
				.build();
	}
	
	
	
}
