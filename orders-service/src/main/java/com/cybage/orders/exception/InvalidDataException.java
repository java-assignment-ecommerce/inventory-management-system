package com.cybage.orders.exception;

import java.util.Map;

public class InvalidDataException extends RuntimeException {

	Map<String, Object> errMap;

	public InvalidDataException() {

		super();
	}

	public InvalidDataException(Map<String, Object> errMap) {

		super("Invalid data ");
		this.errMap = errMap;
	}
}