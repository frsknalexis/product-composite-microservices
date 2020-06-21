package com.dev.microservices.util.http;

import java.time.ZonedDateTime;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dev.microservices.util.exceptions.InvalidInputException;
import com.dev.microservices.util.exceptions.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

	private Function<Exception, String> convertExceptionToStringMessage = (ex) -> ex.getMessage();
	
	private Function<ServerHttpRequest, String> getRequestPathToString = (request) -> {
		return request.getPath().pathWithinApplication().value();
	};

	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<HttpErrorInfo> handleNotFoundException(ServerHttpRequest request, Exception ex) {
		HttpErrorInfo httErrorInfo = createHttpErrorInfo(NOT_FOUND, request, ex);
		return new ResponseEntity<HttpErrorInfo>(httErrorInfo, NOT_FOUND);
	}
	
	@ExceptionHandler(value = InvalidInputException.class)
	public ResponseEntity<HttpErrorInfo> handleInvalidInputException(ServerHttpRequest request, Exception ex) {
		HttpErrorInfo httpErrorInfo = createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
		return new ResponseEntity<HttpErrorInfo>(httpErrorInfo, UNPROCESSABLE_ENTITY);
	}
	
	private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {
		String message = convertExceptionToStringMessage.apply(ex);
		String path = getRequestPathToString.apply(request);
		
		log.info("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
		
		return HttpErrorInfo.builder()
							.timestamp(ZonedDateTime.now())
							.path(path)
							.httpStatus(httpStatus)
							.message(message)
							.build();
	}
}