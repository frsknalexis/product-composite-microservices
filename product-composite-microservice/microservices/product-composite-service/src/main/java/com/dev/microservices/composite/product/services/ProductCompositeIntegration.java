package com.dev.microservices.composite.product.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dev.microservices.api.core.product.Product;
import com.dev.microservices.api.core.product.ProductService;
import com.dev.microservices.api.core.recommendation.Recommendation;
import com.dev.microservices.api.core.recommendation.RecommendationService;
import com.dev.microservices.api.core.review.Review;
import com.dev.microservices.api.core.review.ReviewService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {

	@Override
	public List<Review> getReviews(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recommendation> getRecommendations(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProduct(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

}