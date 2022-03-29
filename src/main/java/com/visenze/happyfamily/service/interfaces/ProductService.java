package com.visenze.happyfamily.service.interfaces;

import com.visenze.happyfamily.exception.NoProductFoundException;
import com.visenze.happyfamily.model.request.OrderRequest;
import com.visenze.happyfamily.model.response.AvailableProductsResponse;
import com.visenze.happyfamily.model.response.OrderResponse;

public interface ProductService {
	
	AvailableProductsResponse getAllProducts();
	
	OrderResponse getOrder(OrderRequest request) throws NoProductFoundException;
	
	Integer getProductStock(Long id) throws NoProductFoundException;

}
