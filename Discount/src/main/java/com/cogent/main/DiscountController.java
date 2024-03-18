package com.cogent.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
public class DiscountController {
	
	@Autowired
	private DiscountService discountService;
	
//	@GetMapping("/get")
//	public List<DiscountDao> getAllDiscounts(@RequestHeader("Authorization") String authorization)
//	{
//		return discountService.fetchAllDiscounts(authorization);
//	}
	
	@GetMapping("/get")
	public List<DiscountEntity> getAllDiscounts(@RequestHeader("Authorization") String authorization)
	{
		return discountService.fetchAllDiscounts(authorization);
	}
	
	@PostMapping("/add")
	public DiscountDao addDiscount(@RequestBody DiscountDao discountDao, @RequestHeader("Authorization") String authorization)
	{
		return discountService.insertDiscount(discountDao, authorization);
	}
	
	@PutMapping("/update/{discountId}")
	public DiscountDao updateDiscount(@RequestBody DiscountDao discountDao, @PathVariable int discountId, @RequestHeader("Authorization") String authorization)
	{
		return discountService.alterDiscount(discountDao, discountId, authorization);
	}
	
	@DeleteMapping("/delete/{discountId}")
	public String deleteDiscount(@PathVariable int discountId, @RequestHeader("Authorization") String authorization)
	{
		return discountService.removeDiscount(discountId, authorization);
	}
	
	@GetMapping("/code/{discountCode}")
	public DiscountDao gainDiscountByCode(@PathVariable String discountCode)
	{
		return discountService.fetchDiscountByCode(discountCode);
	}
	
}
