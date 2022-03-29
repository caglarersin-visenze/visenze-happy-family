package com.visenze.happyfamily.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductGiven {
	
	private Long productId;
	
	private String productName;
	
	private int quantity;
		
}
