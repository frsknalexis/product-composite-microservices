package com.dev.microservices.util.exceptions;

public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1173649370332026710L;

	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NotFoundException(Throwable cause) {
		super(cause);
	}
}