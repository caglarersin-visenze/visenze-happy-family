package com.visenze.happyfamily.helper;

import org.springframework.stereotype.Component;

@Component
public class ProductStockHelper {

	public boolean isProductOrderOkay(int productStock, int wantedProductQuantity, boolean isBuyIfStockShort) {

		if (wantedProductQuantity <= productStock)
			return true;

		return isBuyIfStockShort;
	}

	public int calculateGivenProductQuantity(int wantedProductQuantity, int productStock) {
		return wantedProductQuantity > productStock ? productStock : wantedProductQuantity;
	}

}
