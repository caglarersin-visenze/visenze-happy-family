package com.visenze.happyfamily.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductStockHelperTest {
	
	private ProductStockHelper productStockHelper;
	
	@BeforeEach
	public void setUp() {
		productStockHelper = new ProductStockHelper();
	}
	
	@Test
	public void testIsProductOrderOkayWhenWantedProductQuantityLess() {
		assertTrue(productStockHelper.isProductOrderOkay(10, 5, false));
	}
	
	@Test
	public void testIsProductOrderOkayWhenWantedProductQuantityMore() {
		assertFalse(productStockHelper.isProductOrderOkay(10, 11, false));
	}
	
	@Test
	public void testCalculateGivenProductQuantityWhenWantedProductQuantityOver() {
		assertEquals(10, productStockHelper.calculateGivenProductQuantity(11, 10));
	}
	
	@Test
	public void testCalculateGivenProductQuantityWhenWantedProductQuantityLess() {
		assertEquals(5, productStockHelper.calculateGivenProductQuantity(5, 10));
	}

}
