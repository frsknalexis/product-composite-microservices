package com.dev.microservices.core.recommendation.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.dev.microservices.api.core.recommendation.Recommendation;
import com.dev.microservices.api.core.recommendation.RecommendationService;
import com.dev.microservices.util.exceptions.InvalidInputException;
import com.dev.microservices.util.exceptions.NotFoundException;
import com.dev.microservices.util.http.ServiceUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

	private final ServiceUtil serviceUtil;
	
	public RecommendationServiceImpl(@Autowired ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	
	@Override
	public List<Recommendation> getRecommendations(Integer productId) {
		if (productId < 1) {
			throw new InvalidInputException("Invalid productId: " + productId);
		}
		
		if (productId == 133) {
			log.info("No recommendations found for productId: {}", productId);
			throw new NotFoundException("No recommendations found for productId: " + productId);
		}
		
		List<Recommendation> listRecommendation = generateRecommendationList(productId);
		log.info("/recommendation response size: {}", listRecommendation.size());
		return listRecommendation;
	}
	
	private List<Recommendation> generateRecommendationList(Integer productId) {
		return IntStream.rangeClosed(1, 3)
						.boxed()
						.map((i) -> Recommendation.builder()
										.productId(productId)
										.recommendationId(i)
										.author("Author ".concat(String.valueOf(i)))
										.rate(i)
										.content("Content ".concat(String.valueOf(i)))
										.serviceAddress(serviceUtil.getServiceAddress())
										.build())
						.collect(Collectors.toList());
	}
}