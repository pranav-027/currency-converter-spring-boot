package com.miniproject.currency_converter.advice;

import com.miniproject.currency_converter.exceptions.BadRequestException;
import com.miniproject.currency_converter.exceptions.TechnicalException;
import com.miniproject.currency_converter.model.ApiResponse.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> handleIllegalArgumentResponse(IllegalArgumentException e) {
		ApiError apiError = ApiError.builder()
				.message(e.getMessage())
				.errorCode(HttpStatus.BAD_REQUEST)
				.build();

		return buildErrorResponse(apiError);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiError> handleIllegalArgumentResponse(BadRequestException e) {
		ApiError apiError = ApiError.builder()
				.message(e.getMessage())
				.errorCode(HttpStatus.BAD_REQUEST)
				.build();

		return buildErrorResponse(apiError);
	}

	@ExceptionHandler(TechnicalException.class)
	public ResponseEntity<ApiError> handleTechnicalException(TechnicalException e) {
		ApiError apiError = ApiError.builder()
				.message(e.getMessage())
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
				.build();
		
		return buildErrorResponse(apiError);
	}


	private ResponseEntity<ApiError> buildErrorResponse(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getErrorCode());
	}


}
