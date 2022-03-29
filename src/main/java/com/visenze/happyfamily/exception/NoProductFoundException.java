package com.visenze.happyfamily.exception;

public class NoProductFoundException extends Exception {

	private static final long serialVersionUID = 2202188610940653366L;
	
	public NoProductFoundException(String errorMessage) {
		super(errorMessage);
	}

}
