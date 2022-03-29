package com.visenze.happyfamily.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.visenze.happyfamily.exception.NoProductFoundException;
import com.visenze.happyfamily.model.request.OrderRequest;
import com.visenze.happyfamily.model.response.AvailableProductsResponse;
import com.visenze.happyfamily.model.response.OrderResponse;
import com.visenze.happyfamily.service.interfaces.ProductService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@ApiOperation("Get all the products' informations.")
	@GetMapping
	public ResponseEntity<AvailableProductsResponse> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@ApiOperation("Get the order for products.")
	@PutMapping
	public ResponseEntity<OrderResponse> getOrder(@Valid @RequestBody OrderRequest request)
			throws NoProductFoundException {
		return ResponseEntity.ok(productService.getOrder(request));
	}
	
	@ApiOperation("Get the specific product information.")
	@GetMapping("/{id}")
	public ResponseEntity<Integer> getProductStock(@PathVariable("id") Long id) throws NoProductFoundException {
		return ResponseEntity.ok(productService.getProductStock(id));
	}

}
