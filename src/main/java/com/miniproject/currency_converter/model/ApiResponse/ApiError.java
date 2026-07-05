package com.miniproject.currency_converter.model.ApiResponse;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ApiError {

	private String message;
	private HttpStatus errorCode;
	private List<String> errors;

}
