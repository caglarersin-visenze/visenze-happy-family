package com.visenze.happyfamily.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.visenze.happyfamily.entity.ProductEntity;
import com.visenze.happyfamily.exception.NoProductFoundException;
import com.visenze.happyfamily.helper.ProductStockHelper;
import com.visenze.happyfamily.model.ProductWanted;
import com.visenze.happyfamily.model.request.OrderRequest;
import com.visenze.happyfamily.model.response.AvailableProductsResponse;
import com.visenze.happyfamily.model.response.OrderResponse;
import com.visenze.happyfamily.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private ProductStockHelper productStockHelper;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Captor
	ArgumentCaptor<List<ProductEntity>> productEntitiesCaptor;

	@Test
	public void testGetOrderWhenRequestValid() throws NoProductFoundException {
		OrderRequest request = getOrderRequest();
		when(productRepository.findById(any(Long.class))).thenReturn(getOptionalProductEntity());
		when(productStockHelper.isProductOrderOkay(anyInt(), anyInt(), any(Boolean.class))).thenReturn(true);
		when(productStockHelper.calculateGivenProductQuantity(anyInt(), anyInt())).thenReturn(2);
		when(productRepository.saveAll(any())).thenReturn(null);

		OrderResponse response = productServiceImpl.getOrder(request);

		verify(productRepository).saveAll(productEntitiesCaptor.capture());
		assertEquals(0, productEntitiesCaptor.getValue().get(0).getProductStock());

		assertEquals(2L, response.getGivenProducts().get(0).getProductId());
		assertEquals("egg", response.getGivenProducts().get(0).getProductName());
		assertEquals(2, response.getGivenProducts().get(0).getQuantity());

	}

	@Test
	public void testGetOrderWillThrowWhenProductNotFound() {
		OrderRequest request = getOrderRequest();
		when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		NoProductFoundException exception = assertThrows(NoProductFoundException.class, () -> {
			productServiceImpl.getOrder(request);
		}, "NoProductFoundException was expected.");

		assertEquals("No product found with id:" + request.getWantedProducts().get(0).getProductId(),
				exception.getMessage());
	}

	@Test
	public void testGetAllProducts() {
		when(productRepository.findAll()).thenReturn(getProductEntities());

		AvailableProductsResponse response = productServiceImpl.getAllProducts();

		assertEquals(2L, response.getAvailableProducts().get(0).getProductId());
		assertEquals("egg", response.getAvailableProducts().get(0).getProductName());
		assertEquals(2, response.getAvailableProducts().get(0).getQuantity());
	}

	@Test
	public void testGetProductStockWhenIdValid() throws NoProductFoundException {
		when(productRepository.findById(any(Long.class))).thenReturn(getOptionalProductEntity());

		assertEquals(2, productServiceImpl.getProductStock(2L));
	}

	@Test
	public void testGetProductStockWillThrowWhenProductNotFound() {
		when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		NoProductFoundException exception = assertThrows(NoProductFoundException.class, () -> {
			productServiceImpl.getProductStock(2L);
		}, "NoProductFoundException was expected.");

		assertEquals("No product found with id:" + 2L, exception.getMessage());
	}

	private OrderRequest getOrderRequest() {
		return OrderRequest.builder()
				.wantedProduct(ProductWanted.builder().isBuyIfStockShort(false).productId(2L).quantity(2).build())
				.build();
	}

	private Optional<ProductEntity> getOptionalProductEntity() {
		return Optional.of(ProductEntity.builder().productId(2L).productName("egg").productStock(2).build());
	}

	private List<ProductEntity> getProductEntities() {
		List<ProductEntity> productEntites = new ArrayList<>();
		productEntites.add(getOptionalProductEntity().get());
		return productEntites;
	}

}
