package com.dev.microservices.util.http;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HttpErrorInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 444232846437084175L;

	private ZonedDateTime timestamp;
	
	private String path;
	
	private HttpStatus httpStatus;
	
	private String message;
	
	public Integer getStatus() {
		Supplier<Integer> getStatus = () -> Integer.valueOf(httpStatus.value());
		return getStatus.get();
	}
	
	public String getError() {
		Supplier<String> getError = () -> httpStatus.getReasonPhrase();
		return getError.get();
	}
}