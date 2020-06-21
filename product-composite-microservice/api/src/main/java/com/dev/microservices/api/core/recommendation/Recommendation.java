package com.dev.microservices.api.core.recommendation;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommendation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8064627866012986993L;

	private Integer productId;
	
	private Integer recommendationId;
	
	private String author;
	
	private Integer rate;
	
	private String content;
	
	private String serviceAddress;
}