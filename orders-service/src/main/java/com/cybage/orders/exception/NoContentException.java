package com.cybage.orders.exception;

public class NoContentException extends RuntimeException {

	
	public NoContentException() {

		super("No Records found");
	}
	 
}