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
public class ServiceAddresses implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6818752265242259435L;

	private String compositeAddress;
	
	private String productAddress;
	
	private String reviewAddress;
	
	private String recommendationAddress;
}