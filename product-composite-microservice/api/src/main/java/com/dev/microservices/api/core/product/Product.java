package com.dev.microservices.api.core.product;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7796308245923630536L;

	private Integer productId;
	
	private String name;
	
	private Integer weight;
	
	private String serviceAddress;
}