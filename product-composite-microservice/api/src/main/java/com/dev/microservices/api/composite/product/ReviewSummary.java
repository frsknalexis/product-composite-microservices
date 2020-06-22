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
public class ReviewSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7767881319538743720L;

	private Integer reviewId;
	
	private String author;
	
	private String subject;
}