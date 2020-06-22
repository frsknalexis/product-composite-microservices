package com.dev.microservices.core.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.dev.microservices.api.core.product.Product;
import com.dev.microservices.api.core.product.ProductService;
import com.dev.microservices.util.exceptions.InvalidInputException;
import com.dev.microservices.util.exceptions.NotFoundException;
import com.dev.microservices.util.http.ServiceUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ServiceUtil serviceUtil;
	
	public ProductServiceImpl(@Autowired ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	
	@Override
	public Product getProduct(Integer productId) {
		log.info("/product return the found product for productId={}", productId);
		if (productId < 1) {
			throw new InvalidInputException("Invalid productId: " + productId);
		}
		if (productId == 13) {
			throw new NotFoundException("No product found for productId: " + productId);
		}
		Product product = Product.builder()
						.productId(productId)
						.name("name - " + productId)
						.weight(123)
						.serviceAddress(serviceUtil.getServiceAddress())
						.build();
		return product;
	}
}
