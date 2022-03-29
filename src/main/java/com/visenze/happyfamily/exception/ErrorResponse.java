package com.visenze.happyfamily.exception;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
	
	private int statusCode;
	
	private Date date;
	
	private String errorMessage;
	
	private String errorExplanation;
	
}
