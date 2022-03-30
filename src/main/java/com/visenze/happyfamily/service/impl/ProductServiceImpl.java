package com.visenze.happyfamily.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.visenze.happyfamily.entity.ProductEntity;
import com.visenze.happyfamily.exception.NoProductFoundException;
import com.visenze.happyfamily.helper.ProductStockHelper;
import com.visenze.happyfamily.model.ProductGiven;
import com.visenze.happyfamily.model.ProductWanted;
import com.visenze.happyfamily.model.request.OrderRequest;
import com.visenze.happyfamily.model.response.AvailableProductsResponse;
import com.visenze.happyfamily.model.response.OrderResponse;
import com.visenze.happyfamily.repository.ProductRepository;
import com.visenze.happyfamily.service.interfaces.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductStockHelper productStockHelper;

	@Override
	public OrderResponse getOrder(OrderRequest request) throws NoProductFoundException {

		List<ProductGiven> givenProducts = new ArrayList<ProductGiven>();

		List<ProductEntity> updatedProductEntities = new ArrayList<>();

		for (ProductWanted productWanted : request.getWantedProducts()) {

			Optional<ProductEntity> optionalProductEntity = productRepository.findById(productWanted.getProductId());

			if (!optionalProductEntity.isPresent())
				throw new NoProductFoundException("No product found with id:" + productWanted.getProductId());

			ProductEntity productEntity = optionalProductEntity.get();

			if (productStockHelper.isProductOrderOkay(productEntity.getProductStock(), productWanted.getQuantity(),
					productWanted.getIsBuyIfStockShort())) {

				int givenProductQuantity = productStockHelper.calculateGivenProductQuantity(productWanted.getQuantity(),
						productEntity.getProductStock());

				productEntity.setProductStock(productEntity.getProductStock() - givenProductQuantity);
				updatedProductEntities.add(productEntity);

				givenProducts.add(ProductGiven.builder().productId(productEntity.getProductId())
						.productName(productEntity.getProductName()).quantity(givenProductQuantity).build());

			}

		}

		productRepository.saveAll(updatedProductEntities);

		return new OrderResponse(givenProducts);
	}

	@Override
	public AvailableProductsResponse getAllProducts() {

		List<ProductGiven> availableProducts = new ArrayList<>();

		List<ProductEntity> products = (List<ProductEntity>) productRepository.findAll();

		for (ProductEntity product : products) {
			availableProducts.add(ProductGiven.builder().productId(product.getProductId())
					.productName(product.getProductName()).quantity(product.getProductStock()).build());
		}

		return new AvailableProductsResponse(availableProducts);
	}

	@Override
	public Integer getProductStock(Long id) throws NoProductFoundException {

		Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);

		if (!optionalProductEntity.isPresent())
			throw new NoProductFoundException("No product found with id:" + id);

		return optionalProductEntity.get().getProductStock();
	}

}
