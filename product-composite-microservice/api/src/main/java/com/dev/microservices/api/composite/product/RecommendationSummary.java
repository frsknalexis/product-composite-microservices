package com.dev.microservices.api.composite.product;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1349889298675699977L;

	private Integer recommendationId;
	
	private String author;
	
	private Integer rate;
}