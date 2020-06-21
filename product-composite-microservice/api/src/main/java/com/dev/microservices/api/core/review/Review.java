package com.dev.microservices.api.core.review;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6313190408177414961L;
	
	private Integer productId;
	
	private Integer reviewId;
	
	private String author;
	
	private String subject;
	
	private String content;
	
	private String serviceAddress;
}