package com.dev.microservices.composite.product.services;

import java.util.List;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

import static org.springframework.http.HttpMethod.GET;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dev.microservices.api.core.product.Product;
import com.dev.microservices.api.core.product.ProductService;
import com.dev.microservices.api.core.recommendation.Recommendation;
import com.dev.microservices.api.core.recommendation.RecommendationService;
import com.dev.microservices.api.core.review.Review;
import com.dev.microservices.api.core.review.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {

	private final RestTemplate restTemplate;
	
	private final ObjectMapper objectMapper;
	
	private final String productServiceUrl;
	
	private final String recommendationServiceUrl;
	
	private final String reviewServiceUrl;
	
	@Value("${app.product-service.host}")
	private String productServiceHost;
	
	@Value("${app.product-service.port}")
	private Integer productServicePort;
	
	@Value("${app.recommendation-service.host}")
	private String recommendationServiceHost;
	
	@Value("${app.recommendation-service.port}")
	private Integer recommendationServicePort;
	
	@Value("${app.review-service.host}")
	private String reviewServiceHost;
	
	@Value("${app.review-service.port}")
	private Integer reviewServicePort;
	
	private static BiFunction<String, Integer, String> buildUrlProductService = (host, port) -> {
		return "http://" + host + ":" + port + "/product/";
	};
	
	private static BiFunction<String, Integer, String> buildUrlRecommendationService = (host, port) -> {
		return "http://" + host + ":" + port + "/recommendation?productId=";
	};
	
	private static BiFunction<String, Integer, String> buildUrlReviewService = (host, port) -> {
		return "http://" + host + ":" + port +"/review?productId=";
	};
	
	public ProductCompositeIntegration(@Autowired RestTemplate restTemplate, @Autowired ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
		this.productServiceUrl = buildUrlProductService.apply(productServiceHost, productServicePort);
		this.recommendationServiceUrl = buildUrlRecommendationService.apply(recommendationServiceHost, recommendationServicePort);
		this.reviewServiceUrl = buildUrlReviewService.apply(reviewServiceHost, reviewServicePort);
	}
	
	private static BiFunction<String, Integer, String> buildUrlHelper = (serviceUrl, key) -> {
		return serviceUrl + key;
	};
	
	@Override
	public Product getProduct(Integer productId) {
		String productUrl = buildUrlHelper.apply(productServiceUrl, productId);
		log.info("Will call getProduct API on URL: {}", productUrl);
		Product productResponse = restTemplate.getForObject(productUrl, Product.class);
		log.info("Found a product with id: {}", productResponse.getProductId());
		return productResponse;
	}
	
	@Override
	public List<Recommendation> getRecommendations(Integer productId) {
		String recommendationUrl = buildUrlHelper.apply(recommendationServiceUrl, productId);
		log.info("Will call getRecommendations API on URL: {}", recommendationUrl);
		List<Recommendation> recommendationList = restTemplate.exchange(recommendationUrl, GET, null, 
				new ParameterizedTypeReference<List<Recommendation>>() {}).getBody();
		log.info("Found {} recommendations for a product with id: {}", recommendationList.size(), productId);
		return recommendationList;
	}
	
	@Override
	public List<Review> getReviews(Integer productId) {
		String reviewUrl = buildUrlHelper.apply(reviewServiceUrl, productId);
		log.info("Will call getReviews API on URL: {}", reviewUrl);
		List<Review> reviewList = restTemplate.exchange(reviewUrl, GET, null, 
				new ParameterizedTypeReference<List<Review>>() {}).getBody();
		log.info("Found {} reviews for a product with id: {}", reviewList.size(), productId);
		return reviewList;
	}
}