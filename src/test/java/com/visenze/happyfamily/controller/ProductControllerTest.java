package com.visenze.happyfamily.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.visenze.happyfamily.exception.NoProductFoundException;
import com.visenze.happyfamily.model.ProductGiven;
import com.visenze.happyfamily.model.request.OrderRequest;
import com.visenze.happyfamily.model.response.AvailableProductsResponse;
import com.visenze.happyfamily.model.response.OrderResponse;
import com.visenze.happyfamily.service.interfaces.ProductService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ProductControllerTest {

	@MockBean
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	@Test
	public void testGetAllProducts() {
		when(productService.getAllProducts()).thenReturn(new AvailableProductsResponse(getGivenProducts()));

		ResponseEntity<AvailableProductsResponse> responseEntity = productController.getAllProducts();

		testHttpStatusOk(responseEntity.getStatusCodeValue());
		assertEquals(2L, responseEntity.getBody().getAvailableProducts().get(0).getProductId());
		assertEquals("egg", responseEntity.getBody().getAvailableProducts().get(0).getProductName());
		assertEquals(2, responseEntity.getBody().getAvailableProducts().get(0).getQuantity());
	}

	@Test
	public void testGetOrder() throws NoProductFoundException {
		when(productService.getOrder(any(OrderRequest.class))).thenReturn(new OrderResponse(getGivenProducts()));

		ResponseEntity<OrderResponse> responseEntity = productController.getOrder(new OrderRequest());

		testHttpStatusOk(responseEntity.getStatusCodeValue());
		assertEquals(2L, responseEntity.getBody().getGivenProducts().get(0).getProductId());
		assertEquals("egg", responseEntity.getBody().getGivenProducts().get(0).getProductName());
		assertEquals(2, responseEntity.getBody().getGivenProducts().get(0).getQuantity());
	}
	
	@Test
	public void testGetProductStock() throws NoProductFoundException {
		when(productService.getProductStock(any(Long.class))).thenReturn(2);
		
		ResponseEntity<Integer> responseEntity = productController.getProductStock(2L);
		
		testHttpStatusOk(responseEntity.getStatusCodeValue());
		assertEquals(2, responseEntity.getBody());
	}
	
	private void testHttpStatusOk(int statusCodeValue) {
		assertEquals(HttpStatus.OK.value(), statusCodeValue);
	}

	private List<ProductGiven> getGivenProducts() {
		List<ProductGiven> givenProducts = new ArrayList<>();
		givenProducts.add(ProductGiven.builder().productId(2L).productName("egg").quantity(2).build());
		return givenProducts;
	}

}
