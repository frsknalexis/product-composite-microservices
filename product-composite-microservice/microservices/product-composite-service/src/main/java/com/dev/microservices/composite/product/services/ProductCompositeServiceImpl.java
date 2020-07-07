package com.dev.microservices.composite.product.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.dev.microservices.api.composite.product.ProductAggregate;
import com.dev.microservices.api.composite.product.ProductCompositeService;
import com.dev.microservices.api.composite.product.RecommendationSummary;
import com.dev.microservices.api.composite.product.ReviewSummary;
import com.dev.microservices.api.composite.product.ServiceAddresses;
import com.dev.microservices.api.core.product.Product;
import com.dev.microservices.api.core.recommendation.Recommendation;
import com.dev.microservices.api.core.review.Review;
import com.dev.microservices.util.exceptions.NotFoundException;
import com.dev.microservices.util.http.ServiceUtil;

@RestController
public class ProductCompositeServiceImpl implements ProductCompositeService {

	private final ServiceUtil serviceUtil;
	
	private final ProductCompositeIntegration compositeIntegration;
	
	public ProductCompositeServiceImpl(@Autowired ServiceUtil serviceUtil, 
			@Autowired ProductCompositeIntegration compositeIntegration) {
		this.serviceUtil = serviceUtil;
		this.compositeIntegration = compositeIntegration;
	}
	
	@Override
	public ProductAggregate getProduct(Integer productId) {
		
		Product product = Optional.ofNullable(compositeIntegration.getProduct(productId))
				.orElseThrow(() -> new NotFoundException("No product found for productId: " + productId));
		List<Recommendation> recommendationList = compositeIntegration.getRecommendations(productId);
		List<Review> reviewList = compositeIntegration.getReviews(productId);
		return createProductAggregate(product, recommendationList, reviewList, serviceUtil.getServiceAddress());
	}
	
	private ProductAggregate createProductAggregate(Product product, List<Recommendation> recommendationList,
			List<Review> reviewList, String serviceAddress) {
		
		// Copy summary recommendation info, if available
		List<RecommendationSummary> recommendationSummaryList = Optional.ofNullable(recommendationList)
				.map(lista -> lista.stream()
						.map(r -> RecommendationSummary.builder()
								.recommendationId(r.getRecommendationId())
								.author(r.getAuthor())
								.rate(r.getRate())
								.build())
						.collect(Collectors.toList()))
				.orElseGet(() -> new ArrayList<RecommendationSummary>());
		
		// Copy summary review info, if available
		List<ReviewSummary> reviewSummaryList = Optional.ofNullable(reviewList)
				.map(lista -> lista.stream()
						.map(r -> ReviewSummary.builder()
								.reviewId(r.getReviewId())
								.author(r.getAuthor())
								.subject(r.getSubject())
								.build())
						.collect(Collectors.toList()))
				.orElseGet(() -> new ArrayList<ReviewSummary>());
		
		// Create info regarding the involved microservices addresses
		String productAddress = product.getServiceAddress();
		
		String recommendationAddress = Optional.ofNullable(recommendationList)
				.filter(lista -> lista.size() > 0)
				.flatMap(lista -> lista.stream()
						.findFirst()
						.map(Recommendation::getServiceAddress))
				.orElse("");
		
		String reviewAddress = Optional.ofNullable(reviewList)
				.filter(lista -> lista.size() > 0)
				.flatMap(lista -> lista.stream()
						.findFirst()
						.map(Review::getServiceAddress))
				.orElse("");
		
		ServiceAddresses serviceAddresses = ServiceAddresses.builder()
				.compositeAddress(serviceAddress)
				.productAddress(productAddress)
				.reviewAddress(reviewAddress)
				.recommendationAddress(recommendationAddress)
				.build();
		
		return ProductAggregate.builder()
				.productId(product.getProductId())
				.name(product.getName())
				.weight(product.getWeight())
				.recommendations(recommendationSummaryList)
				.reviews(reviewSummaryList)
				.serviceAddresses(serviceAddresses)
				.build();
	}
}