package com.miniproject.currency_converter.factory;

import org.springframework.http.ResponseEntity;

public class RestResponseFactory {

	public static <T> ResponseEntity<T> buildOkResponse(T object){
		return ResponseEntity.ok(object);
	}

}
