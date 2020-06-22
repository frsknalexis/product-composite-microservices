package com.dev.microservices.api.composite.product;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAggregate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3306028961067080806L;

	private Integer productId;
	
	private String name;
	
	private Integer weight;
	
	private List<RecommendationSummary> recommendations;
	
	private List<ReviewSummary> reviews;
	
	private ServiceAddresses serviceAddresses;
}