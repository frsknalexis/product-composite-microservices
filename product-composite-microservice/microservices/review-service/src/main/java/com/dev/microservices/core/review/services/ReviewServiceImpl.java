package com.dev.microservices.core.review.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.dev.microservices.api.core.review.Review;
import com.dev.microservices.api.core.review.ReviewService;
import com.dev.microservices.util.exceptions.InvalidInputException;
import com.dev.microservices.util.exceptions.NotFoundException;
import com.dev.microservices.util.http.ServiceUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ReviewServiceImpl implements ReviewService {
	
	private final ServiceUtil serviceUtil;
	
	public ReviewServiceImpl(@Autowired ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	
	@Override
	public List<Review> getReviews(Integer productId) {
		if (productId < 1) {
			throw new InvalidInputException("Invalid productId: " + productId);
		}
		
		if (productId == 213) {
			log.info("No reviews found for productId: {}", productId);
			throw new NotFoundException("No reviews found for productId: " + productId);
		}
		List<Review> reviewList = generateReviewList(productId);
		log.info("/reviews response size: {}", reviewList.size());
		return reviewList;
	}
	
	private List<Review> generateReviewList(Integer productId) {
		return IntStream.rangeClosed(1, 3)
				.boxed()
				.map((i) -> Review.builder()
						.productId(productId)
						.reviewId(i)
						.author("Author ".concat(String.valueOf(i)))
						.subject("Subject ".concat(String.valueOf(i)))
						.content("Content ".concat(String.valueOf(i)))
						.serviceAddress(serviceUtil.getServiceAddress())
						.build())
				.collect(Collectors.toList());
	}
}