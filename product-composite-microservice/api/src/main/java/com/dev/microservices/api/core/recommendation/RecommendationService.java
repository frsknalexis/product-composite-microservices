package com.dev.microservices.api.core.recommendation;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface RecommendationService {

	/**
     * Sample usage: curl $HOST:$PORT/recommendation?productId=1
     *
     * @param productId
     * @return
     */
	@GetMapping(value = "/recommendation", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Recommendation> getRecommendations(@RequestParam(value = "productId", required = true) Integer productId);
}