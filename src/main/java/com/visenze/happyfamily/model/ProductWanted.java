package com.visenze.happyfamily.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductWanted {
	
	@NotNull(message = "productId cannot be empty")
	private Long productId;
	
	@Min(value = 1, message = "quantity must be at least 1")
	@Max(value = 100, message = "quantity cannot be over 100")
	private int quantity;
	
	@NotNull(message = "isBuyIfStockShort cannot be null")
	private Boolean isBuyIfStockShort;

}
