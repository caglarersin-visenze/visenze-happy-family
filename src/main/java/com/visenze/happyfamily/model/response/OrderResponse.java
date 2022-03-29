package com.visenze.happyfamily.model.response;

import java.util.List;
import com.visenze.happyfamily.model.ProductGiven;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
	
	private List<ProductGiven> givenProducts;

}
